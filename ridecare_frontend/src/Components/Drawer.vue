<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    clipped
    fixed
    floating
    color="primary"
    dark
  >
    <v-list dense nav>
      <v-list-item
        v-for="item in items"
        :key="item.title"
        link
        :to="item.route"
      >
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
  name: "Drawer",
  data() {
    return {
      drawer: true,
      items: [
        { title: "Home", icon: "mdi-home", route: "/" },
        { title: "Cars", icon: "mdi-car", route: "/my-cars" },
        { title: "Settings", icon: "mdi-cog", route: "/settings" }
      ]
    };
  }
};
</script>

<style scoped></style>
