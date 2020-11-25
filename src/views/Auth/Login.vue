<template>
  <div class="login">ola</div>
</template>

<script>
import AuthLayoutVue from "../../Layouts/AuthLayout.vue";
export default {
  name: "Login",
  created() {
    this.$emit("update:layout", AuthLayoutVue);
  }
};
</script>
