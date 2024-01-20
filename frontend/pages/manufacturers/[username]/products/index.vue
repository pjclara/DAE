<template>
    <v-data-table :headers="headers" :items="products" :sort-by="[{ key: 'name', order: 'asc' }]">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Produtos do Fabricante : {{ username }}</v-toolbar-title>
          <v-btn @click="addNewItem" color="blue">
            Criar novo Produto
            <v-icon class="grey--text">mdi-plus</v-icon>
          </v-btn>

        </v-toolbar>
      </template>
      <template v-slot:item.image="{ item }">
        <v-img :width="33" aspect-ratio="16/9" cover :src="item.image"></v-img>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon size="small" class="me-2" @click="itemDetails(item)">
          mdi-eye
        </v-icon>
      </template>
      <!-- <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">
          Reset
        </v-btn>
      </template> -->
    </v-data-table>
</template>
<script setup>
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
    title: 'Producto',
    align: 'start',
    sortable: false,
    key: 'name',
  },
  { title: 'Stock', key: 'stock' },
  { title: 'Embalagem', key: 'packagingMaterial' },
  { title: 'Imagem', key: 'image' },
  { title: 'Detalhes', key: 'actions', sortable: false },
]
const { data: products, error: productsErr } = await
  useFetch(`${api}/manufacturers/${username}/products`, {
    method: 'get',
    headers: {
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + token.value
    }
  })
console.log("products :", products.value)

const editItem = (item) => {
  navigateTo('/manufacturers/' + route.params.username + '/products/' + item.id + '/edit/')
}
const itemDetails = (item) => {
  navigateTo('/manufacturers/' + route.params.username + '/products/' + item.id + '/details/')
}
const addNewItem = () => {
  navigateTo('/manufacturers/' + route.params.username + '/products/create/')
}

</script>