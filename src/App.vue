<template>
  <v-app>
    <component :is="layout" class="bg">
      <router-view :layout.sync="layout" />
    </component>
  </v-app>
  <!-- <template>
  <v-app>
    <v-navigation-drawer
      app
      clipped
      fixed
      color="#000000"
      floating
      v-model="drawer"
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
          <v-btn block depressed color="#000">
            <v-icon left dark>
              mdi-logout
            </v-icon>
            Logout
          </v-btn>
        </div>
      </template>
    </v-navigation-drawer>
    <v-app-bar app clipped-left fixed flat color="white">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>RideCare</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="d-flex flex-column align-center">
        <span>Rui Santos</span>
        <span>Share Now</span>
      </div>
    </v-app-bar>
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
    showPassword: false
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
