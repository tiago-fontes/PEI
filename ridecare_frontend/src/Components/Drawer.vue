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
    <v-list dense nav expand>
      <v-list-item link to="/">
        <v-list-item-icon>
          <v-icon color="white">mdi-home</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Home</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-group prepend-icon="mdi-garage-variant" color="white" no-action>
        <template v-slot:activator>
          <v-list-item-title>Cars</v-list-item-title>
        </template>

        <v-list-item link to="/my-cars">
          <v-list-item-icon>
            <v-icon color="white">mdi-car</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>My Cars</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item link to="/my-cars/new-car">
          <v-list-item-icon>
            <v-icon color="white">mdi-tooltip-plus</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>New Car</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>

      <v-list-item link to="/events-anomalies">
        <v-list-item-icon>
          <v-icon color="white">mdi-alert</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Events</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item link to="/settings">
        <v-list-item-icon>
          <v-icon color="white">mdi-cog</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Settings</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item link to="/about-project">
        <v-list-item-icon>
          <v-icon color="white">mdi-puzzle-edit</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>About Project</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <template v-slot:append>
      <div class="pa-2">
        <v-btn tile block depressed color="primary" @click="logout">
          <v-icon left dark> mdi-logout </v-icon>
          Log out
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
      drawer: true
    };
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
    }
  }
};
</script>

<style scoped></style>
