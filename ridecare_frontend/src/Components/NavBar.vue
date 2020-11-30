<template>
  <v-app-bar app clipped-left fixed flat color="white">
    <v-app-bar-nav-icon @click="handlerDrawer"></v-app-bar-nav-icon>
    <v-toolbar-title>RideCare</v-toolbar-title>
    <v-spacer></v-spacer>
    <span>Share Now</span>
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
