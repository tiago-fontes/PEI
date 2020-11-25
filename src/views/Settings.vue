<template>
  <div class="settings"></div>
</template>

<script>
import MainLayoutVue from "../Layouts/MainLayout.vue";

export default {
  name: "Settings",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  }
};
</script>
