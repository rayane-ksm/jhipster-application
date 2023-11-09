import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import JoueurService from './joueur.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EquipeService from '@/entities/equipe/equipe.service';
import { type IEquipe } from '@/shared/model/equipe.model';
import { type IJoueur, Joueur } from '@/shared/model/joueur.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JoueurUpdate',
  setup() {
    const joueurService = inject('joueurService', () => new JoueurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const joueur: Ref<IJoueur> = ref(new Joueur());

    const equipeService = inject('equipeService', () => new EquipeService());

    const equipes: Ref<IEquipe[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveJoueur = async joueurId => {
      try {
        const res = await joueurService().find(joueurId);
        res.dateNaissance = new Date(res.dateNaissance);
        joueur.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.joueurId) {
      retrieveJoueur(route.params.joueurId);
    }

    const initRelationships = () => {
      equipeService()
        .retrieve()
        .then(res => {
          equipes.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nom: {},
      poste: {},
      numeroMaillot: {},
      telephone: {},
      dateNaissance: {},
      prixTransfer: {},
      equipe: {},
    };
    const v$ = useVuelidate(validationRules, joueur as any);
    v$.value.$validate();

    return {
      joueurService,
      alertService,
      joueur,
      previousState,
      isSaving,
      currentLanguage,
      equipes,
      v$,
      ...useDateFormat({ entityRef: joueur }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.joueur.id) {
        this.joueurService()
          .update(this.joueur)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApplicationFootballApp.joueur.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.joueurService()
          .create(this.joueur)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApplicationFootballApp.joueur.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
