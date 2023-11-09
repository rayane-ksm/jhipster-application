import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EntraineurService from './entraineur.service';
import { type IEntraineur } from '@/shared/model/entraineur.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entraineur',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const entraineurService = inject('entraineurService', () => new EntraineurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entraineurs: Ref<IEntraineur[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEntraineurs = async () => {
      isFetching.value = true;
      try {
        const res = await entraineurService().retrieve();
        entraineurs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEntraineurs();
    };

    onMounted(async () => {
      await retrieveEntraineurs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEntraineur) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEntraineur = async () => {
      try {
        await entraineurService().delete(removeId.value);
        const message = t$('jhipsterApplicationFootballApp.entraineur.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEntraineurs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      entraineurs,
      handleSyncList,
      isFetching,
      retrieveEntraineurs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEntraineur,
      t$,
    };
  },
});
