<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApplicationFootballApp.stade.home.createOrEditLabel"
          data-cy="StadeCreateUpdateHeading"
          v-text="t$('jhipsterApplicationFootballApp.stade.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="stade.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="stade.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.stade.nom')" for="stade-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="stade-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.stade.lieu')" for="stade-lieu"></label>
            <input
              type="text"
              class="form-control"
              name="lieu"
              id="stade-lieu"
              data-cy="lieu"
              :class="{ valid: !v$.lieu.$invalid, invalid: v$.lieu.$invalid }"
              v-model="v$.lieu.$model"
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
<script lang="ts" src="./stade-update.component.ts"></script>
