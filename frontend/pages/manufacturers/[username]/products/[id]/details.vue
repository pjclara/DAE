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
                src="data:image/jpeg;base64,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" />
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

  