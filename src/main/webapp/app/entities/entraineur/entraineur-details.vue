<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="entraineur">
        <h2 class="jh-entity-heading" data-cy="entraineurDetailsHeading">
          <span v-text="t$('jhipsterApplicationFootballApp.entraineur.detail.title')"></span> {{ entraineur.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.entraineur.nom')"></span>
          </dt>
          <dd>
            <span>{{ entraineur.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.entraineur.numIdent')"></span>
          </dt>
          <dd>
            <span>{{ entraineur.numIdent }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.entraineur.dateNaissance')"></span>
          </dt>
          <dd>
            <span v-if="entraineur.dateNaissance">{{ formatDateLong(entraineur.dateNaissance) }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.entraineur.ancienneEquipe')"></span>
          </dt>
          <dd>
            <span>{{ entraineur.ancienneEquipe }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="entraineur.id"
          :to="{ name: 'EntraineurEdit', params: { entraineurId: entraineur.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entraineur-details.component.ts"></script>
