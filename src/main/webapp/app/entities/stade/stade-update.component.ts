import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StadeService from './stade.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IStade, Stade } from '@/shared/model/stade.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StadeUpdate',
  setup() {
    const stadeService = inject('stadeService', () => new StadeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const stade: Ref<IStade> = ref(new Stade());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveStade = async stadeId => {
      try {
        const res = await stadeService().find(stadeId);
        stade.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stadeId) {
      retrieveStade(route.params.stadeId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nom: {},
      lieu: {},
      equipe: {},
    };
    const v$ = useVuelidate(validationRules, stade as any);
    v$.value.$validate();

    return {
      stadeService,
      alertService,
      stade,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.stade.id) {
        this.stadeService()
          .update(this.stade)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jhipsterApplicationFootballApp.stade.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.stadeService()
          .create(this.stade)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jhipsterApplicationFootballApp.stade.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
