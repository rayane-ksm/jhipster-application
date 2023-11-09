import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EquipeService from './equipe.service';
import { type IEquipe } from '@/shared/model/equipe.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EquipeDetails',
  setup() {
    const equipeService = inject('equipeService', () => new EquipeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const equipe: Ref<IEquipe> = ref({});

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

    return {
      alertService,
      equipe,

      previousState,
      t$: useI18n().t,
    };
  },
});
