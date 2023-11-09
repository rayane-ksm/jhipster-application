import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EquipeService from './equipe.service';
import { type IEquipe } from '@/shared/model/equipe.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Equipe',
  setup() {
    const { t: t$ } = useI18n();
    const equipeService = inject('equipeService', () => new EquipeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const equipes: Ref<IEquipe[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEquipes = async () => {
      isFetching.value = true;
      try {
        const res = await equipeService().retrieve();
        equipes.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEquipes();
    };

    onMounted(async () => {
      await retrieveEquipes();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEquipe) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEquipe = async () => {
      try {
        await equipeService().delete(removeId.value);
        const message = t$('jhipsterApplicationFootballApp.equipe.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEquipes();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      equipes,
      handleSyncList,
      isFetching,
      retrieveEquipes,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEquipe,
      t$,
    };
  },
});
