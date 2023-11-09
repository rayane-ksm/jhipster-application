import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Equipe = () => import('@/entities/equipe/equipe.vue');
const EquipeUpdate = () => import('@/entities/equipe/equipe-update.vue');
const EquipeDetails = () => import('@/entities/equipe/equipe-details.vue');

const Entraineur = () => import('@/entities/entraineur/entraineur.vue');
const EntraineurUpdate = () => import('@/entities/entraineur/entraineur-update.vue');
const EntraineurDetails = () => import('@/entities/entraineur/entraineur-details.vue');

const Joueur = () => import('@/entities/joueur/joueur.vue');
const JoueurUpdate = () => import('@/entities/joueur/joueur-update.vue');
const JoueurDetails = () => import('@/entities/joueur/joueur-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'equipe',
      name: 'Equipe',
      component: Equipe,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe/new',
      name: 'EquipeCreate',
      component: EquipeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe/:equipeId/edit',
      name: 'EquipeEdit',
      component: EquipeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'equipe/:equipeId/view',
      name: 'EquipeView',
      component: EquipeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entraineur',
      name: 'Entraineur',
      component: Entraineur,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entraineur/new',
      name: 'EntraineurCreate',
      component: EntraineurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entraineur/:entraineurId/edit',
      name: 'EntraineurEdit',
      component: EntraineurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entraineur/:entraineurId/view',
      name: 'EntraineurView',
      component: EntraineurDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'joueur',
      name: 'Joueur',
      component: Joueur,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'joueur/new',
      name: 'JoueurCreate',
      component: JoueurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'joueur/:joueurId/edit',
      name: 'JoueurEdit',
      component: JoueurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'joueur/:joueurId/view',
      name: 'JoueurView',
      component: JoueurDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
