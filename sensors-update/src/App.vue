<template>
  <v-app>
    <v-app-bar color="deep-purple">
      <v-col align="center">
        <v-row>
          <v-app-bar-title justify="center">Atualização de Sensores</v-app-bar-title>
        </v-row>
      </v-col>
    </v-app-bar>
    <v-main>
      <v-row class="justify-center py-4">
        <v-col cols="6" align="center">
          <v-select label="Selecione o tipo de Produto" v-model="selectedProduct" :items="products"  item-title="name" item-value="id"></v-select>
          {{ selectedProduct }}
          <v-select label="Selecione o Produto Unitário" v-model="selectedUnitProduct" :items="unitaryProducts"  item-title="serialNumber" item-value="id"></v-select>
          <v-select label="Selecione o sensor do Produto Unitário" ></v-select>
          <v-text-field v-model="sensorValue"></v-text-field>
        </v-col>
      </v-row>
    </v-main>
  </v-app>
</template>

<script setup>
import fetch from 'node-fetch';
import { ref, onMounted, watch } from "vue";

const products = ref([])
const selectedProduct = ref(null)

const unitaryProducts = ref([])
const selectedUnitProduct = ref(null)

// const sensors = ref([])
// const selectedSensor = ref(null)

//const sensorValue = ref(null)


//const showUpdateForm = ref(false);

onMounted(() => {
  fetchAllProducts();
})

async function fetchAllProducts() {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/products/`, {
      method: 'get',
      headers: {
        'Accept': 'application/json'
      }
    });

    const data = await response.json();

    if (data) {
      console.log("data: ", data)
      products.value = data;
      console.log("products.value: ", products.value)
    }
  } catch (error) {
    console.error("Error fetching products:", error);
  }
}

watch(() => selectedProduct.value,
  async (newSelectedProduct) => {
    console.log("newSelectedProduct:", newSelectedProduct);
    if (newSelectedProduct) {
      console.log("newSelectedProduct: ", newSelectedProduct);
      await fetchUnitaryProducts(newSelectedProduct);
      // Add other operations as needed
    }
  }
);

async function fetchUnitaryProducts() {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/products/${selectedProduct.value}/unitProducts`, {
      method: 'get',
      headers: {
        'Accept': 'application/json'
      }
    });

    const data = await response.json();

    if (data) {
      console.log("data: ", data)
      unitaryProducts.value = data;
      console.log("unitaryProducts.value: ", unitaryProducts.value)
    }
  } catch (error) {
    console.error("Error fetching unitaryProducts:", error);
  }
}

watch(() => selectedUnitProduct.value,
  async (newSelectedUnitProduct) => {
    console.log("newSelectedUnitProduct:", newSelectedUnitProduct);
    if (newSelectedUnitProduct) {
      console.log("newSelectedUnitProduct: ", newSelectedUnitProduct);
      await fetchSensors(newSelectedUnitProduct);
      // Add other operations as needed
    }
  }
);

async function fetchSensors() {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/products/${selectedProduct.value}/unitProducts`, {
      method: 'get',
      headers: {
        'Accept': 'application/json'
      }
    });

    const data = await response.json();

    if (data) {
      console.log("data: ", data)
      unitaryProducts.value = data;
      console.log("unitaryProducts.value: ", unitaryProducts.value)
    }
  } catch (error) {
    console.error("Error fetching unitaryProducts:", error);
  }
}


</script>

<style>

</style>
