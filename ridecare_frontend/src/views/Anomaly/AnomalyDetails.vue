<template>
  <div class="anomalies-details">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
          >Event / Anomaly -
          <span class="text-subtitle-2 light-green--text">VIEWED</span>
        </v-col>
      </v-row>
      <v-row>
        <v-tabs v-model="tabs" fixed-tabs>
          <v-tab v-for="item in items" :key="item"> {{ item }} </v-tab>
        </v-tabs>
      </v-row>
      <v-tabs-items v-model="tabs">
        <v-tab-item>
          <v-container>
            <v-row>
              <v-col><ValueDetails /> </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <LineChart />
              </v-col>
            </v-row>
            <v-row justify="end">
              <v-btn tile color="primary" class="text-capitalize">
                Mark as seen
              </v-btn>
            </v-row>
          </v-container>
        </v-tab-item>
        <v-tab-item>
          <v-container>
            <v-row>
              <v-col><GeographicDetails /></v-col>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
import ValueDetails from "../../Components/Anomaly/ValueDetails";
import LineChart from "../../Components/Charts/LineChart";
import GeographicDetails from "../../Components/Anomaly/GeographicDetails";
//import axios from "../../../axios";

export default {
  name: "AnomalyDetails",
  components: { ValueDetails, LineChart, GeographicDetails },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    /*axios
      .get(`${process.env.VUE_APP_ROOT_API}/car/${this.$route.params.carID}`)
      .then(res => {
        this.car = res.data;
      })
      .catch(err => {
        console.log(err);
      });*/
  },
  data() {
    return {
      items: ["Values Details", "Geographic Details"],
      tabs: null,
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
