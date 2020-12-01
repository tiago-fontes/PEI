<template>
  <div>
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
  </div>
</template>

<script>
export default {
  name: "SimpleTable",
  props: ["cars"]
};
</script>

<style scoped></style>
