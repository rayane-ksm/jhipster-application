/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Joueur from './joueur.vue';
import JoueurService from './joueur.service';
import AlertService from '@/shared/alert/alert.service';

type JoueurComponentType = InstanceType<typeof Joueur>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Joueur Management Component', () => {
    let joueurServiceStub: SinonStubbedInstance<JoueurService>;
    let mountOptions: MountingOptions<JoueurComponentType>['global'];

    beforeEach(() => {
      joueurServiceStub = sinon.createStubInstance<JoueurService>(JoueurService);
      joueurServiceStub.retrieve.resolves({ headers: {} });

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
          joueurService: () => joueurServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        joueurServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Joueur, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(joueurServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.joueurs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: JoueurComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Joueur, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        joueurServiceStub.retrieve.reset();
        joueurServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        joueurServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeJoueur();
        await comp.$nextTick(); // clear components

        // THEN
        expect(joueurServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(joueurServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
