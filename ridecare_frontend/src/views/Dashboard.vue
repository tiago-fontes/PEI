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
      <v-row justify="center">
        <v-col cols="12" xs="5" sm="5" md="4" lg="4" xl="2">
          <DashboardCard
            color="green"
            icon="mdi-car"
            description="RideCare On-line"
            :value="onlineCars.length"
          />
        </v-col>
        <v-col cols="12" xs="5" sm="5" md="4" lg="4" xl="2">
          <DashboardCard
            color="red"
            icon="mdi-robot-dead"
            description="RideCare Off-line"
            :value="offlineCars.length"
          />
        </v-col>
        <!--
          <v-col cols="12" xs="5" sm="5" md="3" lg="3" xl="2">
          <DashboardCard
            color="#f7d02e"
            icon="mdi-signal-off"
            description="Sensors Disabled"
            value="0"
          /> </v-col
        >
        -->
        <v-col cols="12" xs="5" sm="5" md="4" lg="4" xl="2">
          <DashboardCard
            color="orange"
            icon="mdi-alert"
            description="Events"
            :value="anomalies.length"
          />
        </v-col>
      </v-row>
      <v-row class="mt-12">
        <v-col cols="12" sm="12" md="6" lg="6">
          <SimpleTable :cars="cars" />
        </v-col>
        <v-col class="text-center" cols="12" sm="12" md="6" lg="6">
          <v-carousel hide-delimiters>
            <v-carousel-item>
              <BarChart :data="anomaliesByMonth" />
            </v-carousel-item>
            <v-carousel-item>
              <PieChart />
            </v-carousel-item>
          </v-carousel>
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
import BarChart from "../Components/Charts/BarChart";
import PieChart from "../Components/Charts/PieChart";

export default {
  name: "Dashboard",
  components: {
    SimpleTable,
    DashboardCard,
    BarChart,
    PieChart
  },
  data() {
    return {
      cars: [],
      onlineCars: [],
      offlineCars: [],
      anomalies: [],
      anomaliesByMonth: [3, 3, 5, 4, 4, 5, 3, 7, 9, 10, 5, 3]
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
        res.data.map(car => {
          if (car.status == "OFFLINE") {
            this.offlineCars.push(car);
          } else {
            this.onlineCars.push(car);
          }
        });
      })
      .catch(err => {
        console.log(err.response);
      });

    axios
      .get(`${process.env.VUE_APP_ROOT_API}/anomaly/user/latest`)
      .then(res => {
        this.anomalies = res.data;
      })
      .catch(err => {
        console.log(err);
      });
  },
  methods: {
    updateTable(cardDescription) {
      if (cardDescription == "Ride-Care Online") {
        this.cars = this.onlineCars;
      } else if (cardDescription == "Ride-Care Online") {
        this.cars = this.offlineCars;
      }
    }
  }
};
</script>
