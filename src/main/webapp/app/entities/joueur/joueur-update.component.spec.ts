/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import JoueurUpdate from './joueur-update.vue';
import JoueurService from './joueur.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import EquipeService from '@/entities/equipe/equipe.service';

type JoueurUpdateComponentType = InstanceType<typeof JoueurUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const joueurSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<JoueurUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Joueur Management Update Component', () => {
    let comp: JoueurUpdateComponentType;
    let joueurServiceStub: SinonStubbedInstance<JoueurService>;

    beforeEach(() => {
      route = {};
      joueurServiceStub = sinon.createStubInstance<JoueurService>(JoueurService);
      joueurServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          joueurService: () => joueurServiceStub,
          equipeService: () =>
            sinon.createStubInstance<EquipeService>(EquipeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(JoueurUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(JoueurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.joueur = joueurSample;
        joueurServiceStub.update.resolves(joueurSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(joueurServiceStub.update.calledWith(joueurSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        joueurServiceStub.create.resolves(entity);
        const wrapper = shallowMount(JoueurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.joueur = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(joueurServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        joueurServiceStub.find.resolves(joueurSample);
        joueurServiceStub.retrieve.resolves([joueurSample]);

        // WHEN
        route = {
          params: {
            joueurId: '' + joueurSample.id,
          },
        };
        const wrapper = shallowMount(JoueurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.joueur).toMatchObject(joueurSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        joueurServiceStub.find.resolves(joueurSample);
        const wrapper = shallowMount(JoueurUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
