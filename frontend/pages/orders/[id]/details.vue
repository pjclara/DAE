<template>
    <div>
      <v-col align="center">
        <v-col cols="8">
          <v-card>
            <v-card-title class="mb-4" justify="center">
              <h1>Detalhes da encomenda</h1>
            </v-card-title>

            <v-card-text>
              <div class="text-left">
                <h3>Estado: </h3>
                <v-text-field v-model="order.status" disabled/>
              </div>
              <div class="text-left">
                <h3>Cliente: </h3>
                <v-text-field v-model="order.endConsumerName" disabled/>
              </div>
              <div class="text-left">
                <h3>Operador Logistica: </h3>
                <v-text-field v-model="order.logisticsOperatorName" disabled/>
              </div>
              <div class="text-left">
                <h3>Embalagem: </h3>
                <v-text-field v-model="order.packageId" disabled/>
              </div>
              <div class="text-left">
                <h3>Produtos da encomenda: </h3>
                <v-text-field v-model="order.productIds" disabled/>
              </div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-col>
    </div>
  </template>
  
  <script setup>
    import { useAuthStore } from "~/store/auth-store.js"
    const authStore = useAuthStore()
    const { token, user } = storeToRefs(authStore)
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const route = useRoute()
    const id = route.params.id
    const { data: order, error: orderErr } = await useFetch(`${api}/orders/${id}`)

 
  </script>
  
  <style scoped>
  </style>
  