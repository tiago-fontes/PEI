<template>
  <v-card elevation="0">
    <v-row justify="center">
      <v-col class="d-flex justify-center">
        <v-avatar size="128"><v-img :src="img"></v-img></v-avatar>
      </v-col>
    </v-row>
    <v-row class="text-center">
      <v-col
        cols="12"
        class="py-0 text-subtitle-2 font-weight-bold primary--text"
        align-self="center"
        >{{ name }}</v-col
      >
      <v-col
        cols="12"
        class="py-0 text-caption font-weight-light "
        align-self="center"
        >{{ number }}</v-col
      >
    </v-row>
  </v-card>
</template>

<script>
export default {
  name: "TeamMember",
  props: ["img", "name", "number"]
};
</script>

<style scoped></style>
