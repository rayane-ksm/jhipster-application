import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import StadeService from './stade.service';
import { type IStade } from '@/shared/model/stade.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StadeDetails',
  setup() {
    const stadeService = inject('stadeService', () => new StadeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const stade: Ref<IStade> = ref({});

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

    return {
      alertService,
      stade,

      previousState,
      t$: useI18n().t,
    };
  },
});
