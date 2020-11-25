<template>
  <div>
    <Drawer drawer="drawer" />
    <NavBar :handlerDrawer="this.handlerDrawer" />
    <v-main>
      <slot />
    </v-main>
  </div>
</template>

<script>
import Drawer from "@/Components/Drawer.vue";
import NavBar from "@/Components/NavBar.vue";

export default {
  name: "MainLayout",
  components: {
    Drawer,
    NavBar
  },
  data() {
    return {
      drawer: null
    };
  },
  methods: {
    handlerDrawer() {
      this.drawer = !this.drawer;
    }
  }
};
</script>

<style scoped></style>
