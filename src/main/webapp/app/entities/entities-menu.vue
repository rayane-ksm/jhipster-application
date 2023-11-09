<template>
  <div>
    <b-dropdown-item to="/equipe">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.equipe')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/stade">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.stade')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/entraineur">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.entraineur')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/joueur">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.joueur')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
