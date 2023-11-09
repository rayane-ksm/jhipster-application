<template>
  <div>
    <h2 id="page-heading" data-cy="EquipeHeading">
      <span v-text="t$('jhipsterApplicationFootballApp.equipe.home.title')" id="equipe-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApplicationFootballApp.equipe.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EquipeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-equipe"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApplicationFootballApp.equipe.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && equipes && equipes.length === 0">
      <span v-text="t$('jhipsterApplicationFootballApp.equipe.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="equipes && equipes.length > 0">
      <table class="table table-striped" aria-describedby="equipes">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.nom')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.pays')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.nbJoueurs')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.classement')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.entraineur')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.equipe.stade')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="equipe in equipes" :key="equipe.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EquipeView', params: { equipeId: equipe.id } }">{{ equipe.id }}</router-link>
            </td>
            <td>{{ equipe.nom }}</td>
            <td>{{ equipe.pays }}</td>
            <td>{{ equipe.nbJoueurs }}</td>
            <td>{{ equipe.classement }}</td>
            <td>
              <div v-if="equipe.entraineur">
                <router-link :to="{ name: 'EntraineurView', params: { entraineurId: equipe.entraineur.id } }">{{
                  equipe.entraineur.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="equipe.stade">
                <router-link :to="{ name: 'StadeView', params: { stadeId: equipe.stade.id } }">{{ equipe.stade.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EquipeView', params: { equipeId: equipe.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EquipeEdit', params: { equipeId: equipe.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(equipe)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="jhipsterApplicationFootballApp.equipe.delete.question"
          data-cy="equipeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-equipe-heading" v-text="t$('jhipsterApplicationFootballApp.equipe.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-equipe"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEquipe()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./equipe.component.ts"></script>
