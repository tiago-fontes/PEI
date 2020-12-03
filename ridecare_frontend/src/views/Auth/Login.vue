<template>
  <div class="parent">
    <v-card width="600" class="px-6 py-8">
      <v-card-title>RideCare</v-card-title>
      <v-card-subtitle>Sign in to RideCare</v-card-subtitle>
      <v-card-text>
        <v-form ref="logInForm" @submit.prevent="login">
          <v-text-field
            v-model="email"
            label="Email"
            type="email"
            prepend-icon="mdi-account-circle"
            required
            color="primary"
          ></v-text-field>
          <v-text-field
            v-model="password"
            label="Password"
            :type="showPassword ? 'text' : 'password'"
            prepend-icon="mdi-lock"
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPassword = !showPassword"
            required
            color="primary"
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
                >
                  Sign in
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
      password: "",
      showPassword: false,
      errorMsg: null
    };
  },
  methods: {
    login() {
      console.log("" + this.email + " " + this.password);
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
