<template>
  <div class="anomaly-list">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
          >Events/Anomalies</v-col
        >
      </v-row>
      <v-row>
        <v-col>
          <v-card elevation="0" class="pa-4">
            <v-card-title>
              <v-spacer></v-spacer>
              <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
              ></v-text-field>
            </v-card-title>
            <v-data-table
              :headers="headers"
              :items="anomalies"
              :search="search"
            >
              <template v-slot:[`item.actions`]="{ item }">
                <router-link
                  :to="{
                    name: 'AnomalyDetail',
                    params: { anomalyId: item.id }
                  }"
                >
                  <v-btn
                    tile
                    small
                    text
                    class="text-decoration-underline text-capitalize"
                    color="primary"
                    >View Details</v-btn
                  >
                </router-link>
              </template>
            </v-data-table>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-snackbar
      v-model="snackbar.show"
      :timeout="snackbar.timeout"
      top
      right
      :color="snackbar.color"
    >
      {{ snackbar.message }}
    </v-snackbar>
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
//import axios from "../../../axios";

export default {
  name: "AnomalyDetails",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    /*axios
      .get(`${process.env.VUE_APP_ROOT_API}/car`)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });*/
  },
  data() {
    return {
      search: "",
      headers: [
        {
          text: "Car(License Plate)",
          sortable: false,
          value: "licensePlate",
          align: "center"
        },
        {
          text: "Classification",
          sortable: false,
          value: "classification",
          align: "center"
        },
        { text: "Viewed", value: "viewed", align: "center" },
        { text: "Data/Hour", value: "timestamp", sortable: false }
      ],
      anomalies: [],
      snackbar: {
        show: false,
        message: null,
        timeout: 3500,
        success: false,
        color: null
      }
    };
  }
};
</script>

<style scoped></style>
