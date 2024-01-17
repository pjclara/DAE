<template>
  <v-row>
    <v-col cols="12">
      <v-card>
        <v-card-title class="mb-4" justify="center">
          <h1>Detalhes da encomenda</h1>
        </v-card-title>
      </v-card>
    </v-col>
  </v-row>

  <v-row>
    <v-col cols="6">
      <v-card>
        <v-card-text>
          <div class="text-left">
            <h3>Estado: </h3>
            <v-text-field v-model="order.status" readonly />
          </div>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col cols="6">
      <v-card>
        <v-card-text>
          <div class="text-left">
            <h3>Endereço de entrega: </h3>
            <v-text-field v-model="order.deliveryAddress" readonly />
          </div>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
  <v-row>
    <v-col cols="12" style="background-color: beige; padding: 10px;">
      <v-card>
        <v-card-text>
          <v-row>
            <h2>Produtos da encomenda: </h2>
          </v-row>
          <v-row>
            <v-col v-for="(item, index) in order.orderItems" :key="index"
              style="background-color:bisque; border-radius: 10%; margin: 5px;" cols="4">
              <v-row>
                <v-col cols="6">
                  <v-img width="100%" height="100%" :src="item.unitProductDTO.productDTO.image" />
                </v-col>
                <v-col cols="6">
                  <ul>
                    <li>
                      <h4>Nome: {{ item.unitProductDTO.productDTO.name }}</h4>
                    </li>
                    <li>
                      <h4>Quantidade: {{ item.quantity }}</h4>
                    </li>
                    <li>
                      <ul v-for="sensor in item.unitProductDTO.packageSensorDTO.sensorValueDTOS">
                        <li>Type:{{ sensor.sensorDTO.type }}</li>
                        <li>Type:{{ sensor }}</li>
                        <li>Value:{{ sensor.value }}</li>
                      </ul>
                    </li>
                  </ul>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script setup>


import { useAuthStore } from "~/store/auth-store.js"

import { onMounted, ref } from "vue";

const message = ref('')

const authStore = useAuthStore()

const { token, user } = storeToRefs(authStore)

const config = useRuntimeConfig()

const api = config.public.API_URL

const route = useRoute()

const id = route.params.id

const username = route.params.username

const { data: order, error: orderErr } = await useFetch(`${api}/orders/${id}`)

</script>
