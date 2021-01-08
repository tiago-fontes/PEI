<template>
  <v-app id="app">
    <component :is="layout">
      <router-view :layout.sync="layout" />
    </component>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => {
    return {
      layout: "div"
    };
  }
};
</script>

<style scoped>
#app {
  background-color: #f5f5f5;
}
</style>
