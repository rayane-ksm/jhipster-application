<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApplicationFootballApp.joueur.home.createOrEditLabel"
          data-cy="JoueurCreateUpdateHeading"
          v-text="t$('jhipsterApplicationFootballApp.joueur.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="joueur.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="joueur.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.joueur.nom')" for="joueur-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="joueur-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.joueur.poste')" for="joueur-poste"></label>
            <input
              type="text"
              class="form-control"
              name="poste"
              id="joueur-poste"
              data-cy="poste"
              :class="{ valid: !v$.poste.$invalid, invalid: v$.poste.$invalid }"
              v-model="v$.poste.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.joueur.numeroMaillot')"
              for="joueur-numeroMaillot"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numeroMaillot"
              id="joueur-numeroMaillot"
              data-cy="numeroMaillot"
              :class="{ valid: !v$.numeroMaillot.$invalid, invalid: v$.numeroMaillot.$invalid }"
              v-model.number="v$.numeroMaillot.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.joueur.telephone')" for="joueur-telephone"></label>
            <input
              type="text"
              class="form-control"
              name="telephone"
              id="joueur-telephone"
              data-cy="telephone"
              :class="{ valid: !v$.telephone.$invalid, invalid: v$.telephone.$invalid }"
              v-model="v$.telephone.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.joueur.dateNaissance')"
              for="joueur-dateNaissance"
            ></label>
            <div class="d-flex">
              <input
                id="joueur-dateNaissance"
                data-cy="dateNaissance"
                type="datetime-local"
                class="form-control"
                name="dateNaissance"
                :class="{ valid: !v$.dateNaissance.$invalid, invalid: v$.dateNaissance.$invalid }"
                :value="convertDateTimeFromServer(v$.dateNaissance.$model)"
                @change="updateInstantField('dateNaissance', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.joueur.prixTransfer')"
              for="joueur-prixTransfer"
            ></label>
            <input
              type="number"
              class="form-control"
              name="prixTransfer"
              id="joueur-prixTransfer"
              data-cy="prixTransfer"
              :class="{ valid: !v$.prixTransfer.$invalid, invalid: v$.prixTransfer.$invalid }"
              v-model.number="v$.prixTransfer.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.joueur.equipe')" for="joueur-equipe"></label>
            <select class="form-control" id="joueur-equipe" data-cy="equipe" name="equipe" v-model="joueur.equipe">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="joueur.equipe && equipeOption.id === joueur.equipe.id ? joueur.equipe : equipeOption"
                v-for="equipeOption in equipes"
                :key="equipeOption.id"
              >
                {{ equipeOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./joueur-update.component.ts"></script>
