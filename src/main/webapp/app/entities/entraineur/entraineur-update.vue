<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApplicationFootballApp.entraineur.home.createOrEditLabel"
          data-cy="EntraineurCreateUpdateHeading"
          v-text="t$('jhipsterApplicationFootballApp.entraineur.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="entraineur.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="entraineur.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.entraineur.nom')" for="entraineur-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="entraineur-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.entraineur.numIdent')"
              for="entraineur-numIdent"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numIdent"
              id="entraineur-numIdent"
              data-cy="numIdent"
              :class="{ valid: !v$.numIdent.$invalid, invalid: v$.numIdent.$invalid }"
              v-model.number="v$.numIdent.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.entraineur.dateNaissance')"
              for="entraineur-dateNaissance"
            ></label>
            <div class="d-flex">
              <input
                id="entraineur-dateNaissance"
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
              v-text="t$('jhipsterApplicationFootballApp.entraineur.ancienneEquipe')"
              for="entraineur-ancienneEquipe"
            ></label>
            <input
              type="text"
              class="form-control"
              name="ancienneEquipe"
              id="entraineur-ancienneEquipe"
              data-cy="ancienneEquipe"
              :class="{ valid: !v$.ancienneEquipe.$invalid, invalid: v$.ancienneEquipe.$invalid }"
              v-model="v$.ancienneEquipe.$model"
            />
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
<script lang="ts" src="./entraineur-update.component.ts"></script>
