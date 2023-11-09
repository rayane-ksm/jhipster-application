import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import StadeService from './stade.service';
import { type IStade } from '@/shared/model/stade.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Stade',
  setup() {
    const { t: t$ } = useI18n();
    const stadeService = inject('stadeService', () => new StadeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const stades: Ref<IStade[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveStades = async () => {
      isFetching.value = true;
      try {
        const res = await stadeService().retrieve();
        stades.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveStades();
    };

    onMounted(async () => {
      await retrieveStades();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IStade) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeStade = async () => {
      try {
        await stadeService().delete(removeId.value);
        const message = t$('jhipsterApplicationFootballApp.stade.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveStades();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      stades,
      handleSyncList,
      isFetching,
      retrieveStades,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeStade,
      t$,
    };
  },
});
