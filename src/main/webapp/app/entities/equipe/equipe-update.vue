<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApplicationFootballApp.equipe.home.createOrEditLabel"
          data-cy="EquipeCreateUpdateHeading"
          v-text="t$('jhipsterApplicationFootballApp.equipe.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="equipe.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="equipe.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.equipe.nom')" for="equipe-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="equipe-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.equipe.pays')" for="equipe-pays"></label>
            <input
              type="text"
              class="form-control"
              name="pays"
              id="equipe-pays"
              data-cy="pays"
              :class="{ valid: !v$.pays.$invalid, invalid: v$.pays.$invalid }"
              v-model="v$.pays.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.equipe.nbJoueurs')" for="equipe-nbJoueurs"></label>
            <input
              type="number"
              class="form-control"
              name="nbJoueurs"
              id="equipe-nbJoueurs"
              data-cy="nbJoueurs"
              :class="{ valid: !v$.nbJoueurs.$invalid, invalid: v$.nbJoueurs.$invalid }"
              v-model.number="v$.nbJoueurs.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.equipe.classement')"
              for="equipe-classement"
            ></label>
            <input
              type="number"
              class="form-control"
              name="classement"
              id="equipe-classement"
              data-cy="classement"
              :class="{ valid: !v$.classement.$invalid, invalid: v$.classement.$invalid }"
              v-model.number="v$.classement.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('jhipsterApplicationFootballApp.equipe.entraineur')"
              for="equipe-entraineur"
            ></label>
            <select class="form-control" id="equipe-entraineur" data-cy="entraineur" name="entraineur" v-model="equipe.entraineur">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="equipe.entraineur && entraineurOption.id === equipe.entraineur.id ? equipe.entraineur : entraineurOption"
                v-for="entraineurOption in entraineurs"
                :key="entraineurOption.id"
              >
                {{ entraineurOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApplicationFootballApp.equipe.stade')" for="equipe-stade"></label>
            <select class="form-control" id="equipe-stade" data-cy="stade" name="stade" v-model="equipe.stade">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="equipe.stade && stadeOption.id === equipe.stade.id ? equipe.stade : stadeOption"
                v-for="stadeOption in stades"
                :key="stadeOption.id"
              >
                {{ stadeOption.id }}
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
<script lang="ts" src="./equipe-update.component.ts"></script>
