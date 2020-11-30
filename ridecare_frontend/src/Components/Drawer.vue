<template>
  <v-navigation-drawer
    app
    clipped
    fixed
    color="primary"
    floating
    v-model="myDrawer"
    dark
  >
    <v-list dense nav>
      <v-list-item v-for="item in items" :key="item.title" link>
        <v-list-item-icon>
          <v-icon color="white">{{ item.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <template v-slot:append>
      <div class="pa-2">
        <v-btn block depressed color="primary">
          <v-icon left dark>
            mdi-logout
          </v-icon>
          Logout
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { title: "Home", icon: "mdi-home" },
        { title: "Cars", icon: "mdi-car" },
        { title: "Settings", icon: "mdi-cog" }
      ],
      myDrawer: this.drawer
    };
  },
  props: ["drawer"],
  watch: {
    drawer: function() {
      console.log("aqui");
    } // watch it
  }
};
</script>

<style scoped></style>
