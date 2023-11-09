import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import JoueurService from './joueur.service';
import { type IJoueur } from '@/shared/model/joueur.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Joueur',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const joueurService = inject('joueurService', () => new JoueurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const joueurs: Ref<IJoueur[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveJoueurs = async () => {
      isFetching.value = true;
      try {
        const res = await joueurService().retrieve();
        joueurs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveJoueurs();
    };

    onMounted(async () => {
      await retrieveJoueurs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IJoueur) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeJoueur = async () => {
      try {
        await joueurService().delete(removeId.value);
        const message = t$('jhipsterApplicationFootballApp.joueur.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveJoueurs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      joueurs,
      handleSyncList,
      isFetching,
      retrieveJoueurs,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeJoueur,
      t$,
    };
  },
});
