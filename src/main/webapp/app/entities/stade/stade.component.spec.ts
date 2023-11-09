/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Stade from './stade.vue';
import StadeService from './stade.service';
import AlertService from '@/shared/alert/alert.service';

type StadeComponentType = InstanceType<typeof Stade>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Stade Management Component', () => {
    let stadeServiceStub: SinonStubbedInstance<StadeService>;
    let mountOptions: MountingOptions<StadeComponentType>['global'];

    beforeEach(() => {
      stadeServiceStub = sinon.createStubInstance<StadeService>(StadeService);
      stadeServiceStub.retrieve.resolves({ headers: {} });

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
          stadeService: () => stadeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stadeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Stade, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(stadeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.stades[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: StadeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Stade, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        stadeServiceStub.retrieve.reset();
        stadeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        stadeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeStade();
        await comp.$nextTick(); // clear components

        // THEN
        expect(stadeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(stadeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
