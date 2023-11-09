/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EquipeUpdate from './equipe-update.vue';
import EquipeService from './equipe.service';
import AlertService from '@/shared/alert/alert.service';

import EntraineurService from '@/entities/entraineur/entraineur.service';

type EquipeUpdateComponentType = InstanceType<typeof EquipeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const equipeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EquipeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Equipe Management Update Component', () => {
    let comp: EquipeUpdateComponentType;
    let equipeServiceStub: SinonStubbedInstance<EquipeService>;

    beforeEach(() => {
      route = {};
      equipeServiceStub = sinon.createStubInstance<EquipeService>(EquipeService);
      equipeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          equipeService: () => equipeServiceStub,
          entraineurService: () =>
            sinon.createStubInstance<EntraineurService>(EntraineurService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EquipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.equipe = equipeSample;
        equipeServiceStub.update.resolves(equipeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.update.calledWith(equipeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        equipeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EquipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.equipe = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        equipeServiceStub.find.resolves(equipeSample);
        equipeServiceStub.retrieve.resolves([equipeSample]);

        // WHEN
        route = {
          params: {
            equipeId: '' + equipeSample.id,
          },
        };
        const wrapper = shallowMount(EquipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.equipe).toMatchObject(equipeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        equipeServiceStub.find.resolves(equipeSample);
        const wrapper = shallowMount(EquipeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
