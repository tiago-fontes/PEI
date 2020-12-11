<template>
  <div class="car-list">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="10"
          lg="11"
          class="text-center text-sm-left text-h6 font-weight-bold"
          >My Cars</v-col
        >
        <v-col cols="12" sm="2" lg="1">
          <router-link :to="{ name: 'NewCar' }">
            <v-btn tile small color="primary" class="text-capitalize" block
              >New Car</v-btn
            >
          </router-link>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card elevation="0" class="pa-4">
            <v-select
              label="Filter"
              :items="[
                'Only On-line Device',
                'Only Off-line Device',
                'All Cars'
              ]"
              v-model="filteredCars"
            ></v-select>
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
              :items="filteredItems"
              :search="search"
            >
              <template v-slot:[`item.actions`]="{ item }">
                <v-btn
                  tile
                  small
                  text
                  @click="viewItem(item)"
                  class="text-decoration-underline text-capitalize"
                  color="primary"
                  >View More</v-btn
                >
                <v-btn
                  tile
                  small
                  text
                  @click="editItem(item)"
                  class="text-decoration-underline text-capitalize"
                  color="primary"
                  >Edit</v-btn
                >
                <v-btn
                  tile
                  small
                  text
                  @click="deleteItem(item)"
                  class="text-decoration-underline text-capitalize"
                  color="primary"
                  >Delete</v-btn
                >
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
  name: "CarList",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/car`)
      .then(res => {
        let cars = res.data;
        this.cars = cars.map(car => {
          return {
            id: car.id,
            licensePlate: car.licensePlate,
            brand: car.brand,
            nEventsOrAnomalies: car.anomalies.length,
            status: "Online"
          };
        });
      })
      .catch(err => {
        this.snackbar.show = true;
        this.snackbar.message = err.message;
        this.snackbar.success = false;
      });
  },
  data() {
    return {
      search: "",
      headers: [
        {
          text: "License Plate",
          sortable: false,
          value: "licensePlate",
          align: "center"
        },
        { text: "Brand", sortable: false, value: "brand", align: "center" },
        {
          text: "Nº of Eventes/Anomalies",
          value: "nEventsOrAnomalies",
          align: "center"
        },
        { text: "Status (On or Off)", value: "status", align: "center" },
        { text: "", value: "actions", sortable: false }
      ],
      cars: [],
      snackbar: {
        show: false,
        message: null,
        timeout: 3500,
        success: false,
        color: "error"
      },
      filteredCars: null
    };
  },
  methods: {
    editItem(item) {
      console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    }
  },
  computed: {
    filteredItems() {
      if (this.filteredCars != null) {
        return this.cars.filter(item => {
          if (this.filteredCars == "Only On-line Device") {
            return !this.filteredCars || item.status == "Online";
          } else if (this.filteredCars == "Only On-line Device") {
            return !this.filteredCars || item.status == "Offline";
          } else if (this.filteredCars == "All Cars") {
            return this.cars;
          }
        });
      }
      return this.cars;
    }
  }
};
</script>

<style scoped></style>
