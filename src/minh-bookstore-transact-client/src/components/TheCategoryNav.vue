<script setup lang="ts">

import type { CategoryItem } from "@/types";
import {apiUrl} from "@/api"
import { useRoute } from "vue-router";

import { useCategoryStore } from "@/stores/category";

const categoryStore = useCategoryStore();
const route = useRoute();

function showActiveCategory(category: CategoryItem): boolean {
  return (
    route.name !== "home" &&
    categoryStore.selectedCategoryName === category.name
  );
}

</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  text-align: center;
  justify-content: space-between;
}

.button.category-button {
  background-color: white;
  color: var(--secondary-background-color);
}

.button.category-button.router-link-active,
.button.category-button:hover,
.button.category-button:active {
  background-color: black;
  color: var(--secondary-text-color);
}

.blue-border-nav-bar {
  border-top-style: solid;
  border-bottom-style: solid;
  border-width: thin;
  border-color: #00239E;
  background: white;
  color: #00239E;
}
</style>

<template>
  <nav class="category-nav blue-border-nav-bar">
    <ul class="category-buttons">
      <template v-for="category in categoryStore.categoryList" >
      <li :key="category.categoryId" v-if="showActiveCategory(category)" class="active">
        <router-link
          :to="'/category/' + category.name"
          class="button category-button"
        >
          {{ category.name }}
        </router-link>
      </li>
      <li :key="category.categoryId" v-else>
        <router-link
          :to="'/category/' + category.name"
          class="button category-button"
        >
          {{ category.name }}
        </router-link>
      </li>
    </template>
    </ul>
  </nav>
</template>
