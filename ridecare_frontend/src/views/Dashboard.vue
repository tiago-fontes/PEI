<template>
  <div class="dashboard">
    <v-btn color="error">Login</v-btn>
  </div>
</template>

<script>
import MainLayoutVue from "../Layouts/MainLayout.vue";
export default {
  name: "Dashboard",
  components: {},
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>
