<template>
  <v-container>
    <div class="dashboard">
      <v-row class="mt-6">
        <h2>Dashboard</h2>
      </v-row>
      <v-row class="mt-12">
        <v-col>
          <v-card color="green" class="rounded-xl">
            <v-card-title>Online</v-card-title>
          </v-card>
        </v-col>
        <v-col>
          <v-card color="red" class="rounded-xl">
            <v-card-title>cars off</v-card-title>
          </v-card>
        </v-col>
        <v-col>
          <v-card color="yellow" class="rounded-xl">
            <v-card-title>sensors disabled</v-card-title>
          </v-card>
        </v-col>
        <v-col>
          <v-card color="orange" class="rounded-xl">
            <v-card-title>anomalies</v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row class="mt-12">
        <v-col>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">
                    Matrícula
                  </th>
                  <th class="text-center">
                    Estado
                  </th>
                </tr>
              </thead>
              <tbody class="text-center">
                <tr v-for="(car, index) in cars" :key="index">
                  <td>{{ car.numberPlate }}</td>
                  <td>{{ car.status }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
        <v-col class="text-center">
          Graph
        </v-col>
      </v-row>
    </div>
  </v-container>
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
          status: "OK"
        },
        {
          numberPlate: "AA-01-ZZ",
          status: "OFF"
        },
        {
          numberPlate: "AA-02-ZZ",
          status: "ANOMALY DETECTED"
        },
        {
          numberPlate: "AA-03-ZZ",
          status: "SENSOR UNAVAILABLE"
        },
        {
          numberPlate: "AA-04-ZZ",
          status: "OK"
        }
      ]
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>
