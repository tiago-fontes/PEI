<template>
  <div class="parent">
    <v-card width="600" class="px-6 py-8">
      <v-card-title>RideCare</v-card-title>
      <v-card-subtitle>Sign up to RideCare</v-card-subtitle>
      <v-card-text>
        <v-form ref="signUpForm" @submit.prevent="signup">
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="companyName"
                  label="Company Name"
                  type="text"
                  required
                  color="primary"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="email"
                  label="E-mail"
                  type="text"
                  required
                  color="primary"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="6" md="6" lg="6">
                <v-text-field
                  v-model="password"
                  label="Password"
                  type="password"
                  required
                  color="primary"
                />
              </v-col>
              <v-col cols="12" sm="6" md="6" lg="6">
                <v-text-field
                  v-model="repeatPassword"
                  label="Repeat Password"
                  type="password"
                  required
                  color="primary"
                />
              </v-col>
            </v-row>
            <v-divider class="mb-4"></v-divider>
            <v-row>
              <v-col>
                <v-btn
                  type="submit"
                  color="primary"
                  block
                  tile
                  class="text-none"
                >
                  Create Account
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
    <v-alert
      v-if="errorMsg"
      class="mt-7 mb-0"
      icon="mdi-shield-lock-outline"
      text
      type="error"
    >
      {{ errorMsg }}
    </v-alert>
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
      password: "",
      repeatPassword: "",
      companyName: "",
      errorMsg: null
    };
  },
  methods: {
    signup() {
      console.log(this.data);
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
