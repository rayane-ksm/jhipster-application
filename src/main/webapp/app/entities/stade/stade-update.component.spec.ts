/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StadeUpdate from './stade-update.vue';
import StadeService from './stade.service';
import AlertService from '@/shared/alert/alert.service';

type StadeUpdateComponentType = InstanceType<typeof StadeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stadeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StadeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Stade Management Update Component', () => {
    let comp: StadeUpdateComponentType;
    let stadeServiceStub: SinonStubbedInstance<StadeService>;

    beforeEach(() => {
      route = {};
      stadeServiceStub = sinon.createStubInstance<StadeService>(StadeService);
      stadeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          stadeService: () => stadeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(StadeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stade = stadeSample;
        stadeServiceStub.update.resolves(stadeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stadeServiceStub.update.calledWith(stadeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        stadeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StadeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stade = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stadeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        stadeServiceStub.find.resolves(stadeSample);
        stadeServiceStub.retrieve.resolves([stadeSample]);

        // WHEN
        route = {
          params: {
            stadeId: '' + stadeSample.id,
          },
        };
        const wrapper = shallowMount(StadeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.stade).toMatchObject(stadeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stadeServiceStub.find.resolves(stadeSample);
        const wrapper = shallowMount(StadeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
