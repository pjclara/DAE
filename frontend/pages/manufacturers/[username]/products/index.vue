<template>
  <default>
    <v-data-table :headers="headers" :items="products" :sort-by="[{ key: 'name', order: 'asc' }]">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>My Products</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          Add a new product
          <v-icon class="grey--text" @click="addNewItem">mdi-plus</v-icon>
          
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon size="small" class="me-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">
          Reset
        </v-btn>
      </template>
    </v-data-table>
  </default>
</template>
<script setup>
import Default from '/pages/layouts/default.vue'
import { ref } from 'vue'
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)

const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const headers = [
  {
    title: 'Product Name',
    align: 'start',
    sortable: false,
    key: 'name',
  },
  { title: 'Stock', key: 'stock' },
  { title: 'Package', key: 'packageId' },
  { title: 'Image', key: 'image' },
  { title: 'Actions', key: 'actions', sortable: false },
]
const { data: products, error: productsErr } = await
  useFetch(`${api}/manufacturers/${username}/products`, {
    method: 'get',
    headers: {
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + token.value
    }
  })

const editItem = (item) => {
  navigateTo('/manufacturers/' + route.params.username + '/products/' + item.id + '/edit/')
}
const addNewItem = () => {
  navigateTo('/manufacturers/' + route.params.username + '/products/create/')
}

</script>