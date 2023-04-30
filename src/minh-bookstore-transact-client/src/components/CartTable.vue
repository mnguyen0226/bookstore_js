<script setup lang="ts">
import { defineProps } from "vue";
import type { BookItem } from "@/types";
import { useCategoryStore } from "@/stores/category";
import { useCartStore } from "@/stores/cart";
import { computed } from "vue";
import { asDollarsAndCents } from "@/utils"

const cartStore = useCartStore();
const categoryStore = useCategoryStore();

const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  name = name.replace(/:/g, "");
  name = name.replace(/’/g, "");
  name = name.replace(/,/g, "");
  return `${name}.gif`;
};

function getBookImageUrl(imageFileName: string) {
  return new URL(`../assets/book-images/my_books/${imageFileName}`, import.meta.url).href
}

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};

const cart = computed(() => {
  return cartStore.cart;
});

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
  text-align: center;
}

.heading-price {
  grid-column: 3 / 4;
  text-align: center;
}

.heading-quantity {
  grid-column: 4 / 5;
  text-align: center;
}

.heading-subtotal {
  text-align: center;
  grid-column: 5 / -1;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.square {
  width: 100px;
  height: 100px;
}

.wide-rect {
  width: 125px;
  height: 100px;
}

.cart-book-price {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-quantity {
  text-align: center;
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

.line-sep {
  display: block;
  height: 1px;
  background-color: #00239E;
  grid-column: 1 / -1;
}

.icon-button {
  border: none;
  cursor: pointer;
}

.inc-button {
  font-size: 1.125rem;
  color: var(--primary-color);
  margin-right: 0.25em;
  background-color: white;
}

.inc-button:hover {
  color: var(--primary-color-dark);
  background-color: white;
}

.dec-button {
  font-size: 1.125rem;
  color: gray;
  background-color: white;
}

.dec-button:hover {
  color: silver;
  background-color: white;
}

.dec-arrow-button,
.inc-arrow-button {
  font-size: 1rem;
  color: var(--primary-color);
}

.dec-arrow-button:hover,
.inc-arrow-button:hover {
  color: var(--primary-color-dark);
}

input[type="number"] {
  width: 4em;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}

.cart-book-image {
  padding-left: 1em;
  padding-right: 1em;
  justify-content: center;
  text-align: center;
  width: 100px;
}

.center-order {
  align-items: center;
}

.pill-button {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 200px;
}

.center-text {
  text-align: center;
}

.white-border-blue-bg {
  border-style: solid;
  border-width: thin;

  border-color: #00239E;
  background: #00239E;
  color: white;
}

.white-border-gray-bg {
  border-style: solid;
  border-width: thin;

  border-color: #4e4e4e;
  background: gray;
  color: white;
}

.white-border-gray-bg:hover,
.white-border-gray-bg:active {
  background: silver;
  color: black;
}

.white-border-silver-bg {
  font-size: 0.8rem;
  border-style: solid;
  border-width: thin;

  border-color: #4e4e4e;
  background: gray;
  color: white;
}

.white-border-silver-bg:hover,
.white-border-silver-bg:active {
  background: silver;
  color: black;
}

</style>

<template>
  <div class="center-text">
    <strong>YOUR CART</strong>
  </div>
  <br/>
  <div class="center-text">
    <ul>
      <li>
        <template v-if="cartStore.count > 1">
            Your shopping cart contains
            {{ cartStore.count }} items.
          </template>
          <template v-else-if="cartStore.count === 1">
            Your shopping cart contains
            {{ cartStore.count }} item.
          </template>
          <template v-else>Your shopping cart is empty.</template>      
        </li>
    </ul>
  </div>

  <br/>

  <div class="cart-table" v-if="!cart.empty">

    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price</div>
        <div class="heading-quantity">Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>

      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li class="center-order">
          <div class="cart-book-image">
            <img
              :src="getBookImageUrl(bookImageFileName(item.book))"
              :alt="item.book.title"
              class="book-width"
            />
          </div>

          <div class="cart-book-title">
            <div>{{ item.book.title }}</div>
            <br/>
            <div>
              <button 
                class="button pill-button blue-border-white-bg" style="font-size: 0.8rem;"
                v-if="!cart.empty"
                @click="updateCart(item.book, 0)"
              >
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
          
          <div class="cart-book-price">
            {{ asDollarsAndCents(item.book.price) }}
          </div>

          <div class="cart-book-quantity">
            <span class="quantity">{{ item.quantity }}</span
            >&nbsp;
            
            <button
              class="icon-button inc-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              <i class="fas fa-plus-circle"></i>
            </button>
            
            <button
              class="icon-button dec-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              <i class="fas fa-minus-circle"></i>
            </button>
          </div>

          <div class="cart-book-subtotal">{{ asDollarsAndCents(item.book.price * item.quantity) }}</div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>
  </div>

  <br/>
  <div style="text-align: center;">--------------------------------------------------------------------------------------</div>
  <div class="center-text">
    <ul v-if="!cart.empty">
      <li>
        <b>Cart subtotal:</b> {{asDollarsAndCents(cart.subtotal)}}.
      </li>
      <br/>
      <li>
        <b>Cart total:</b> {{asDollarsAndCents(cart.total)}}.
      </li>
    </ul>
  </div>

  <br/>

  <div class="center-text">
    <router-link :to="'/category/' + categoryStore.categoryName">
      <button class="button pill-button white-border-gray-bg">
          Continue to Shopping
      </button>
    </router-link>
    &nbsp
    <router-link to="/checkout">
      <button 
        class="button pill-button white-border-blue-bg"
        v-if="!cart.empty"
      >
          Proceed To Checkout
      </button>
    </router-link>
  </div>
  <br/>
  <div class="center-text">
    <button 
      class="button pill-button blue-border-white-bg"
      v-if="!cart.empty"
      @click.stop.prevent="cartStore.clearCart"
    >
      Clear Cart
    </button>
  </div>
</template>
