import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EntraineurService from './entraineur.service';
import { useDateFormat } from '@/shared/composables';
import { type IEntraineur } from '@/shared/model/entraineur.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntraineurDetails',
  setup() {
    const dateFormat = useDateFormat();
    const entraineurService = inject('entraineurService', () => new EntraineurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const entraineur: Ref<IEntraineur> = ref({});

    const retrieveEntraineur = async entraineurId => {
      try {
        const res = await entraineurService().find(entraineurId);
        entraineur.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entraineurId) {
      retrieveEntraineur(route.params.entraineurId);
    }

    return {
      ...dateFormat,
      alertService,
      entraineur,

      previousState,
      t$: useI18n().t,
    };
  },
});
