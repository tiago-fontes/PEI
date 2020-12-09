<template>
  <div class="parent">
    <v-card width="600" class="px-6 py-8">
      <v-card-title>RideCare</v-card-title>
      <v-card-subtitle>Sign in to RideCare</v-card-subtitle>
      <v-card-text>
        <v-form ref="logInForm" v-model="formValidity" @submit.prevent="login">
          <v-text-field
            v-model="email"
            label="Email"
            type="email"
            prepend-icon="mdi-account-circle"
            color="primary"
            :rules="emailRules"
            required
          ></v-text-field>
          <v-text-field
            v-model="password"
            label="Password"
            :type="showPassword ? 'text' : 'password'"
            prepend-icon="mdi-lock"
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPassword = !showPassword"
            color="primary"
            :rules="passwordRules"
            required
          />
          <v-row justify="end">
            <router-link :to="{ name: 'ForgotPwd' }">
              <v-btn
                tile
                text
                color="primary"
                small
                class="mb-4 text-decoration-underline text-capitalize"
              >
                Forgot Password?
              </v-btn>
            </router-link>
          </v-row>
          <v-divider class="mb-4"></v-divider>
          <v-card-actions>
            <v-container>
              <v-row>
                <v-btn
                  tile
                  type="submit"
                  color="primary"
                  block
                  class="text-none"
                  :disabled="!formValidity"
                >
                  Log in
                </v-btn>
              </v-row>
              <v-row class="mt-4">
                <v-col>
                  <router-link :to="{ name: 'SignUp' }">
                    <v-btn
                      tile
                      type="submit"
                      color="secondary"
                      block
                      small
                      text
                      class="text-none"
                    >
                      New to RideCare? Create an account.
                    </v-btn>
                  </router-link>
                </v-col>
              </v-row>
            </v-container>
          </v-card-actions>
          <v-alert
            v-if="errorMsg"
            class="mt-7 mb-0"
            icon="mdi-shield-lock-outline"
            text
            type="error"
          >
            {{ errorMsg }}
          </v-alert>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import AuthLayoutVue from "../../Layouts/AuthLayout.vue";
export default {
  name: "Login",
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
      showPassword: false,
      errorMsg: null,
      formValidity: false
    };
  },
  methods: {
    login() {
      this.$store
        .dispatch("login", {
          email: this.email,
          password: this.password
        })
        .then(() => {
          this.$router.push({ name: "Dashboard" });
        })
        .catch(err => {
          console.log(err.response);
        });
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
