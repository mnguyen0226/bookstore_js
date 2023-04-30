<script setup lang="ts">
import TheHeaderDropdown from "@/components/TheHeaderDropdown.vue";
import { useCartStore } from "@/stores/cart";

function getBookImageUrl(imageFileName: string) {
  return new URL(`../assets/site-images/${imageFileName}.png`, import.meta.url).href
}

const cartStore = useCartStore();

</script>

<style scoped>
header {
  background: var(--primary-background-color);
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  padding: 1em;
  gap: 1em;
}

.text-logo {
  font-family: var(--title-font-family);
  color: #00239E;
}

.search-bar {
  padding: 0.5em;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  text-indent: 1em;
  transition: width 2s cubic-bezier(0.18, 0.89, 0.32, 1.15);
  width: 100%;
}

input:hover {
  width: 358px;
}

.search-button {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
}

.pill-button {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 200px;
}

.pill-button-cart {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 200px;
  margin-right: 10px
}

.pill-button-count {
  padding-top: 0.05em;
  padding-bottom: 0.05em;
  padding-right: 0.6em;
  padding-left: 0.6em;
  border-radius: 20px;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}

.title-and-search-bar {
  display: flex;
}

.search-bar-and-button {
  display: flex;
}

.header-dropdown-and-cart {
  display: flex
}

.header-space {
  justify-content: space-between;
}

.header-div {
  display: flex;
  align-items: center;
  gap: 1em;
}

.relative {
  position: relative;
}

.absolute {
  position: absolute;
  top: -10px;
  right: -2px;
}

.yellow-border-yellow-bg {
  border-style: solid;
  border-width: thin;

  border-color: #c6b200;
  background: #c6b200;
  color: white;
}

.yellow-border-yellow-bg:hover,
.yellow-border-yellow-bg:active {
  border-style: solid;
  border-width: thin;
  border-color: #ffe600;
  background: #ffe600;
  color: white;
}
</style>

<template>
  <header class="container header-space">

    <div class="header-div">
      <section class="bookstore-logo">
        <router-link to="/">
          <img
            :src="getBookImageUrl('bookstore-logo')"
            width="50"
            height="auto"
          />
        </router-link>
      </section>

      <section class="title-and-search-bar">
        <router-link to="/" style="text-decoration: None">
          <h1 class="text-logo">Books.JBP</h1>
        </router-link>
      </section>

      <section class="title-and-search-bar">
        <form action="category.html" class="search-bar-and-button">
          <input
            type="text"
            class="search-bar blue-border-white-bg"
            placeholder="Search"
          />
          <button
            type="submit"
            class="button search-button white-border-blue-bg"
          >
            <i class="fa fa-search"></i>
          </button>
        </form>
      </section>
    </div>

    <section class="header-dropdown-and-cart header-div">

      <the-header-dropdown></the-header-dropdown>

      <button class="button pill-button white-border-blue-bg">
        Minh Nguyen
      </button>
      <div class="relative">
        <router-link to="/cart">
          <button class="button pill-button-cart white-border-blue-bg">
            <i class="fa fa-shopping-cart"></i>
          </button>
          <button
            class="button pill-button-count absolute yellow-border-yellow-bg">
            {{ cartStore.cart.numberOfItems }}
          </button>
        </router-link>
      </div>
    </section>
  </header>
</template>
