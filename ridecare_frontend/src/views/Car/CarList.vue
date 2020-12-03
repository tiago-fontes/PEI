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
            <v-data-table :headers="headers" :items="cars" :search="search">
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
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";

export default {
  name: "CarList",
  created() {
    this.$emit("update:layout", MainLayoutVue);
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
        { text: "Rented", sortable: false, value: "rented", align: "center" },
        { text: "Nº of Anomalies", value: "nAnomalies", align: "center" },
        { text: "", value: "actions", sortable: false }
      ],
      cars: [
        {
          licensePlate: "AA-00-BB",
          brand: "Mercedes",
          rented: "Yes",
          nAnomalies: 0
        },
        {
          licensePlate: "AA-01-BB",
          brand: "BMW",
          rented: "No",
          nAnomalies: 0
        },
        {
          licensePlate: "AA-02-BB",
          brand: "Honda",
          rented: "Yes",
          nAnomalies: 2
        },
        {
          licensePlate: "AA-03-BB",
          brand: "Mercedes",
          rented: "Yes",
          nAnomalies: 3
        },
        {
          licensePlate: "AA-04-BB",
          brand: "Mercedes",
          rented: "No",
          nAnomalies: 3
        },
        {
          licensePlate: "AA-05-BB",
          brand: "Mercedes",
          rented: "Yes",
          nAnomalies: 9
        }
      ]
    };
  },
  methods: {
    newCar() {
      console.log("New Car");
    },
    editItem(item) {
      console.log(item);
    },
    deleteItem(item) {
      console.log(item);
    },
    viewItem(item) {
      console.log(item);
    }
  }
};
</script>

<style scoped></style>
