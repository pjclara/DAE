<template>
    <v-data-table
      :headers="headers"
      :items="products"
      :sort-by="[{ key: 'name', order: 'asc' }]"
    >
      <template v-slot:top>
        <v-toolbar
          flat
        >
          <v-toolbar-title>My Products</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          Add a new product
          <v-icon
            class="grey--text"
            @click="addNewItem"
            >mdi-plus</v-icon>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
          size="small"
          class="me-2"
          @click="editItem(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
          size="small"
          @click="deleteItem(item)"
        >
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn
          color="primary"
          @click="initialize"
        >
          Reset
        </v-btn>
      </template>
    </v-data-table>
  </template>
  <script setup>
    import { ref } from 'vue'
    const dialog = ref(false)
    const dialogDelete = ref(false)
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
        useFetch(`${api}/manufacturers/${username}/products`)

    const editItem =  (item)=> {
        navigateTo('/manufacturers/' + route.params.username + '/products/' + item.id + '/edit/')
      }
    const addNewItem =  ()=> {
        navigateTo('/manufacturers/' + route.params.username + '/products/create/')
      }
    
  </script>