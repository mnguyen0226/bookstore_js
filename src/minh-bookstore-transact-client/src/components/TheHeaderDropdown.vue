<script setup lang="ts">

import { useCategoryStore } from "@/stores/category";

const categoryStore = useCategoryStore();

</script>

<style scoped>
.header-dropdown {
  position: relative;
}

.categories-button {
  background-color: var(--primary-color-dark);
  margin-top: 0.25em;
}

.header-dropdown ul {
  background-color: white;
  color: black;
  display: none;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: #00239E;
  background: white;
  text-decoration: none;
}

.header-dropdown a:hover {
  background: var(--primary-color-dark);
  color: white;
}

.header-dropdown li:hover a {
  background: var(--primary-color-dark);
  color: #ffffff;
}

.header-dropdown li:hover {
  background: var(--primary-color-dark);
  color: white;
  border-radius: 20px;
  border-style: solid;
  border-width: thin;
  border-color: #00239E;
}

.header-dropdown:hover ul {
  display: block;
  position: absolute;
  min-width: 8em;
  z-index: 1;
  cursor: pointer;
}

.categories-button-style{
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 20px;
}

.categories-element {
  border-radius: 20px;
  border-style: solid;
  border-width: thin;

  border-color: #00239E;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button categories-button-style blue-border-white-bg">
      Categories
      <i class="fa fa-caret-down"></i>
    </button>

    <ul class="categories-element">
      <li v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link :to="{name: 'category-view', params: {name: category.name }}">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>