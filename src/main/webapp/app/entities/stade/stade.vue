<template>
  <div>
    <h2 id="page-heading" data-cy="StadeHeading">
      <span v-text="t$('jhipsterApplicationFootballApp.stade.home.title')" id="stade-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApplicationFootballApp.stade.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'StadeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-stade"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApplicationFootballApp.stade.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stades && stades.length === 0">
      <span v-text="t$('jhipsterApplicationFootballApp.stade.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="stades && stades.length > 0">
      <table class="table table-striped" aria-describedby="stades">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.stade.nom')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.stade.lieu')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stade in stades" :key="stade.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StadeView', params: { stadeId: stade.id } }">{{ stade.id }}</router-link>
            </td>
            <td>{{ stade.nom }}</td>
            <td>{{ stade.lieu }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StadeView', params: { stadeId: stade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StadeEdit', params: { stadeId: stade.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(stade)"
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
          id="jhipsterApplicationFootballApp.stade.delete.question"
          data-cy="stadeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-stade-heading" v-text="t$('jhipsterApplicationFootballApp.stade.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-stade"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeStade()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./stade.component.ts"></script>
