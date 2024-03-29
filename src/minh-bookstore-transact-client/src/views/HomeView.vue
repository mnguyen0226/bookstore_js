<script setup lang="ts">
import TheHomeCategoryList from "@/components/TheHomeCategoryList.vue";

function getBookImageUrl(imageFileName: string) {
  return new URL(`../assets/site-images/${imageFileName}`, import.meta.url).href
}

</script>

<style scoped>
.home-page {
  display: flex;
}

.welcome-text {
  width: 50%;
}

.category-image-items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 1em;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}

.category-image-items div {
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); 
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
}

.quote-text {
  text-align: center;
  font-style: italic;
  font-size: 120%;
}

.reference-text {
  text-align: center;
  font-weight: bold;
  margin-top: 1em;
  font-style: italic;
}

.shopping-button {
  text-align: center;
  margin-top: 1em;
  margin-bottom: 3em;
}

.background-pic {
  background-image: url("../assets/site-images/library.png");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.left-page-margin {
  margin-left: 3em;
  margin-right: 3em;
  margin-top: 3em;
  margin-bottom: 3em;
}

.blue-upper-border {
  border-top-style: solid;
  border-color: #00239E;
  border-width: thin
}

.position-12-rules {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-5%, 0%);
}

.position-beyond-order {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-45%, -28%);
}

.position-map-of-meaning {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-55%, 70%);
}

.round-book {
  border-radius: 10px;
}

.resize-12-rules {
  max-width: 70%;
  height: 70%;
}

.resize-beyond-order {
  max-width: 40%;
  height: 40;
}

.resize-map-of-meaning {
  max-width: 50%;
  height: 50%;
}

.right-page-margin {
  display:flex;
  flex-direction: row;
  justify-content: space-evenly;

  margin-left: 3em;
  margin-right: 3em;
  margin-top: 6em;
  margin-bottom: 3em;
  position: relative
}

.pill-button-cta {
  padding-top: 1em;
  padding-bottom: 1em;
  padding-right: 1.5em;
  padding-left: 1.5em;
  border-radius: 200px;
  font-size: 150%;
}
</style>

<template>
  <div class="home-page">
    <section
      class="welcome-text flow-content container dark-background align-welcome-text blue-upper-border"
    >
      <div class="left-page-margin">
        <p class="quote-text">
          “So, listen, to yourself and to those with whom you are speaking. Your
          wisdom then consists not of the knowledge you already have, but the
          continual search for knowledge, which is the highest form of wisdom.”
        </p>
        <div class="reference-text">
          <p>― Jordan B. Peterson</p>
          <p>12 Rules for Life: An Antidote to Chaos</p>
        </div>
      </div>

      <div class="shopping-button">
        <router-link to="/category/Jordan%20Petersons%20Book" class="no-underline">
          <button class="button pill-button-cta white-border-blue-bg">
            SHOP FOR BOOKS
          </button>
        </router-link>
      </div>
    </section>

    <section
      class="welcome-text flow-content container dark-background align-welcome-text background-pic"
    >
      <div class="right-page-margin">
        <div class="position-12-rules">
          <img
            :src="getBookImageUrl('12_rules.png')"  
            class="round-book resize-12-rules"
            alt="12 Rules For Lives"
          />
        </div>
        <div class="position-map-of-meaning">
          <img
            class="round-book resize-map-of-meaning"
            :src="getBookImageUrl('map_of_meaning.png')"  
            alt="12 Rules For Lives"
          />
        </div>
        <div class="position-beyond-order">
          <img
            class="round-book resize-beyond-order"
            :src="getBookImageUrl('beyond_order.png')"  
            alt="12 Rules For Lives"
          />
        </div>
      </div>
    </section>
  </div>
</template>
