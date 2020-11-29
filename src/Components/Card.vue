<template>
  <v-card class="mx-auto" max-width="344" outlined color="green">
    <v-row class="ma-0">
      <v-col sm="12">
        <span>6</span>
      </v-col>
      <v-col sm="12">
        <v-icon>mdi-car</v-icon>
      </v-col>
    </v-row>
    <v-row class="ma-0">
      <span>Cars Online</span>
    </v-row>
  </v-card>
</template>

<script>
export default {
  name: "Card",
  props: ["color", "title", "value"]
};
</script>

<style scoped></style>
