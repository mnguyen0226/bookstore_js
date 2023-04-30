import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoryView from "@/views/CategoryView.vue";
import CartView from "@/views/CartView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import ConfirmationVue from "@/views/ConfirmationView.vue";
import NotFound from "@/views/NotFound.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/home",
      name: "home-view-with-home",
      component: HomeView,
    },
    {
      path: "/index.html",
      name: "home-view-index",
      component: HomeView,
    },
    {
      path: "/category/:name",
      name: "category-view",
      component: CategoryView,
      props: true,
    },
    {
      path: "/category",
      redirect: '/category/Jordan%20Petersons%20Book'
    },
    {
      path: "/cart",
      name: "cart-view",
      component: CartView,
    },
    {
      path: "/checkout",
      name: "checkout-view",
      component: CheckoutView,
    },
    {
      path: "/confirmation",
      name: "confirmation-view",
      component: ConfirmationVue,
    },
    {
      path: "/:pathMatch(.*)*",
      name: "not-found",
      component: NotFound,
    },
    {
      path: "/404page",
      name: "page-not-found-404",
      component: NotFound,
    },
  ],
});

export default router;
