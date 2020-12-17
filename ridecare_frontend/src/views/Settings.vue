<template>
  <div class="settings">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Settings
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <span class="font-weight-bold">
                    Email:
                    <span class="font-weight-light">{{ this.email }}</span>
                  </span>
                </v-row>
                <v-row>
                  <span class="font-weight-bold">
                    Company Name:
                    <span class="font-weight-light">{{
                      this.companyName
                    }}</span>
                  </span>
                </v-row>
                <v-row>
                  <v-col cols="12">
                    <v-btn
                      tile
                      block
                      outlined
                      color="primary"
                      class="text-capitalize"
                      >Edit</v-btn
                    >
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <v-btn tile block color="primary" class="text-capitalize"
                      >Change Password</v-btn
                    >
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Project Version
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <span class="font-weight-bold">
                    Version
                    <span class="font-weight-light">1.0.0</span>
                  </span>
                </v-row>
                <v-row>
                  <span class="font-weight-bold">
                    Last Update
                    <span class="font-weight-light">2020-12-01</span>
                  </span>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from "../../axios";
import MainLayoutVue from "../Layouts/MainLayout.vue";

export default {
  name: "Settings",
  data() {
    return {
      companyName: "",
      email: ""
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/user`)
      .then(res => {
        this.companyName = res.data.companyName;
        this.email = res.data.email;
      })
      .catch(err => {
        console.log(err.response);
      });
  }
};
</script>
