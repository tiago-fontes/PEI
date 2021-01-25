<template>
  <div class="anomalies-details">
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
          >Event / Anomaly -
          <span
            class="text-subtitle-2 light-green--text"
            v-if="this.anomaly.viewed == true"
            >VIEWED</span
          >
          <span class="text-subtitle-2 red--text" v-else>NOT VIEWED</span>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-tabs v-model="tabs" fixed-tabs>
            <v-tab v-for="item in items" :key="item"> {{ item }} </v-tab>
          </v-tabs>
        </v-col>
      </v-row>
      <v-tabs-items v-model="tabs">
        <v-tab-item>
          <v-container>
            <v-row>
              <v-col>
                <ValueDetails
                  :first_measurement="anomaly.duringAnomaly[0]"
                  :carId="anomaly.carId"
                  :classification="anomaly.classification"
                  :licensePlate="anomaly.licensePlate"
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-carousel hide-delimiters>
                  <v-carousel-item>
                    <LineChart
                      name="Gas"
                      :beforeAnomaly="beforeAnomaly.gas"
                      :duringAnomaly="duringAnomaly.gas"
                      :afterAnomaly="afterAnomaly.gas"
                    />
                  </v-carousel-item>
                  <v-carousel-item>
                    <LineChart
                      name="PM 10"
                      :beforeAnomaly="beforeAnomaly.pm10"
                      :duringAnomaly="duringAnomaly.pm10"
                      :afterAnomaly="afterAnomaly.pm10"
                    />
                  </v-carousel-item>
                  <v-carousel-item>
                    <LineChart
                      name="PM 25"
                      :beforeAnomaly="beforeAnomaly.pm25"
                      :duringAnomaly="duringAnomaly.pm25"
                      :afterAnomaly="afterAnomaly.pm25"
                    />
                  </v-carousel-item>
                  <v-carousel-item>
                    <LineChart
                      name="Humidity"
                      :beforeAnomaly="beforeAnomaly.humidity"
                      :duringAnomaly="duringAnomaly.humidity"
                      :afterAnomaly="afterAnomaly.humidity"
                    />
                  </v-carousel-item>
                  <v-carousel-item>
                    <LineChart
                      name="Pressure"
                      :beforeAnomaly="beforeAnomaly.pressure"
                      :duringAnomaly="duringAnomaly.pressure"
                      :afterAnomaly="afterAnomaly.pressure"
                    />
                  </v-carousel-item>
                  <v-carousel-item>
                    <LineChart
                      name="Temperature"
                      :beforeAnomaly="beforeAnomaly.temperature"
                      :duringAnomaly="duringAnomaly.temperature"
                      :afterAnomaly="afterAnomaly.temperature"
                    />
                  </v-carousel-item>
                </v-carousel>
              </v-col>
            </v-row>
            <v-row justify="end">
              <v-col cols="12" class="d-flex justify-end">
                <v-btn
                  tile
                  color="primary"
                  class="text-capitalize"
                  v-if="this.anomaly.viewed != true"
                  @click="markAsSeen"
                >
                  Mark as seen
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>
        <v-tab-item>
          <v-container>
            <v-row>
              <v-col
                ><GeographicDetails :location="anomaly.duringAnomaly[0]"
              /></v-col>
            </v-row>
          </v-container>
        </v-tab-item>
        <v-tab-item>
          <v-container>
            <v-row
              v-for="(classification, index) in alternativeClassification"
              :key="index"
            >
              <v-col class="font-weight-bold">{{
                classification.algorithm
              }}</v-col>
              <v-col class="font-weight-light">
                {{ classification.classification }}
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs-items>
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
import ValueDetails from "../../Components/Anomaly/ValueDetails";
import LineChart from "../../Components/Charts/LineChart";
import GeographicDetails from "../../Components/Anomaly/GeographicDetails";
import axios from "../../../axios";

