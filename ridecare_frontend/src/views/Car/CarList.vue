<template>
  <div class="car-list">
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
                'Only On-line Devices',
                'Only Off-line Devices',
                'All Cars'
              ]"
              v-model="statusFilter"
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
                    >View More</v-btn
                  >
                </router-link>
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
    <v-dialog v-model="dialogDelete" max-width="500px">
      <v-card>
        <v-card-title class="text-body-1"
          >Are you sure you want to delete this car?</v-card-title
        >
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="deleteItemConfirm"
            >OK</v-btn
          >
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
        //console.log(res);
        let cars = res.data;
        this.cars = cars.map(car => {
          return {
            id: car.id,
            licensePlate: car.licensePlate,
            brand: car.brand,
            nEventsOrAnomalies: car.anomalies.length,
            status: car.status.status.toLowerCase()
          };
        });
      })
      .catch(err => {
        this.snackbar.show = true;
        this.snackbar.message = err.message;
        this.snackbar.success = false;
        this.snackbar.color = "error";
      })
      .finally(() => (this.loading = false));
  },
  data() {
    return {
      loading: true,
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
          text: "Nº of Events/Anomalies",
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
        color: null
      },
      statusFilter: null,
      dialogDelete: false,
      deleteLicensePlate: null
    };
  },
  watch: {
    dialogDelete(val) {
      val;
    }
  },
  methods: {
    deleteItem(item) {
      //console.log(item);
      this.deleteLicensePlate = item.licensePlate;
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      axios
        .delete(
          `${process.env.VUE_APP_ROOT_API}/car/${this.deleteLicensePlate}`
        )
        .then(res => {
          this.cars = this.cars.filter(function(el) {
            let licensePlate = res.config.url.split("/").slice(-1)[0];
            return el.licensePlate != licensePlate;
          });
          this.dialogDelete = false;
          this.deleteLicensePlate = null;
          this.snackbar.show = true;
          this.snackbar.success = true;
          this.snackbar.message = "Car successfully deleted";
          this.snackbar.color = "success";
        })
        .catch(err => {
          this.dialogDelete = false;
          this.deleteLicensePlate = null;
          this.snackbar.show = true;
          this.snackbar.success = false;
          this.snackbar.message = err.message;
          this.snackbar.color = "error";
        });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.deleteLicensePlate = null;
    }
  },
  computed: {
    filteredItems() {
      if (this.statusFilter != null) {
        return this.cars.filter(item => {
          if (this.statusFilter == "Only Off-line Devices") {
            return item.status.toUpperCase() == "OFFLINE";
          } else if (this.statusFilter == "Only On-line Devices") {
            return item.status.toUpperCase() == "ONLINE";
          } else if (this.statusFilter == "All Cars") {
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
