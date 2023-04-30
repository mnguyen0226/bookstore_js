import { defineStore } from "pinia";
import { ShoppingCart } from "@/types";
import type { BookItem, OrderDetails, ServerErrorResponse, CustomerForm } from "@/types"; 
import { apiUrl } from "@/api"; 
import { useOrderDetailsStore } from "@/stores/orderDetails"; 

const CART_STORAGE_KEY = "ShoppingCart";

export const useCartStore = defineStore("CartStore", {
  state: () => {
    const initCart = new ShoppingCart();
    const cartString = localStorage.getItem(CART_STORAGE_KEY);
    if (cartString !== null) {
      const cartFromStorage = JSON.parse(cartString) as ShoppingCart;
      Object.assign(initCart, cartFromStorage); 
    }
    return {
      cart: initCart,
    };
  },
  getters: {
    count(): number {
      console.log(this.cart.numberOfItems);
      return this.cart.numberOfItems;
    },
    subtotal(): number {
      return this.cart.subtotal;
    },
    total(): number {
      return this.cart.total;
    },
  },

  actions: {
    clearCart() {
      this.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    addToCart(book: BookItem) {
      this.cart.addBook(book);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    updateBookQuantity(book: BookItem, quantity: number) {
      this.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    async placeOrder(customerForm: CustomerForm): Promise<OrderDetails | ServerErrorResponse> {
      const orderDetailsStore = useOrderDetailsStore();
      orderDetailsStore.clearOrderDetails(); 
      
      const order = { cart: this.cart, customerForm: customerForm }
      console.log(JSON.stringify(order))

      const url = apiUrl + "/orders";
      const response: Response = await fetch(url, {
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrer: 'client',
        method: 'POST',
        body: JSON.stringify(order)
      })
    
      const placeOrderResponse: OrderDetails | ServerErrorResponse = await response.json()
    
      if (response.ok) {
        this.clearCart();
        orderDetailsStore.setOrderDetails(placeOrderResponse as OrderDetails);
      }
      return placeOrderResponse
    }
  },
});
