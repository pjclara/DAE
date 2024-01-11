<template>
  <Default>
    <v-col align="center">
      <v-col cols="8">
        <v-card>
          <v-card-title class="mb-4" justify="center">
            <h1>Detalhes da encomenda</h1>
          </v-card-title>

          <v-card-text>
            <div class="text-left">
              <h3>Estado: </h3>
              <v-text-field v-model="order.status" disabled />
            </div>
            <div class="text-left">
              <h3>Produtos da encomenda: </h3>
              
              <span v-for="(item, index) in order.orderItems" :key="index" style="align-items: center;">
                <v-img width="100" height="100" :src="item.productDTO.image" />                
                <br>
                <ul>
                  <li>
                    <h4>Nome: {{ item.productDTO.name }}</h4>
                  </li>
                  <li>
                    <h4>Quantidade: {{ item.quantity }}</h4>
                  </li>
                  <li>
                    <ul>
                      <li>
                        <h4>Package material: {{ item.productDTO.packageDTO.packagingMaterial }}</h4>
                        <ul v-for="sensor in item.productDTO.packageDTO.sensors" key="sensor.id">
                          <li>
                            <h4>Sensor type: {{ sensor.type }}</h4>
                          </li>
                          <li>
                            <h4>Value: {{ sensor.value }}</h4>
                          </li>
                        </ul>
                      </li>
                      
                    </ul>
                  </li>
                </ul>
              </span>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-col>
  </Default>
</template>

<script setup>

import Default from '/pages/layouts/default.vue'

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

console.log(order.value)
</script>

<style scoped>
/* Add your component styles here */
</style>
