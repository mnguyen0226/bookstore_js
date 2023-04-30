<script setup lang="ts">
import { useCartStore } from "@/stores/cart";
import { asDollarsAndCents } from "@/utils";
import { useOrderDetailsStore } from "@/stores/orderDetails";
import type { BookItem, OrderDetails } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails as OrderDetails;
const cartStore = useCartStore();
const cart = cartStore.cart;

const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
  return orderDetails.books[index];
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;

  border-style: solid;
  border-width: thin;
  
  border-color: #00239E;
  background: white;
  color: #00239E;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: black;
  color: white;
}

.table-heading > * {
  background-color: black;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 3;
  text-align: center
}

.heading-delivery {
  grid-column: 1 / 4;
  text-align: center
}

.heading-quantity {
  grid-column: 3/4;
  text-align: center;
  padding-right: 1em;
}

.heading-price {
  grid-column:  4/6;
  text-align: center;
  padding-right: 1em;
}

.center-order {
  align-items: center;
}

.line-sep {
  display: block;
  height: 1px;
  background-color: #00239E;
  grid-column: 1 / -1;
}
</style>


<template>
  <div class="cart-table">

    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-quantity">Quantity</div>
        <div class="heading-price">Price</div>
      </li>

      <template v-for="(item, index) in orderDetails.lineItems" :key="item.bookId">
        <li class="center-order">
          <div class="heading-book">
            {{ orderDetails.books[index].title }}
          </div>
          <div class="heading-quantity">
            {{ item.quantity }}
          </div>
          <div class="heading-price">
            {{ asDollarsAndCents(orderDetails.books[index].price *  item.quantity) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>      
      <li class="center-order">
        <div class="heading-delivery">
            --- Delivery Surcharge ---
          </div>
        <div class="heading-price">
          {{ asDollarsAndCents(cart.surcharge) }}
        </div>
      </li>
      <li class="line-sep"></li>
      <li class="center-order">
        <div class="heading-delivery" style="padding-bottom: 1em;">
           <strong>Total</strong>
          </div>
        <div class="heading-price">
          <strong>{{ asDollarsAndCents(orderDetails.order.amount) }}</strong>
        </div>
      </li>
    </ul>
  </div>

</template>

