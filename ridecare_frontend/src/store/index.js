import Vue from "vue";
import Vuex from "vuex";
import axios from "../../axios";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null
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
      commit("CLEAR_USER_DATA");
    }
  },
  modules: {},
  getters: {
    loggedIn(state) {
      return !!state.user;
    }
  }
});
