import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EquipeService from './equipe.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EntraineurService from '@/entities/entraineur/entraineur.service';
import { type IEntraineur } from '@/shared/model/entraineur.model';
import { type IEquipe, Equipe } from '@/shared/model/equipe.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EquipeUpdate',
  setup() {
    const equipeService = inject('equipeService', () => new EquipeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const equipe: Ref<IEquipe> = ref(new Equipe());

    const entraineurService = inject('entraineurService', () => new EntraineurService());

    const entraineurs: Ref<IEntraineur[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEquipe = async equipeId => {
      try {
        const res = await equipeService().find(equipeId);
        equipe.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.equipeId) {
      retrieveEquipe(route.params.equipeId);
    }

    const initRelationships = () => {
      entraineurService()
        .retrieve()
        .then(res => {
          entraineurs.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nom: {},
      pays: {},
      nbJoueurs: {},
      classement: {},
      entraineur: {},
      joueurs: {},
    };
    const v$ = useVuelidate(validationRules, equipe as any);
    v$.value.$validate();

    return {
      equipeService,
      alertService,
      equipe,
      previousState,
      isSaving,
      currentLanguage,
      entraineurs,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.equipe.id) {
        this.equipeService()
          .update(this.equipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApplicationFootballApp.equipe.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.equipeService()
          .create(this.equipe)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApplicationFootballApp.equipe.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
