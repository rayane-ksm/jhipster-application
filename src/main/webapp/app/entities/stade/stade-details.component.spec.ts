/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StadeDetails from './stade-details.vue';
import StadeService from './stade.service';
import AlertService from '@/shared/alert/alert.service';

type StadeDetailsComponentType = InstanceType<typeof StadeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stadeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Stade Management Detail Component', () => {
    let stadeServiceStub: SinonStubbedInstance<StadeService>;
    let mountOptions: MountingOptions<StadeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      stadeServiceStub = sinon.createStubInstance<StadeService>(StadeService);

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
          stadeService: () => stadeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stadeServiceStub.find.resolves(stadeSample);
        route = {
          params: {
            stadeId: '' + 123,
          },
        };
        const wrapper = shallowMount(StadeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.stade).toMatchObject(stadeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stadeServiceStub.find.resolves(stadeSample);
        const wrapper = shallowMount(StadeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
