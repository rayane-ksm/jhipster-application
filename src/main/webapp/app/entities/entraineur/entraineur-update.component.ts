import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EntraineurService from './entraineur.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IEntraineur, Entraineur } from '@/shared/model/entraineur.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntraineurUpdate',
  setup() {
    const entraineurService = inject('entraineurService', () => new EntraineurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entraineur: Ref<IEntraineur> = ref(new Entraineur());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEntraineur = async entraineurId => {
      try {
        const res = await entraineurService().find(entraineurId);
        res.dateNaissance = new Date(res.dateNaissance);
        entraineur.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entraineurId) {
      retrieveEntraineur(route.params.entraineurId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nom: {},
      numIdent: {},
      dateNaissance: {},
      ancienneEquipe: {},
      equipe: {},
    };
    const v$ = useVuelidate(validationRules, entraineur as any);
    v$.value.$validate();

    return {
      entraineurService,
      alertService,
      entraineur,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: entraineur }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.entraineur.id) {
        this.entraineurService()
          .update(this.entraineur)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApplicationFootballApp.entraineur.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.entraineurService()
          .create(this.entraineur)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApplicationFootballApp.entraineur.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
