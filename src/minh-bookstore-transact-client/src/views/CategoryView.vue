<script setup lang="ts">
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
import {useRoute} from "vue-router";
import {useBookStore} from "@/stores/book";
import {watch} from "vue";
import { useCategoryStore } from "@/stores/category";
import router from "@/router"

const route = useRoute();
const categoryStore = useCategoryStore();
const bookStore = useBookStore();

watch(
  () => route.params.name,
  async (newName) => {
    await categoryStore.setSelectedCategoryName(newName as string);
    await bookStore.fetchBooks(newName as string).catch(() => {
      router.push("/not-found");
    });
  },
  { immediate: true }
);
</script>

<style scoped></style>

<template>
 <div>
  <the-category-nav></the-category-nav>
  <the-category-book-list :book-list="bookStore.bookList"></the-category-book-list>
 </div>
</template>