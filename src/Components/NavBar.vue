<template>
  <v-app-bar app clipped-left fixed flat color="white">
    <v-app-bar-nav-icon @click.stop="handlerDrawer"></v-app-bar-nav-icon>
    <v-toolbar-title>RideCare</v-toolbar-title>
    <v-spacer></v-spacer>
    <div class="d-flex flex-column align-center">
      <span>Rui Santos</span>
      <span>Share Now</span>
    </div>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: {
    handlerDrawer: { type: Function }
  }
};
</script>

<style scoped></style>
