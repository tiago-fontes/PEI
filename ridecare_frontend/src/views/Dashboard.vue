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
          <v-card color="green" class="rounded-xl">
            <v-card-title>cars online</v-card-title>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <v-card color="red" class="rounded-xl">
            <v-card-title>cars off</v-card-title>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <v-card color="yellow" class="rounded-xl">
            <v-card-title>sensors disabled</v-card-title>
          </v-card>
        </v-col>
        <v-col cols="12" sm="6" md="3" lg="3">
          <v-card color="orange" class="rounded-xl">
            <v-card-title>anomalies</v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row class="mt-12">
        <v-col cols="12" sm="12" md="6" lg="6">
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">
                    License Plate
                  </th>
                  <th class="text-center">
                    Status
                  </th>
                </tr>
              </thead>
              <tbody class="text-center">
                <tr v-for="(car, index) in cars" :key="index">
                  <td>{{ car.numberPlate }}</td>
                  <td>
                    <v-tooltip right>
                      <template v-slot:activator="{ on, attrs }">
                        <span v-bind="attrs" v-on="on">
                          <v-icon color="red">mdi-checkbox-blank-circle</v-icon>
                        </span>
                      </template>
                      <span>Car Offline</span>
                    </v-tooltip>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
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
export default {
  name: "Dashboard",
  components: {},
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
