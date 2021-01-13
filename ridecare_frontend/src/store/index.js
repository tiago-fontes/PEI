import Vue from "vue";
import Vuex from "vuex";
import axios from "../../axios";

import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    socket: new SockJS(`${process.env.VUE_APP_ROOT_API}/notifications`),
    stompClient: null
  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.user = userData;
      localStorage.setItem("user", JSON.stringify(userData));
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${userData.token}`;
    },
    CLEAR_USER_DATA() {
      localStorage.removeItem("user");
      // clears state and axios headers by reloading the current page
      location.reload();
    },
    SETUP_SOCKET(state, stompClient) {
      state.stompClient = stompClient;
    },
    DISCONNECT_SOCKET(state) {
      state.stompClient.disconnect();

      state.socket = null;
      state.stompClient = null;
    }
  },
  actions: {
    async signup(_, credentials) {
      try {
        let response = await axios.post(`/register`, credentials);
        return response.data;
      } catch {
        return {
          error: "There was an error. Please try again."
        };
      }
    },
    async login({ commit }, credentials) {
      return await axios.post("/login", credentials).then(({ data }) => {
        commit("SET_USER_DATA", data);
      });
    },
    logout({ commit }) {
      commit("DISCONNECT_SOCKET");
      commit("CLEAR_USER_DATA");
    },
    connectSocket({ commit }) {
      let stompClient = Stomp.over(this.state.socket);
      if (stompClient) {
        commit("SETUP_SOCKET", stompClient);
      }
    },
    disconnectSocket({ commit }) {
      commit("DISCONNECT_SOCKET");
    }
  },
  modules: {},
  getters: {
    loggedIn(state) {
      return !!state.user;
    }
  }
});
