<template>
  <div class="parent">
    <v-card width="600" class="px-6 py-8">
      <v-alert
        v-if="errorMsg"
        class="ma-5"
        icon="mdi-shield-lock-outline"
        text
        type="error"
      >
        {{ errorMsg }}
      </v-alert>
      <v-card-title>RideCare</v-card-title>
      <v-card-subtitle>Sign up to RideCare</v-card-subtitle>
      <v-card-text>
        <v-form
          ref="signUpForm"
          v-model="formValidity"
          @submit.prevent="signup"
        >
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="companyName"
                  label="Company Name"
                  type="text"
                  color="primary"
                  :rules="companyNameRules"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="email"
                  label="Email"
                  type="text"
                  color="primary"
                  :rules="emailRules"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="6" md="6" lg="6">
                <v-text-field
                  v-model="password"
                  label="Password"
                  type="password"
                  color="primary"
                  :rules="passwordRules"
                  required
                />
              </v-col>
              <v-col cols="12" sm="6" md="6" lg="6">
                <v-text-field
                  v-model="repeatPassword"
                  label="Repeat Password"
                  type="password"
                  color="primary"
                  :rules="[passwordRules, passwordConfirmationRule]"
                  required
                />
              </v-col>
            </v-row>
            <v-divider class="mb-4"></v-divider>
            <v-row>
              <v-col>
                <v-btn
                  tile
                  type="submit"
                  color="primary"
                  block
                  class="text-none"
                  :disabled="!formValidity"
                >
                  Create Account
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
    <v-bottom-sheet v-model="sheet" persistent>
      <v-sheet class="text-center" height="200px">
        <div class="pt-12 mb-6">
          {{ message }}
        </div>
        <router-link :to="{ name: 'Login' }">
          <v-btn tile color="primary" small class="text-capitalize">
            Log in
          </v-btn>
        </router-link>
      </v-sheet>
    </v-bottom-sheet>
  </div>
</template>

<script>
import AuthLayoutVue from "../../Layouts/AuthLayout.vue";
export default {
  name: "SignUp",
  created() {
    this.$emit("update:layout", AuthLayoutVue);
  },
  data() {
    return {
      email: "",
      emailRules: [
        v => !!v || "Email is required.",
        v =>
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
            v
          ) || "Please insert a valid email."
      ],
      password: "",
      passwordRules: [v => !!v || "Password is required."],
      repeatPassword: "",
      companyName: "",
      companyNameRules: [v => !!v || "Please identify your company."],
      errorMsg: null,
      message: null,
      sheet: false,
      formValidity: false
    };
  },
  computed: {
    passwordConfirmationRule() {
      return this.password === this.repeatPassword || "Passwords must match.";
    }
  },
  methods: {
    async signup() {
      let user = await this.$store.dispatch("signup", {
        email: this.email,
        password: this.password,
        companyName: this.companyName
      });
      console.log("user is ->", user);

      if (user.error) {
        this.errorMsg = user.error;
      } else {
        this.sheet = true;
        this.message = "Welcome, " + user.companyName + "! You can now log in!";
        this.$refs.signUpForm.reset();
      }
    }
  }
};
</script>

<style scoped>
.parent {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
