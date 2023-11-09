import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import JoueurService from './joueur.service';
import { useDateFormat } from '@/shared/composables';
import { type IJoueur } from '@/shared/model/joueur.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'JoueurDetails',
  setup() {
    const dateFormat = useDateFormat();
    const joueurService = inject('joueurService', () => new JoueurService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const joueur: Ref<IJoueur> = ref({});

    const retrieveJoueur = async joueurId => {
      try {
        const res = await joueurService().find(joueurId);
        joueur.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.joueurId) {
      retrieveJoueur(route.params.joueurId);
    }

    return {
      ...dateFormat,
      alertService,
      joueur,

      previousState,
      t$: useI18n().t,
    };
  },
});
