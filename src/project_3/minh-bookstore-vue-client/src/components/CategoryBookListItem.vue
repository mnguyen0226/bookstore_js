<script setup lang="ts">
import { defineProps } from "vue";
import type { BookItem } from "@/types";
const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.gif`;
};
</script>
<style scoped>
.book-box {
  display: flex;
  flex-direction: column;
  padding: 1em;
  background-color: var(--card-background-color);

  gap: 0.25em;
}

.book-title {
  font-weight: bold;
}

.book-author {
  font-style: italic;
}
</style>

<template>
  <li class="book-box">
    <div class="book-image">
      <img
        :src="'/book-images/' + bookImageFileName(props.book)"
        :alt="book.title"
      />
    </div>
    <div class="book-title">{{ book.title }}</div>
    <div class="book-author">{{ book.author }}</div>
    <div class="book-price">${{ (book.price / 100).toFixed(2) }}</div>
    <button class="button">Add to Cart</button>
    <button class="button">Read Now</button>
  </li>
</template>
