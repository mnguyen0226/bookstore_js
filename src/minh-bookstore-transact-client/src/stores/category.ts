import type { CategoryItem } from "@/types"
import {apiUrl} from '@/api'
import {defineStore} from "pinia"

const DEFAULT_CATEGORY_NAME = "Jordan Petersons Book";

export const useCategoryStore = defineStore("category", {
    state: () => ({
      categoryList: [] as CategoryItem[],
      selectedCategoryName: DEFAULT_CATEGORY_NAME as string,
    }),
    actions: {
      async fetchCategories() { 
        const response = await fetch(`${apiUrl}/categories`);
        const data = await response.json();
        this.categoryList = data as CategoryItem[];
       },

      async setSelectedCategoryName(selectedCategoryName: string) {
        this.selectedCategoryName = selectedCategoryName;
      },
    },
    
    // ref: https://github.com/nowucca/greater-goods-client/blob/vue3-main-vite/src/stores/CategoryStore.ts
    getters: {
    categoryName(): string {
      return this.selectedCategoryName || this.defaultCategoryName;
    },

    defaultCategoryName(): string {
      return DEFAULT_CATEGORY_NAME;
    },
  }
});