export default {
  name: "AnomalyDetails",
  components: { ValueDetails, GeographicDetails, LineChart },
  data() {
    return {
      loading: true,
      items: ["Values Details", "Geographic Details", "Other Algorithms"],
      tabs: null,
      snackbar: {
        show: false,
        message: null,
        timeout: 3500,
        success: false,
        color: null
      },
      anomaly: {},
      duringAnomaly: {
        gas: [],
        pm10: [],
        pm25: [],
        humidity: [],
        pressure: [],
        temperature: []
      },
      beforeAnomaly: {
        gas: [],
        pm10: [],
        pm25: [],
        humidity: [],
        pressure: [],
        temperature: []
      },
      afterAnomaly: {
        gas: [],
        pm10: [],
        pm25: [],
        humidity: [],
        pressure: [],
        temperature: []
      },
      alternativeClassification: [],
      otherAlgorithms: {}
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(
        `${process.env.VUE_APP_ROOT_API}/anomaly/${this.$route.params.anomalyID}/detailed`
      )
      .then(res => {
        console.log(res.data);
        this.anomaly = res.data;
        this.alternativeClassification = res.data.alternativeClassification;
        this.transformResponse(res.data);
      })
      .catch(err => {
        //console.log(err);
        this.snackbar.show = true;
        this.snackbar.success = false;
        this.snackbar.message = err;
        this.snackbar.color = "error";
      })
      .finally(() => (this.loading = false));
  },
  methods: {
    transformResponse(data) {
      for (let i = 0; i < data.beforeAnomaly.length; i++) {
        let obj = data.beforeAnomaly[i];
        this.beforeAnomaly.gas.push({
          t: new Date(obj.timeValue),
          y: obj.gas
        });
        this.beforeAnomaly.pm10.push({
          t: new Date(obj.timeValue),
          y: obj.pm10
        });
        this.beforeAnomaly.pm25.push({
          t: new Date(obj.timeValue),
          y: obj.pm25
        });
        this.beforeAnomaly.humidity.push({
          t: new Date(obj.timeValue),
          y: obj.humidity
        });
        this.beforeAnomaly.pressure.push({
          t: new Date(obj.timeValue),
          y: obj.pressure
        });
        this.beforeAnomaly.temperature.push({
          t: new Date(obj.timeValue),
          y: obj.temperature
        });
      }
      for (let i = 0; i < data.duringAnomaly.length; i++) {
        let obj = data.duringAnomaly[i];
        this.duringAnomaly.gas.push({ t: new Date(obj.timeValue), y: obj.gas });
        this.duringAnomaly.pm10.push({
          t: new Date(obj.timeValue),
          y: obj.pm10
        });
        this.duringAnomaly.pm25.push({
          t: new Date(obj.timeValue),
          y: obj.pm25
        });
        this.duringAnomaly.humidity.push({
          t: new Date(obj.timeValue),
          y: obj.humidity
        });
        this.duringAnomaly.pressure.push({
          t: new Date(obj.timeValue),
          y: obj.pressure
        });
        this.duringAnomaly.temperature.push({
          t: new Date(obj.timeValue),
          y: obj.temperature
        });
      }
      for (let i = 0; i < data.afterAnomaly.length; i++) {
        let obj = data.afterAnomaly[i];
        this.afterAnomaly.gas.push({ t: new Date(obj.timeValue), y: obj.gas });
        this.afterAnomaly.pm10.push({
          t: new Date(obj.timeValue),
          y: obj.pm10
        });
        this.afterAnomaly.pm25.push({
          t: new Date(obj.timeValue),
          y: obj.pm25
        });
        this.afterAnomaly.humidity.push({
          t: new Date(obj.timeValue),
          y: obj.humidity
        });
        this.afterAnomaly.pressure.push({
          t: new Date(obj.timeValue),
          y: obj.pressure
        });
        this.afterAnomaly.temperature.push({
          t: new Date(obj.timeValue),
          y: obj.temperature
        });
      }
    },
    markAsSeen() {
      axios
        .patch(
          `${process.env.VUE_APP_ROOT_API}/anomaly/${this.$route.params.anomalyID}/viewed`
        )
        .then(() => {
          //console.log(res.data);
          this.snackbar.show = true;
          this.snackbar.success = true;
          this.snackbar.message = "Event/Anomaly Marked as seen ";
          this.snackbar.color = "success";
        })
        .catch(err => {
          //console.log(err);
          this.snackbar.show = true;
          this.snackbar.success = false;
          this.snackbar.message = err;
          this.snackbar.color = "error";
        });
    }
  }
};
</script>

<style scoped></style>
