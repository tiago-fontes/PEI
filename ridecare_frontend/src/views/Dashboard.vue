<template>
  <div class="dashboard">
    <v-text-field
      color="primary"
      loading
      disabled
      v-if="
        loadingAnomalies == true &&
          loadingCars == true &&
          loadingAnomaliesNotSeen == true
      "
    ></v-text-field>
    <v-container
      v-else-if="
        loadingAnomalies == false &&
          loadingCars == false &&
          loadingAnomaliesNotSeen == false
      "
    >
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Dashboard
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-col
              cols="12"
              xs="5"
              sm="5"
              md="4"
              lg="4"
              xl="2"
              v-bind="attrs"
              v-on="on"
            >
              <DashboardCard
                v-ripple
                class="dcard"
                color="green"
                icon="mdi-car"
                description="RideCare On-line"
                :value="onlineCars.length"
                @click.native="getOnlineCars"
              />
            </v-col>
          </template>
          <span>Load online cars into the table</span>
        </v-tooltip>
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-col
              cols="12"
              xs="5"
              sm="5"
              md="4"
              lg="4"
              xl="2"
              v-bind="attrs"
              v-on="on"
            >
              <DashboardCard
                v-ripple
                class="dcard"
                color="red"
                icon="mdi-robot-dead"
                description="RideCare Off-line"
                :value="offlineCars.length"
                @click.native="getOfflineCars"
              />
            </v-col>
          </template>
          <span>Load offline cars into the table</span>
        </v-tooltip>
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-col
              cols="12"
              xs="5"
              sm="5"
              md="4"
              lg="4"
              xl="2"
              v-bind="attrs"
              v-on="on"
            >
              <DashboardCard
                v-ripple
                class="dcard"
                color="orange"
                icon="mdi-alert"
                description="Events"
                :value="anomaliesNotSeen.length"
                @click.native="goToEvents"
              />
            </v-col>
          </template>
          <span>See events</span>
        </v-tooltip>
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
              <PieChart
                :anomaliesByClassification="anomaliesByClassification"
              />
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
      loadingCars: true,
      loadingAnomalies: true,
      loadingAnomaliesNotSeen: true,
      cars: [],
      onlineCars: [],
      offlineCars: [],
      anomalies: [],
      anomaliesNotSeen: [],
      anomaliesByMonth: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      anomaliesByClassification: [],
      error: null
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/car`)
      .then(res => {
        console.log(res);
        this.cars = res.data;
        res.data.map(car => {
          if (car.status.status.toUpperCase() == "OFFLINE") {
            this.offlineCars.push(car);
          } else {
            this.onlineCars.push(car);
          }
        });
      })
      .catch(err => {
        this.error = err;
      })
      .finally(() => (this.loadingCars = false));

    axios
      .get(`${process.env.VUE_APP_ROOT_API}/anomaly/user/all`)
      .then(res => {
        this.anomalies = res.data;
        this.getAnomaliesByClassification();
        this.getAnomaliesByMonth();
      })
      .catch(err => {
        //console.log(err);
        this.error = err;
      })
      .finally(() => (this.loadingAnomalies = false));

    axios
      .get(`${process.env.VUE_APP_ROOT_API}/anomaly/user/latest`)
      .then(res => {
        this.anomaliesNotSeen = res.data;
      })
      .catch(err => {
        this.error = err;
      })
      .finally(() => (this.loadingAnomaliesNotSeen = false));
  },
  methods: {
    getOnlineCars() {
      this.cars = this.onlineCars;
    },
    getOfflineCars() {
      this.cars = this.offlineCars;
    },
    goToEvents() {
      this.$router.push("/events-anomalies");
    },
    updateTable(cardDescription) {
      if (cardDescription == "Ride-Care Online") {
        this.cars = this.onlineCars;
      } else if (cardDescription == "Ride-Care Online") {
        this.cars = this.offlineCars;
      }
    },
    getAnomaliesByClassification() {
      for (let i = 0; i < this.anomalies.length; i++) {
        let classification = this.anomalies[i].classification;
        let position = this.anomaliesByClassification
          .map(function(e) {
            return e.classification;
          })
          .indexOf(classification);

        if (position == -1) {
          this.anomaliesByClassification.push({
            classification: classification,
            value: 1
          });
        } else {
          this.anomaliesByClassification[position].value += 1;
        }
      }
    },
    getAnomaliesByMonth() {
      for (let i = 0; i < this.anomalies.length; i++) {
        let year = new Date().getFullYear();
        let anomalyDate = new Date(this.anomalies[i].measurements[0].timeValue);

        if (year == anomalyDate.getFullYear()) {
          //console.log(anomalyDate.getMonth());
          if (this.anomaliesByMonth[anomalyDate.getMonth()] == undefined) {
            this.anomaliesByMonth[anomalyDate.getMonth()] = 1;
          } else {
            this.anomaliesByMonth[anomalyDate.getMonth()] += 1;
          }
        }
      }
    }
  }
};
</script>

<style scoped>
.dcard {
  cursor: pointer;
}
</style>
