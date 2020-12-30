<template>
  <div>
    <v-container>
      <v-row>
        <v-col class="font-weight-bold"
          >Only first Measurement of anomaly</v-col
        >
      </v-row>
      <v-row>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Car(License Plate):</span>
          {{ licensePlate }}
          <router-link
            :to="{
              name: 'CarDetails',
              params: { carID: carId }
            }"
          >
            <v-icon small>mdi-open-in-new</v-icon>
          </router-link>
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Classification:</span>
          {{ classification }}
        </v-col>
        <v-col cols="12" sm="12" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Data and Hour:</span>
          {{ first_measurement.timeValue }}
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Gas:</span> {{ first_measurement.gas }}
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Humidity:</span>
          {{ first_measurement.humidity }}
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">PM 10:</span>
          {{ first_measurement.pm10 }}
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">PM 25:</span>
          {{ first_measurement.pm25 }}
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Pressure:</span>
          {{ first_measurement.pressure }}
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Temperature:</span>
          {{ first_measurement.temperature }}
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "ValueDetails",
  props: ["first_measurement", "carId", "classification", "licensePlate"]
};
</script>

<style scoped></style>
