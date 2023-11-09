import { defineComponent, provide } from 'vue';

import EquipeService from './equipe/equipe.service';
import StadeService from './stade/stade.service';
import EntraineurService from './entraineur/entraineur.service';
import JoueurService from './joueur/joueur.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('equipeService', () => new EquipeService());
    provide('stadeService', () => new StadeService());
    provide('entraineurService', () => new EntraineurService());
    provide('joueurService', () => new JoueurService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
