<script setup lang="ts">
import { reactive, ref } from "vue";
import useVuelidate from "@vuelidate/core";
import { helpers, maxLength, minLength, required, email, numeric } from "@vuelidate/validators";
import type { OrderDetails, ServerErrorResponse } from "@/types";
import { useCartStore } from "@/stores/cart";
import { isCreditCard, isMobilePhone } from "@/validators";
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import router from "@/router";
import { asDollarsAndCents } from "@/utils"
import { useCategoryStore } from "@/stores/category";

const cartStore = useCartStore();
const cart = cartStore.cart;
const categoryStore = useCategoryStore();

const defaultServerErrorMessage = "An unexpected error occurred, please try again."
const serverErrorMessage = ref(defaultServerErrorMessage);

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  
  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    phone: helpers.withMessage(
      "Please provide a valid phone number.",
      (value: string) => !helpers.req(value) || isMobilePhone(value)
    ),
  },

  email: {
    required: helpers.withMessage("Please provide an email address.", required),
    email: helpers.withMessage("Please provide a valid email address.", email),  
  },

  ccNumber: {
    required: helpers.withMessage("Please provide credit cart number.", required),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit cart number. ",
      (value: string) => !helpers.req(value) || isCreditCard(value)
    ),
  },

  ccExpiryMonth: {
    required: helpers.withMessage(
      "Please provide your Card expiration details.",
      required
    ),
  },
  ccExpiryYear: {
    required: helpers.withMessage(
      "Please provide your Card expiration details.",
      required
    ),
  },

};
const v$ = useVuelidate(rules, form); 

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    try {
      form.checkoutStatus = "PENDING";
      serverErrorMessage.value = defaultServerErrorMessage;

      const placeOrderResponse: OrderDetails | ServerErrorResponse =
        await cartStore
          .placeOrder({
            name: form.name,
            address: form.address,
            phone: form.phone,
            email: form.email,
            ccNumber: form.ccNumber,
            ccExpiryMonth: form.ccExpiryMonth,
            ccExpiryYear: form.ccExpiryYear,
          })

      if ("error" in placeOrderResponse) {
        form.checkoutStatus = "SERVER_ERROR";
        serverErrorMessage.value = (placeOrderResponse as ServerErrorResponse).message
        console.log("Error placing order", placeOrderResponse);
      } else {
        form.checkoutStatus = "OK";
        await router.push({name: "confirmation-view"});
      }

    } catch (e) {
      form.checkoutStatus = "SERVER_ERROR";
      serverErrorMessage.value = defaultServerErrorMessage;
      console.log("Error placing order", e);
    }
  }
}

function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}

</script>

<style scoped>
.checkout-page {
  background: white;
  color: #00239E;
}
.checkout-page-body {
  display: flex;
  justify-content: center;
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

.form-input {
  display: inline-block;
  padding: 0.3em;
  border: 0.1em solid #ccc;
  border-radius: 0.3em;
  font-size: 1em;
}

.set-form-width {
  width: 13.2em
}

.form-label {
  display: inline-block;
  width: 5em;
  margin-right: 1em;
  text-align: right;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  background-color: silver;
  margin-left: 0.5em;
}

form > .error {
  color: red;
  text-align: right;
}

.error-message {
  color: red;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}

.center-text {
  text-align: center;
}

.pill-button {
  padding-top: 0.5em;
  padding-bottom: 0.5em;
  padding-right: 1em;
  padding-left: 1em;
  border-radius: 3em;
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

.white-border-blue-bg {
  border-style: solid;
  border-width: thin;

  border-color: #00239E;
  background: #00239E;
  color: white;
}

.error {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  font-size: 1em;
  color: red;
  margin-top: -0.5em;
  margin-bottom: 0.6em;
}
</style>

<template>
  <div class="center-text">
    <strong>CHECKOUT</strong>
  </div>

  <div class="checkout-page">
    <template v-if="cartStore.count >= 1">
      <section class="checkout-page-body" v-if="!cart.empty">
 
        <form @submit.prevent="submitOrder">
          <div style="display: inline-block;">
            <label for="name" class="form-label">Name</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              class="form-input set-form-width"
              v-model.lazy="v$.name.$model"
            />
          </div>
          <em class="error"><checkout-field-error :field-name="v$.name"/></em>

          <div style="display: inline-block;">
            <label for="name" class="form-label">Address</label>
            <input
              type="text"
              size="20"
              id="address"
              name="address"
              class="form-input set-form-width"
              v-model.lazy="v$.address.$model"
            />
          </div>
          <em class="error"><checkout-field-error :field-name="v$.address"/></em>

          <div style="display: inline-block;">
            <label for="phone" class="form-label">Phone</label>
            <input
              class="textField form-input set-form-width"
              type="text"
              size="20"
              id="phone"
              name="phone"
              v-model.lazy="v$.phone.$model"
            />
          </div>
          <em class="error"><checkout-field-error :field-name="v$.phone"/></em>

          <div style="display: inline-block;">
            <label for="email" class="form-label">Email</label>
            <input
              class="textField form-input set-form-width"
              type="text"
              size="20"
              id="email"
              name="email"
              v-model.lazy="v$.email.$model"
            />          
          </div>
          <em class="error"><checkout-field-error :field-name="v$.email"/></em>

          <div style="display: inline-block;">
            <label for="ccNumber" class="form-label">Credit card</label>
            <input
            class="textField form-input set-form-width"
              type="text"
              size="20"
              id="ccNumber"
              name="ccNumber"
              v-model.lazy="v$.ccNumber.$model"
            />    
            </div>
          <em class="error"><checkout-field-error :field-name="v$.ccNumber"/></em>

          <div style="display: inline-block;">
            <label class="form-label">Expiry</label>
            <select v-model="v$.ccExpiryMonth.$model" class="form-input">
              <option 
                v-for="(month, index) in months"
                :key="index"
                :value="index + 1"
              >
                {{ month }} ({{ index + 1 }})
              </option>
            </select>
            <select class="form-input">
              <option
                v-for="(year, index) in 15"
                :key="index"
                :value="yearFrom(index)"
              >
                {{ yearFrom(index) }}
              </option>
            </select>
          </div>
          <br/>
          <section class="center-text">
            <input
            type="submit"
            name="submit"
            class="button pill-button white-border-blue-bg"
            :disabled="form.checkoutStatus === 'PENDING'"
            value="Complete Purchase"
          />
          </section>
        </form>
      </section>
      <section class="checkout-details center-text">
        <div>--------------------------------------------------------------------------------------</div>
        Your credit cart will be charged
        <strong>{{ asDollarsAndCents(cart.subtotal + cart.surcharge) }}.</strong>
        <br/>
        (<strong>{{ asDollarsAndCents(cart.subtotal) }}</strong>
        + 
        <strong>{{ asDollarsAndCents(cart.surcharge) }}</strong> shipping)
      </section>

      <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
        <div v-if="form.checkoutStatus === 'ERROR'" class="error-message">
          <em><strong>Error:</strong> Please fix the problems above and try again.</em>
        </div>

        <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

        <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

        <div v-else style="color: red">
          <em><strong>Error:</strong> {{ serverErrorMessage }}</em>
        </div>
      </section>
      <br/>
    </template>

    <template v-else>
      <br/>
      <div class="center-text">Please select some books to checkout.</div>
      <br/>
      <div class="center-text">
        <router-link :to="'/category/' + categoryStore.categoryName">
          <button class="button pill-button white-border-gray-bg">
            Continue to Shopping
          </button>
        </router-link>
      </div>
      <br/>
    </template>
    
  </div>
</template>
