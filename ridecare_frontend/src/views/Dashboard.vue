<template>
  <div class="dashboard">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Dashboard
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" sm="6" md="3" lg="3">
          <DashboardCard
            color="green"
            icon="mdi-car"
            description="Cars On-line"
          />
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <DashboardCard
            color="red"
            icon="mdi-robot-dead"
            description="Cars Off-line"
          />
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <DashboardCard
            color="#f7d02e"
            icon="mdi-signal-off"
            description="Sensors Disabled"
          />
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <DashboardCard
            color="orange"
            icon="mdi-alert"
            description="Anomalies"
          />
        </v-col>
      </v-row>
      <v-row class="mt-12">
        <v-col cols="12" sm="12" md="6" lg="6">
          <SimpleTable :cars="cars" />
        </v-col>
        <v-col class="text-center" cols="12" sm="12" md="6" lg="6">
          Graph
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import MainLayoutVue from "../Layouts/MainLayout.vue";

import SimpleTable from "../Components/SimpleTable.vue";
import DashboardCard from "../Components/DashboardCard.vue";

export default {
  name: "Dashboard",
  components: {
    SimpleTable,
    DashboardCard
  },
  data() {
    return {
      cars: [
        {
          numberPlate: "AA-00-ZZ",
          status: "Everything is Ok"
        },
        {
          numberPlate: "AA-01-ZZ",
          status: "Sensor Unavailable"
        },
        {
          numberPlate: "AA-02-ZZ",
          status: "Anomaly Detected"
        },
        {
          numberPlate: "AA-03-ZZ",
          status: "Car Offline"
        },
        {
          numberPlate: "AA-04-ZZ",
          status: "Sensor Unavailable"
        }
      ]
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>
