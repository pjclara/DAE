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
          <v-select label="Selecione o tipo de embalagem" v-model="selectedPackageType" :items="packageType"></v-select>
          {{ selectedPackageType }}
          <v-select label="Selecione a Encomenda" v-if="selectedPackageType == 'Orders'" v-model="selectedOrder"
            :items="orders" item-title="name" item-value="id"></v-select>

          <v-select label="Selecione o tipo de Produto" v-if="selectedPackageType == 'Product'" v-model="selectedProduct"
            :items="products" item-title="name" item-value="id"></v-select>
          <v-select label="Selecione o Produto Unitário" v-if="selectedProduct" v-model="selectedUnitProduct"
            :items="unitaryProducts" item-title="serialNumber" item-value="id"></v-select>
          <v-select label="Selecione o sensor do Produto Unitário" v-if="sensorsData" :items="sensorsData"
            item-title="sensorName" item-value="sensorId" v-model="idSensor"></v-select>
          <v-text-field v-model="sensorValue" label="Insira valor"></v-text-field>
          <v-btn color="primary" @click="updateSensorValue" v-if="sensorValue">Atualizar</v-btn>

        </v-col>
      </v-row>
    </v-main>
  </v-app>
</template>

<script setup>
import fetch from 'node-fetch';
import { ref, watch } from "vue";

const packageType = ref(['Product', 'Orders'])
const selectedPackageType = ref(null)

//Orders

const orders = ref([])
const selectedOrder = ref(null)

//Products

const products = ref([])
const selectedProduct = ref(null)

const unitaryProducts = ref([])
const selectedUnitProduct = ref(null)

const sensorsData = ref([])

const idSensor = ref(null)

// const sensors = ref([])
// const selectedSensor = ref(null)

const sensorValue = ref(null)


//const showUpdateForm = ref(false);


watch(() => selectedPackageType.value,
  async (newPackageType) => {
    console.log("newPackageType:", newPackageType);
    if (newPackageType == 'Product') {
      await fetchAllProducts();
    } else {
      await fetchAllOrders();
    }
  }
);


async function fetchAllOrders() {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/orders/`, {
      method: 'get',
      headers: {
        'Accept': 'application/json'
      }
    });

    const data = await response.json();

    if (data) {
      console.log("data: ", data)
      orders.value = data;
      console.log("orders.value: ", orders.value)
    }
  } catch (error) {
    console.error("Error fetching orders:", error);
  }
}


//Product

// onMounted(() => {
//   fetchAllProducts();
// })

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
    }
  }
);

async function fetchSensors(id) {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/unitProducts/${id}`, {
      method: 'get',
      headers: {
        'Accept': 'application/json'
      }
    });

    const data = await response.json();

    if (data) {
      console.log("data: ", data)
      sensorsData.value = [];
      sensorValue.value = null;
      console.log("sensorsData.value: ", data.packageSensorDTO.sensorValueDTOS)
      data.packageSensorDTO.sensorValueDTOS.forEach(element => {
        sensorsData.value.push({
          sensorId: element.id,
          sensorName: element.sensorDTO.type,
          sensorValue: element.value
        })
      });
    }
  } catch (error) {
    console.error("Error fetching unitaryProducts:", error);
  }
}

async function updateSensorValue() {
  try {
    const response = await fetch(`http://localhost:8080/backend/api/sensorValues/${idSensor.value}/updateSensorValue/${sensorValue.value}`, {
      method: 'put',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(sensorValue.value)
    });

    const data = await response.json();
    console.log("data: ", data)

    if (data) {   
      alert("Valor atualizado com sucesso!")   
    }
  } catch (error) {
    console.error("Error fetching unitaryProducts:", error);
  }
}


</script>

<style></style>
