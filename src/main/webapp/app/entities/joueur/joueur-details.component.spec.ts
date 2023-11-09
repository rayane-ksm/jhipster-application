/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import JoueurDetails from './joueur-details.vue';
import JoueurService from './joueur.service';
import AlertService from '@/shared/alert/alert.service';

type JoueurDetailsComponentType = InstanceType<typeof JoueurDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const joueurSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Joueur Management Detail Component', () => {
    let joueurServiceStub: SinonStubbedInstance<JoueurService>;
    let mountOptions: MountingOptions<JoueurDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      joueurServiceStub = sinon.createStubInstance<JoueurService>(JoueurService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          joueurService: () => joueurServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        joueurServiceStub.find.resolves(joueurSample);
        route = {
          params: {
            joueurId: '' + 123,
          },
        };
        const wrapper = shallowMount(JoueurDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.joueur).toMatchObject(joueurSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        joueurServiceStub.find.resolves(joueurSample);
        const wrapper = shallowMount(JoueurDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
