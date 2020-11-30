<template>
  <div class="car-details"></div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
export default {
  name: "CarDetails",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>

<style scoped></style>
