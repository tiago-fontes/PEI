<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Car(License Plate):</span> AA-AA-AA
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Classification:</span> Smoked Detected
        </v-col>
        <v-col cols="12" sm="12" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Data and Hour:</span> 2020/12/17
          23:00:00
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Gas:</span> 112302
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Humidity:</span> 62.7877953468
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">PM 10:</span> 14.20
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">PM 25:</span> 6.30
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Pressure:</span> 1019.64309576
        </v-col>
        <v-col cols="12" sm="6" md="4" lg="4" class="text-caption">
          <span class="text-subtitle-2">Temperature:</span> 20.234296875
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "ValueDetails",
  props: ["cars"]
};
</script>

<style scoped></style>
