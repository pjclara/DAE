<template>
  <div>
    <v-col align="center">
      <v-col cols="6">
        <v-card>
          <v-card-title justify="center">
            <h1>{{ product?.name }}</h1>
          </v-card-title>

          <v-card-text>
            <div v-if="product.image">
              <v-img width="300" height="300" :src="product.image" />
            </div>
            <div v-else>
              <v-img width="200" height="200"
                src="https://t4.ftcdn.net/jpg/00/89/55/15/360_F_89551596_LdHAZRwz3i4EM4J0NHNHy2hEUYDfXc0j.jpg" />
            </div>
            <p>Stock :{{ product.stock }}</p>
          </v-card-text>
          <v-card-text>
            <p>Manufacturer: {{ product.manufacturerUsername }}</p>
          </v-card-text>
          <v-card-text>
            <p>Product Package: {{ product?.packagingMaterial }}</p>

          </v-card-text>
          <v-card-actions class="justify-center d-flex flex-wrap">
            <v-btn block rounded="xl" size="large" class="mb-2" @click="editItem">Editar</v-btn>
            <v-btn block rounded="xl" size="large" @click="listUnitProducts">Produtos Unitários</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-col>
  </div>
</template>
  
<script setup>
const message = ref('')
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const id = route.params.id
const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packageProducts/type/PRIMARY`)

const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`, {
  method: 'get',
  headers: {
    'Accept': 'application/json',
    'Authorization': 'Bearer ' + token.value
  }
})

const packageId = ref(product.value.packageId)


const editItem = (item) => {
  navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/edit/')
}
const listUnitProducts = (item) => {
  navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/unitProducts/')
}
const update = async () => {
  const requestOptions = {
    method: 'PUT',
    headers: {
      "Content-Type": "application/json",
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + token.value
    },
  }
  const { error } = await useFetch(`${api}/products/${id}/package/${packageId.value}`, requestOptions)
}
</script>

  