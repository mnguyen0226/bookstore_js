import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// ref: https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: "/MinhBookstoreTransact",
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
});
