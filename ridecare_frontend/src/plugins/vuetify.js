import Vue from "vue";
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";
import en from "vuetify/es5/locale/en";

Vue.use(Vuetify);

export default new Vuetify({
  lang: {
    locales: { en },
    current: "en"
  },
  theme: {
    themes: {
      light: {
        primary: "#1D2430",
        warning: "#ffb74d",
        error: "#f44336"
      }
    }
  }
});
