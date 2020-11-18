import Vue from "vue";
import VueRouter from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import NotFound from "../views/NotFound.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Dashboard",
    component: Dashboard
  },
  {
    path: "/my-cars",
    name: "CarList",
    component: () => import("@/views/Car/CarList.vue")
  },
  {
    path: "/my-cars/:carID/details",
    name: "CarDetails",
    component: () => import("@/views/Car/CarDetails.vue")
  },
  {
    path: "/my-cars/new-car",
    name: "NewCar",
    component: () => import("@/views/Car/NewCar.vue")
  },
  {
    path: "/settings",
    name: "Settings",
    component: () => import("@/views/Settings.vue")
  },
  {
    path: "/sign-up",
    name: "SignUp",
    component: () => import("@/views/Auth/SignUp.vue")
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Auth/Login.vue")
  },
  {
    path: "/forgot-password",
    name: "ForgotPwd",
    component: () => import("@/views/Auth/ForgotPwd.vue")
  },
  {
    path: "/404",
    component: NotFound
  },
  {
    path: "*",
    redirect: "/404"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
