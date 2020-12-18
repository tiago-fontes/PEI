import Vue from "vue";
import VueRouter from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import NotFound from "../views/NotFound.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Dashboard",
    component: Dashboard,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/my-cars",
    name: "CarList",
    component: () => import("@/views/Car/CarList.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/my-cars/:carID/details",
    name: "CarDetails",
    component: () => import("@/views/Car/CarDetails.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/my-cars/new-car",
    name: "NewCar",
    component: () => import("@/views/Car/NewCar.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/events-anomalies",
    name: "AnomalyList",
    component: () => import("@/views/Anomaly/AnomalyList.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/events-anomalies/:anomalyID/details",
    name: "AnomalyDetails",
    component: () => import("@/views/Anomaly/AnomalyDetails.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/settings",
    name: "Settings",
    component: () => import("@/views/Settings.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/about-project",
    name: "AboutProject",
    component: () => import("@/views/AboutProject.vue"),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/sign-up",
    name: "SignUp",
    component: () => import("@/views/Auth/SignUp.vue"),
    meta: {
      requiresVisitor: true
    }
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Auth/Login.vue"),
    meta: {
      requiresVisitor: true
    }
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

router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem("user");

  if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn) {
    next("/login");
  } else if (to.matched.some(record => record.meta.requiresVisitor)) {
    if (loggedIn) {
      next("/");
    } else {
      next();
    }
  }
  next();
});

export default router;
