/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import EntraineurUpdate from './entraineur-update.vue';
import EntraineurService from './entraineur.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type EntraineurUpdateComponentType = InstanceType<typeof EntraineurUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entraineurSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EntraineurUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Entraineur Management Update Component', () => {
    let comp: EntraineurUpdateComponentType;
    let entraineurServiceStub: SinonStubbedInstance<EntraineurService>;

    beforeEach(() => {
      route = {};
      entraineurServiceStub = sinon.createStubInstance<EntraineurService>(EntraineurService);
      entraineurServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          entraineurService: () => entraineurServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(EntraineurUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EntraineurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entraineur = entraineurSample;
        entraineurServiceStub.update.resolves(entraineurSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entraineurServiceStub.update.calledWith(entraineurSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        entraineurServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EntraineurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entraineur = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entraineurServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        entraineurServiceStub.find.resolves(entraineurSample);
        entraineurServiceStub.retrieve.resolves([entraineurSample]);

        // WHEN
        route = {
          params: {
            entraineurId: '' + entraineurSample.id,
          },
        };
        const wrapper = shallowMount(EntraineurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.entraineur).toMatchObject(entraineurSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entraineurServiceStub.find.resolves(entraineurSample);
        const wrapper = shallowMount(EntraineurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
