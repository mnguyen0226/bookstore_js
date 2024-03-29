<script setup lang="ts">
import { defineProps } from "vue";
import type { BookItem } from "@/types";
import { useCartStore } from "@/stores/cart";
import { PriceFormatter } from "@/utils";

const cartStore = useCartStore();

const props = defineProps<{
  book: BookItem;
}>();
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

</script>

<style scoped>
#book-boxes {
  display: flex;
  flex-wrap: wrap;
  padding: 1em;
  gap: 1em;
  justify-content: space-evenly;
}

.book-box {
  display: flex;
  flex-direction: row;
  padding: 1em;
  gap: 0.25em;
  position: relative;

  border-style: solid;
  border-width: thin;
  
  border-color: #00239E;
  background: white;
  color: #00239E;
}

.book-title {
  font-weight: bold;
  padding-bottom: 0.5em;
}

.book-author {
  font-style: italic;
  padding-bottom: 0.5em;
}

.book-price {
  font-weight: bold;
  padding-bottom: 1em;
}

.book-image {
  justify-content: center;
  text-align: center;
  width: 150px;
}

.book-width {
  height:200px;
}

.description-box {
  justify-content: center;
  text-align: center;
  width: 120px;
}

.read-now-pos {
  position: absolute;
  top: 72.5%;
  left: 40%;
  transform: translate(-117%, 0%);
}

.add-to-cart-pos {
  position: absolute;
  top: 72.5%;
  left: 50%;
  transform: translate(15%, 0%);
}

.price-pos {
  position: absolute;
  top: 58%;
  left: 53%;
  transform: translate(82%, 0%);
}

.round-button {
  border-radius: 20px;
}

.price-font {
  font-family: var(--price-font-family);
}

</style>

<template>
  <ul id="book-boxes">
    <li class="book-box">
      <div class="book-image">
        <img
          :src="getBookImageUrl(bookImageFileName(props.book))"
          :alt="book.title"
          class="book-width"
        />
        <button
          v-if="book.isPublic"
          class="button read-now-pos round-button blue-border-white-bg"
        >
          <i class="fas fa-book-reader"></i>
        </button>
      </div>
      <div class="description-box">
        <div class="book-title">
          {{ book.title }}
        </div>
        <div class="book-author">
          {{ book.author }}
        </div>
        <div class="book-price price-pos price-font">
          ${{ PriceFormatter.format(book.price / 100.0) }}
        </div>
        <button
          @click="cartStore.addToCart(book)"
          class="button round-button add-to-cart-pos white-border-black-bg"
        >
          Add to Cart
        </button>
      </div>
    </li>
  </ul>
</template>
