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
  background-color: var(--primary-background-color);
  color: var(--secondary-text-color); 
}

/*category color selected*/
.button.selected-category-button {
  background-color: black;
  color: var(--secondary-text-color); 
}

/*category color unselected*/
.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--primary-background-color);
  color: var(--default-text-color);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
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
          class="button unselected-category-button"
        >
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>
