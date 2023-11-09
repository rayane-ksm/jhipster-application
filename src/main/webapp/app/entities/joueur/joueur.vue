<template>
  <div>
    <h2 id="page-heading" data-cy="JoueurHeading">
      <span v-text="t$('jhipsterApplicationFootballApp.joueur.home.title')" id="joueur-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApplicationFootballApp.joueur.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'JoueurCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-joueur"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApplicationFootballApp.joueur.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && joueurs && joueurs.length === 0">
      <span v-text="t$('jhipsterApplicationFootballApp.joueur.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="joueurs && joueurs.length > 0">
      <table class="table table-striped" aria-describedby="joueurs">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.nom')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.poste')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.numeroMaillot')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.telephone')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.dateNaissance')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.prixTransfer')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApplicationFootballApp.joueur.equipe')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="joueur in joueurs" :key="joueur.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'JoueurView', params: { joueurId: joueur.id } }">{{ joueur.id }}</router-link>
            </td>
            <td>{{ joueur.nom }}</td>
            <td>{{ joueur.poste }}</td>
            <td>{{ joueur.numeroMaillot }}</td>
            <td>{{ joueur.telephone }}</td>
            <td>{{ formatDateShort(joueur.dateNaissance) || '' }}</td>
            <td>{{ joueur.prixTransfer }}</td>
            <td>
              <div v-if="joueur.equipe">
                <router-link :to="{ name: 'EquipeView', params: { equipeId: joueur.equipe.id } }">{{ joueur.equipe.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'JoueurView', params: { joueurId: joueur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'JoueurEdit', params: { joueurId: joueur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(joueur)"
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
          id="jhipsterApplicationFootballApp.joueur.delete.question"
          data-cy="joueurDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-joueur-heading" v-text="t$('jhipsterApplicationFootballApp.joueur.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-joueur"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeJoueur()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./joueur.component.ts"></script>
