/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Entraineur from './entraineur.vue';
import EntraineurService from './entraineur.service';
import AlertService from '@/shared/alert/alert.service';

type EntraineurComponentType = InstanceType<typeof Entraineur>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Entraineur Management Component', () => {
    let entraineurServiceStub: SinonStubbedInstance<EntraineurService>;
    let mountOptions: MountingOptions<EntraineurComponentType>['global'];

    beforeEach(() => {
      entraineurServiceStub = sinon.createStubInstance<EntraineurService>(EntraineurService);
      entraineurServiceStub.retrieve.resolves({ headers: {} });

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
          entraineurService: () => entraineurServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entraineurServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Entraineur, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(entraineurServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.entraineurs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: EntraineurComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Entraineur, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        entraineurServiceStub.retrieve.reset();
        entraineurServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        entraineurServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEntraineur();
        await comp.$nextTick(); // clear components

        // THEN
        expect(entraineurServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(entraineurServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
