import Vue from "vue";
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";
import pt from "vuetify/es5/locale/pt";

Vue.use(Vuetify);

export default new Vuetify({
  lang: {
    locales: { pt },
    current: "pt"
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
