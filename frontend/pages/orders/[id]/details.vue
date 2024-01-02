<template>
    <div>
      <v-col align="center">
        <v-col cols="6">
          <v-card>
            <v-card-title justify="center">
              <h1>Detalhes da encomenda</h1>
            </v-card-title>

            <v-card-text>
              <p>Estado: {{ order.status }}</p>
              <p>Cliente: {{ order.endConsumerName }}</p>
              <p>Operador Logistica: {{ order.logisticsOperatorName }}</p>
              <p>Produtos da encomenda: {{ order.productIds }}</p>
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
    const { data: order, error: orderErr } = await useFetch(`${api}/orders/${id}`, {
      method: 'get',
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + token.value
      }
    })
  </script>
  
  <style scoped>
  </style>
  