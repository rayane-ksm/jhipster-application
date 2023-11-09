<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="joueur">
        <h2 class="jh-entity-heading" data-cy="joueurDetailsHeading">
          <span v-text="t$('jhipsterApplicationFootballApp.joueur.detail.title')"></span> {{ joueur.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.nom')"></span>
          </dt>
          <dd>
            <span>{{ joueur.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.poste')"></span>
          </dt>
          <dd>
            <span>{{ joueur.poste }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.numeroMaillot')"></span>
          </dt>
          <dd>
            <span>{{ joueur.numeroMaillot }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.telephone')"></span>
          </dt>
          <dd>
            <span>{{ joueur.telephone }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.dateNaissance')"></span>
          </dt>
          <dd>
            <span v-if="joueur.dateNaissance">{{ formatDateLong(joueur.dateNaissance) }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.prixTransfer')"></span>
          </dt>
          <dd>
            <span>{{ joueur.prixTransfer }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.equipe')"></span>
          </dt>
          <dd>
            <div v-if="joueur.equipe">
              <router-link :to="{ name: 'EquipeView', params: { equipeId: joueur.equipe.id } }">{{ joueur.equipe.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="joueur.id" :to="{ name: 'JoueurEdit', params: { joueurId: joueur.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./joueur-details.component.ts"></script>
