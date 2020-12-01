<template>
  <v-app-bar app clipped-left fixed flat color="white">
    <v-app-bar-nav-icon @click.stop="$emit('toggle-drawer')" />
    <v-toolbar-title>RideCare</v-toolbar-title>
    <v-spacer></v-spacer>
    <span class="font-weight-light font-italic text--primary hidden-sm-and-down"
      >Share Now</span
    >
    <v-btn icon class="mx-2">
      <v-badge color="red" dot overlap>
        <v-icon color="black" medium>
          mdi-bell
        </v-icon>
      </v-badge>
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  name: "NavBar",
  data() {
    return {};
  },
  methods: {}
};
</script>

<style scoped></style>
