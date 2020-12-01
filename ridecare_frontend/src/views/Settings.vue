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
                    <span class="font-weight-light">email@email.com</span>
                  </span>
                </v-row>
                <v-row>
                  <span class="font-weight-bold">
                    Company Name:
                    <span class="font-weight-light">Share Now</span>
                  </span>
                </v-row>
                <v-row>
                  <v-col cols="12">
                    <v-btn
                      block
                      outlined
                      color="primary"
                      class="text-capitalize"
                      >Edit</v-btn
                    >
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <v-btn block color="primary" class="text-capitalize"
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

      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          About the Project
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    >Project in partnership with
                    <span class="font-weight-bold">Bosch(Braga)</span> and
                    <span class="font-weight-bold">Universidade do Minho</span>
                    within the scope of the Computer Engineering Project course
                    of the Masters in Computer Engineering.
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    Coordenador: André Ferreira
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0" cols="12">
                    Participantes:
                  </v-col>
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
import MainLayoutVue from "../Layouts/MainLayout.vue";

export default {
  name: "Settings",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>
