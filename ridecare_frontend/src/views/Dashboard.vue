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
            description="RideCare On-line"
          />
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <DashboardCard
            color="red"
            icon="mdi-robot-dead"
            description="RideCare Off-line"
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
            description="Events / Anomalies"
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
import axios from "../../axios";
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
      cars: []
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/car`)
      .then(res => {
        this.cars = res.data;
      })
      .catch(err => {
        console.log(err.response);
      });
  }
};
</script>
