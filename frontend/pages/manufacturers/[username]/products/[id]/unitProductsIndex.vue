<template>
    <v-data-table :headers="headers" :items="products" :sort-by="[{ key: 'name', order: 'asc' }]">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Produtos Unitários</v-toolbar-title>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon size="small" class="me-2" @click="itemDetails(item)">
          mdi-eye
        </v-icon>
      </template>
    </v-data-table>
</template>
<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)

const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const id = route.params.id
const headers = [
  { title: 'Número de série', key: 'serialNumber' },
  { title: 'Disponivel', key: 'available' },
  { title: 'Actions', key: 'actions', sortable: false },
]
const { data: products, error: productsErr } = await
  useFetch(`${api}/manufacturers/${username}/products/${id}/unitProducts`, {
    method: 'get',
    headers: {
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + token.value
    }
  })
console.log("products :", products.value)

const itemDetails = (item) => {
  navigateTo('/unitProducts/' + item.id + '/details')
}

</script>