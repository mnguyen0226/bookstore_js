<script setup lang="ts"></script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  gap: 1em;
  color: var(--secondary-text-color);
}

.links,
.social-media-icons {
  display: flex;
  flex-direction: column;
}

.social-media-icons {
  text-align: center;
  gap: 0.25em;
  margin-top: 1em;
  margin-bottom: 1em;
}

.centered-footer {
  justify-content: center;
}

.center-text {
  text-align: center;
}

.bold-url {
  font-size: 1.25rem;
  font-weight: bold;
}

.inline-display {
  display: inline-block;
}

.margin-links {
  margin-bottom: 1em;
}

.round-icon {
  border-radius: 50px;
}

.no-underline {
  text-decoration: None;
}

.no-underline:active,
.no-underline:hover {
  color: white;
  text-decoration: underline;
}

.button:hover,
.button:active {
  border-style: solid;
  border-width: thin;
  border-color: white;
  background: #00239E;
  color: white;
}
</style>

<template>
  <footer class="container centered-footer">
    <div class="center-text">
      <section class="bold-url">
        <router-link to="/" style="text-decoration: None; color: white">
          Books.JBP
        </router-link>
      </section>

      <section class="social-media-icons inline-display margin-footer">
        <router-link to="#" class="button round-icon">
          <i class="fab fa-youtube"></i>
        </router-link>
        &nbsp;
        <router-link to="#" class="button round-icon">
          <i class="fab fa-twitter"></i>
        </router-link>
        &nbsp;
        <router-link to="#" class="button round-icon">
          <i class="fab fa-facebook"></i>
        </router-link>
        &nbsp;
        <router-link to="#" class="button round-icon">
          <i class="fab fa-instagram"></i>
        </router-link>
        &nbsp;
        <router-link to="#" class="button round-icon">
          <i class="fab fa-pinterest"></i>
        </router-link>
      </section>
      <br />
      <section class="links inline-display margin-links">
        <router-link to="#" class="no-underline">About</router-link>
        &nbsp;|&nbsp;
        <router-link to="#" class="no-underline">Contact</router-link>
        &nbsp;|&nbsp;
        <router-link to="#" class="no-underline">Directions</router-link>
      </section>
      <section>Copyright &copy; 2023. All Right Reserved.</section>
    </div>
  </footer>
</template>
