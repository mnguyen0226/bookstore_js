<script setup lang="ts">
import { inject } from "vue";
import type { CategoryItem } from "@/types";
const categoryList = inject("categoryList") as CategoryItem[];
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
      <li v-for="category in categoryList" :key="category.categoryId">
        <router-link
          :to="'/category/' + category.name"
          class="button category-button"
        >
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>
