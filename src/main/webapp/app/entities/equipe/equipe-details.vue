<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="equipe">
        <h2 class="jh-entity-heading" data-cy="equipeDetailsHeading">
          <span v-text="t$('jhipsterApplicationFootballApp.equipe.detail.title')"></span> {{ equipe.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.nom')"></span>
          </dt>
          <dd>
            <span>{{ equipe.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.pays')"></span>
          </dt>
          <dd>
            <span>{{ equipe.pays }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.nbJoueurs')"></span>
          </dt>
          <dd>
            <span>{{ equipe.nbJoueurs }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.classement')"></span>
          </dt>
          <dd>
            <span>{{ equipe.classement }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.entraineur')"></span>
          </dt>
          <dd>
            <div v-if="equipe.entraineur">
              <router-link :to="{ name: 'EntraineurView', params: { entraineurId: equipe.entraineur.id } }">{{
                equipe.entraineur.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="equipe.id" :to="{ name: 'EquipeEdit', params: { equipeId: equipe.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./equipe-details.component.ts"></script>
