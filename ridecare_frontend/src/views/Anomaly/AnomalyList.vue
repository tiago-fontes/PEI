<template>
  <div class="anomaly-list">
    <v-text-field
      color="primary"
      loading
      disabled
      v-if="loading == true"
    ></v-text-field>
    <v-container v-else>
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
                    name: 'AnomalyDetails',
                    params: { anomalyID: item.id }
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
import axios from "../../../axios";

export default {
  name: "AnomalyDetails",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/anomaly/user/all`)
      .then(res => {
        console.log(res.data);
        this.anomalies = res.data;
      })
      .catch(err => {
        console.log(err);
      })
      .finally(() => (this.loading = false));
  },
  data() {
    return {
      loading: true,
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
        {
          text: "Data/Hour",
          value: "measurements[0].timeValue",
          sortable: false,
          align: "center"
        },
        { text: "", value: "actions", sortable: false }
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
