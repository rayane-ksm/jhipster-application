/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Equipe from './equipe.vue';
import EquipeService from './equipe.service';
import AlertService from '@/shared/alert/alert.service';

type EquipeComponentType = InstanceType<typeof Equipe>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Equipe Management Component', () => {
    let equipeServiceStub: SinonStubbedInstance<EquipeService>;
    let mountOptions: MountingOptions<EquipeComponentType>['global'];

    beforeEach(() => {
      equipeServiceStub = sinon.createStubInstance<EquipeService>(EquipeService);
      equipeServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          equipeService: () => equipeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Equipe, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(equipeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.equipes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: EquipeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Equipe, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        equipeServiceStub.retrieve.reset();
        equipeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        equipeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEquipe();
        await comp.$nextTick(); // clear components

        // THEN
        expect(equipeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(equipeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
