<script setup lang="ts">
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/category";
import ConfirmationTable from "@/components/ConfirmationTable.vue";
import { useOrderDetailsStore } from "@/stores/orderDetails";
import type {OrderDetails} from "@/types";
import { displayCreditCard, formatMonth } from "@/utils";

const cartStore = useCartStore();
const categoryStore = useCategoryStore();
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails as OrderDetails;

</script>
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}

.blue-upper-border {
  border-top-style: solid;
  border-color: #00239E;
  border-width: thin
}

.center-text {
  text-align: center;
}

.pill-button {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 200px;
}

.white-border-gray-bg {
  border-style: solid;
  border-width: thin;

  border-color: #4e4e4e;
  background: gray;
  color: white;
}

.white-border-gray-bg:hover,
.white-border-gray-bg:active {
  background: silver;
  color: black;
}
</style>

<template>
  <section class="blue-upper-border">
    <br/>
    <br/>
    <div class="center-text">
      <strong>CONFIRMATION</strong>
    </div>
    <br/>

    <div v-if="!orderDetails.order">
      <div class="center-text">
        <p>We are sorry, the order you requested could not be found.</p>
        <br/> 
        <router-link :to="'/'">
          <button class="button pill-button white-border-gray-bg">
            Go to Home Page
          </button>
        </router-link>
      </div>
    </div>

    <div v-else>
      <div class="center-text">
        <ul>
          <li>
            Your confirmation number is {{ orderDetails.order.confirmationNumber }}
          </li>
          <li>{{ new Date(orderDetails.order.dateCreated) }}</li>
        </ul>
        <br/>
        <confirmation-table> </confirmation-table>
        <br/>
        <div>
          <div>--------------------------------------------------------------------------------------</div>
          <strong>CUSTOMER INFORMATION</strong>        
          <ul style="padding-top: 1em;">
            <li>{{ orderDetails.customer.customerName }}</li>
            <li>({{ orderDetails.customer.email }})</li>
            <li>{{ orderDetails.customer.address }}</li>
            <li>{{ orderDetails.customer.phone }}</li>
            <li>{{ displayCreditCard(orderDetails.customer.ccNumber) }}
              ({{
                formatMonth(
                  new Date(orderDetails.customer.ccExpDate).getUTCMonth()
                )
              }}/{{ new Date(orderDetails.customer.ccExpDate).getUTCFullYear() }})            
            </li>
          </ul>
        </div>
      </div>
      <br/>
      <div class="center-text">
        <router-link :to="'/category/' + categoryStore.categoryName">
          <button class="button pill-button white-border-gray-bg">
            Continue to Shopping
          </button>
        </router-link>
      </div>
    </div>
    <br/>
    <br/>
  </section>
</template>