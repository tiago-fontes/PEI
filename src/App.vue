<template>
  <v-app>
    <component :is="layout" class="bg">
      <router-view :layout.sync="layout" />
    </component>
  </v-app>
  <!-- <template>
  <v-app>
    <v-main>
      teste
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({
    drawer: null,
    items: [
      { title: "Home", icon: "mdi-home" },
      { title: "Cars", icon: "mdi-car" },
      { title: "Settings", icon: "mdi-cog" }
    ],
  })
};
</script>
 -->
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

<style></style>
