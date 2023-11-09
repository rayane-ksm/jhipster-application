/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EquipeDetails from './equipe-details.vue';
import EquipeService from './equipe.service';
import AlertService from '@/shared/alert/alert.service';

type EquipeDetailsComponentType = InstanceType<typeof EquipeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const equipeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Equipe Management Detail Component', () => {
    let equipeServiceStub: SinonStubbedInstance<EquipeService>;
    let mountOptions: MountingOptions<EquipeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      equipeServiceStub = sinon.createStubInstance<EquipeService>(EquipeService);

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
          equipeService: () => equipeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        equipeServiceStub.find.resolves(equipeSample);
        route = {
          params: {
            equipeId: '' + 123,
          },
        };
        const wrapper = shallowMount(EquipeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.equipe).toMatchObject(equipeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        equipeServiceStub.find.resolves(equipeSample);
        const wrapper = shallowMount(EquipeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
