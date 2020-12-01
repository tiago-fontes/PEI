<template>
  <v-app-bar app clipped-left fixed flat color="white">
    <v-app-bar-nav-icon @click="handlerDrawer"></v-app-bar-nav-icon>
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
  data() {
    return {};
  },
  methods: {
    handlerDrawer() {
      this.$emit("handler-drawer");
    }
  }
};
</script>

<style scoped></style>
