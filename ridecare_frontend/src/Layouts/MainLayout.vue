<template>
  <div id="wrapper">
    <Drawer ref="drawer" />
    <NavBar @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer" />
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
    return {};
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

      this.$store.state.stompClient.connect(
        {},
        frame => {
          console.log("Connected: " + frame);
          this.$store.state.stompClient.subscribe(
            `/queue/${this.activeUser.companyName}`,
            tick => {
              console.log("TICK: " + tick);
              console.log("BODY", tick.body);
              //this.received_messages.push(JSON.parse(tick.body).content);
            }
          );
        },
        error => {
          console.log("ERROR DETECTED", error);
        }
      );
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
