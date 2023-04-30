<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/cart";

const cartStore = useCartStore();
const clearCart = function () {
  cartStore.cart.clear();
};

</script >
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}

.blue-upper-border {
  border-top-style: solid;
  border-color: #00239E;
  border-width: thin
}

</style>

<template>
  <section class="blue-upper-border">
    <br/>
    <br/>
    <cart-table></cart-table>
    <br/>
    <br/>
  </section>
</template>