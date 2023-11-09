<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="stade">
        <h2 class="jh-entity-heading" data-cy="stadeDetailsHeading">
          <span v-text="t$('jhipsterApplicationFootballApp.stade.detail.title')"></span> {{ stade.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.stade.nom')"></span>
          </dt>
          <dd>
            <span>{{ stade.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApplicationFootballApp.stade.lieu')"></span>
          </dt>
          <dd>
            <span>{{ stade.lieu }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="stade.id" :to="{ name: 'StadeEdit', params: { stadeId: stade.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./stade-details.component.ts"></script>
