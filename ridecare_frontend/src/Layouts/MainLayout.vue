<template>
  <div id="wrapper">
    <Drawer ref="drawer" />
    <NavBar @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer" />
    <v-snackbar
      v-if="activeMessage"
      v-model="show"
      top
      right
      timeout="9000"
      color="#fdc500"
      text
    >
      <v-row dense justify="center" align="center">
        <v-icon left color="#fdc500">mdi-bell</v-icon>
        <router-link
          v-if="activeMessage"
          :to="{
            name: 'AnomalyDetails',
            params: { anomalyID: activeMessage.id }
          }"
        >
          New event detected on {{ activeMessage.licensePlate }}
        </router-link>
        <v-btn text small @click.native="show = false" class="ml-2">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-row>
    </v-snackbar>
    <div id="main_wrapper">
      <v-main class="MainLayout__main">
        <slot />
      </v-main>
    </div>
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
      show: false,
      activeMessage: null
    };
  },
  computed: {
    activeUser() {
      return this.$store.state.user;
    }
  },
  mounted() {
    this.connect();
  },
  methods: {
    async connect() {
      await this.$store.dispatch("connectSocket");
      let that = this;
      await this.$store.state.stompClient.connect(
        {},
        frame => {
          console.log("Connected: " + frame);
          this.$store.state.stompClient.subscribe(
            `/queue/${this.activeUser.companyName}`,
            tick => {
              that.showMessage(tick.body);
            }
          );
        },
        error => {
          console.log("ERROR DETECTED", error);
        }
      );
    },
    showMessage(msg) {
      this.show = true;
      this.activeMessage = JSON.parse(msg);
      setTimeout(() => {
        this.show = false;
        this.activeMessage = null;
      }, 9000);
    }
  }
};
</script>

<style>
#wrapper {
  height: 100%;
}

#main_wrapper {
  display: flex;
  align-items: center;
  height: 100%;
}

.MainLayout__main > div:first-child {
  min-height: 89vh;
  margin: 20px;
  padding: 10px;
  -webkit-box-shadow: 0px 0px 15px 5px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0px 0px 15px 5px rgba(0, 0, 0, 0.2);
  box-shadow: 0px 0px 15px 5px rgba(0, 0, 0, 0.2);
  background-color: white;
}
</style>
