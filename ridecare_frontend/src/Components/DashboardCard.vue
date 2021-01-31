<template>
  <v-card :color="color" class="rounded-xl" dark>
    <v-container>
      <v-row class="ma-0" justify="center">
        <v-col align-self="center" class="text-center">
          <h1>{{ value }}</h1>
        </v-col>
        <v-col align-self="center" class="text-center">
          <v-icon size="75">{{ icon }}</v-icon>
        </v-col>
      </v-row>
      <v-row class="ma-0">
        <v-col class="text-center">
          <h3>{{ description }}</h3>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  name: "DashboardCard",
  props: ["color", "icon", "description", "info", "value"]
};
</script>

<style scoped></style>
