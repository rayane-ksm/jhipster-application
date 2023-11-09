/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EntraineurDetails from './entraineur-details.vue';
import EntraineurService from './entraineur.service';
import AlertService from '@/shared/alert/alert.service';

type EntraineurDetailsComponentType = InstanceType<typeof EntraineurDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entraineurSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Entraineur Management Detail Component', () => {
    let entraineurServiceStub: SinonStubbedInstance<EntraineurService>;
    let mountOptions: MountingOptions<EntraineurDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      entraineurServiceStub = sinon.createStubInstance<EntraineurService>(EntraineurService);

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
          entraineurService: () => entraineurServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entraineurServiceStub.find.resolves(entraineurSample);
        route = {
          params: {
            entraineurId: '' + 123,
          },
        };
        const wrapper = shallowMount(EntraineurDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.entraineur).toMatchObject(entraineurSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entraineurServiceStub.find.resolves(entraineurSample);
        const wrapper = shallowMount(EntraineurDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
