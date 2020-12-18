<template>
  <div class="car-details">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="10"
          lg="11"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          {{ car.licensePlate }}
          <span class="text-caption font-weight-thin">{{ car.year }}</span>
        </v-col>
        <v-col cols="12" sm="2" lg="1">
          <v-btn
            tile
            small
            outlined
            rounded
            color="primary"
            class="text-capitalize"
            block
            >Edit</v-btn
          >
        </v-col>
      </v-row>
      <v-row class="mt-4">
        <v-col cols="12" sm="6" md="6" lg="6" class="d-flex justify-center">
          <v-img
            v-if="car.image != ''"
            contain
            max-height="300"
            max-width="253"
            :src="car.image"
          ></v-img>
          <v-img
            v-else
            contain
            max-height="300"
            max-width="253"
            src="../../assets/car.png"
          ></v-img>
        </v-col>
        <v-col
          cols="12"
          sm="3"
          md="3"
          lg="3"
          class="d-flex justify-center align-center"
        >
          <v-container>
            <v-row justify="center">{{ car.brand }}</v-row>
            <v-row justify="center">{{ car.model }}</v-row>
            <v-row justify="center">
              <v-col
                cols="12"
                sm="4"
                md="4"
                lg="4"
                class="d-flex justify-center pa-1"
                >{{ car.numberOfDoors }} Doors /
                {{ car.numberOfSeats }} Seats</v-col
              >
              <v-col class="d-flex justify-center pa-1">{{
                car.transmission
              }}</v-col>
              <v-col class="d-flex justify-center pa-1">{{ car.fuel }}</v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
      <v-row class="my-4">
        <v-col cols="12" sm="12" md="6" lg="6">
          <v-container>
            <v-row>
              <v-card elevation="0" width="100%">
                <v-card-title> Device </v-card-title>
                <v-card-text class="text-center">
                  <v-container>
                    <v-row>
                      <v-col>Status</v-col>
                      <v-col>
                        <v-icon
                          v-if="car.status.status === 'OFFLINE'"
                          color="red"
                        >
                          mdi-checkbox-blank-circle
                        </v-icon>
                        <v-icon v-else color="green">
                          mdi-checkbox-blank-circle
                        </v-icon>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>Last Validation</v-col>
                      <v-col>{{ car.status.date }}</v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="12" sm="12" md="6" lg="6">
          <v-card elevation="0" width="100%">
            <v-card-title> Events/Anomalies </v-card-title>
            <v-data-table
              :headers="headerEventsandAnomalies"
              :items="car.anomalies"
            >
              <template v-slot:[`item.actions`]="{ item }">
                <router-link
                  :to="{
                    name: 'CarDetails',
                    params: { carID: item.id }
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
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
import axios from "../../../axios";

export default {
  name: "CarDetails",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/car/${this.$route.params.carID}`)
      .then(res => {
        this.car = res.data;
        console.log(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  },
  data() {
    return {
      headerEventsandAnomalies: [
        {
          text: "Event/Anomaly",
          sortable: false,
          value: "classification",
          align: "center"
        },
        {
          text: "Date/Hour",
          sortable: false,
          value: "measurements[0].timeValue",
          align: "center"
        }
      ],
      car: {
        status: {}
      },
      eventAndAnomalies: [],
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
