<template>
    <div>
      <v-col align="center">
        <v-col cols="6">
          <v-card>

            <v-card-text>
              <p>Stock :{{ product.serialNumber }}</p>
              <p>Manufacturer: {{ product.available }}</p>
              <!-- Dados de embalagem e sensor  -->
            </v-card-text>

            <v-card-actions class="justify-center">
            </v-card-actions>
          </v-card>
        </v-col>
      </v-col>
    </div>
  </template>
  
  <script setup>
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const route = useRoute()
    const id = route.params.id
    const { data: product, error: productErr } = await useFetch(`${api}/unitProducts/${id}`)
    console.log("product: ", product.value)
  </script>

  <style scoped>
  </style>
  