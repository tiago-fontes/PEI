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
          AA-BB-CC <span class="text-caption font-weight-thin">2019</span>
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
            contain
            max-height="300"
            max-width="253"
            src="../../assets/mercedes.jpg"
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
            <v-row justify="center">Mercedes Benz</v-row>
            <v-row justify="center">Class A 180</v-row>
            <v-row justify="center">
              <v-col
                cols="12"
                sm="4"
                md="4"
                lg="4"
                class="d-flex justify-center pa-1"
                >5 Doors</v-col
              >
              <v-col class="d-flex justify-center pa-1">Automatic</v-col>
              <v-col class="d-flex justify-center pa-1">Diesel</v-col>
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
                      <v-col
                        ><v-icon color="green"
                          >mdi-checkbox-blank-circle</v-icon
                        ></v-col
                      >
                    </v-row>
                    <v-row>
                      <v-col>Last Validation</v-col>
                      <v-col>2020-12-03 10:30:00</v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-row>
            <v-row>
              <v-card elevation="0" width="100%">
                <v-card-title> Sensors </v-card-title>
                <v-data-table
                  :headers="headerSensor"
                  :items="sensors"
                  hide-default-footer
                >
                </v-data-table>
              </v-card>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="12" sm="12" md="6" lg="6">
          <v-card elevation="0" width="100%">
            <v-card-title> Events/Anomalies </v-card-title>
            <v-data-table
              :headers="headerEventandAnomalies"
              :items="eventAndAnomalies"
            >
            </v-data-table>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
export default {
  name: "CarDetails",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  data() {
    return {
      headerSensor: [
        { text: "Sensor", sortable: false, value: "sensor", align: "center" },
        { text: "Status", sortable: false, value: "status", align: "center" }
      ],
      headerEventandAnomalies: [
        {
          text: "Event/Anomaly",
          sortable: false,
          value: "eventOrAnomaly",
          align: "center"
        },
        {
          text: "Date/Hour",
          sortable: false,
          value: "dateAndHour",
          align: "center"
        }
      ],
      sensors: [
        { sensor: "Sensor A", status: "Ok" },
        { sensor: "Sensor A", status: "Ok" }
      ],
      eventAndAnomalies: [
        {
          eventOrAnomaly: "Smoke detected",
          dateAndHour: "2020-12-03 10:30:00"
        },
        {
          eventOrAnomaly: "Smoke detected",
          dateAndHour: "2020-12-03 10:30:00"
        },
        {
          eventOrAnomaly: "Smoke detected",
          dateAndHour: "2020-12-03 10:30:00"
        },
        {
          eventOrAnomaly: "Smoke detected",
          dateAndHour: "2020-12-03 10:30:00"
        },
        {
          eventOrAnomaly: "Smoke detected",
          dateAndHour: "2020-12-03 10:30:00"
        },
        { eventOrAnomaly: "Smoke detected", dateAndHour: "2020-12-03 10:30:00" }
      ]
    };
  }
};
</script>

<style scoped></style>
