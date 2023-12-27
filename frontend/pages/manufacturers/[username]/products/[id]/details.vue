<template>
    <div>
      <v-col align="center">
        <v-col cols="6">
          <v-card>
            <v-card-title justify="center">
              {{ product.name }}
            </v-card-title>

            <v-card-text>
              <div v-if="product.image">
                <v-img :src="product.image" />
              </div>
              <div v-else>
                <v-img width="200" height="200" src="https://t4.ftcdn.net/jpg/00/89/55/15/360_F_89551596_LdHAZRwz3i4EM4J0NHNHy2hEUYDfXc0j.jpg" />
              </div>
              <p>Stock :{{ product.stock }}</p>
              <p>Manufacturer: {{ product.manufacturerUsername }}</p>
              <p>Product Package: {{ product.productPackage }}</p>
            </v-card-text>

            <v-card-actions class="justify-center">
              <v-btn><nuxt-link class="link " :to="`/products/${product.id}/edit`">Editar</nuxt-link></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-col>
    </div>
  </template>
  
  <script setup>
    const message = ref('')
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const route = useRoute()
    const id = route.params.id
    const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`)
    console.log("product: ", product.value)
  </script>

  <style scoped>
  </style>
  