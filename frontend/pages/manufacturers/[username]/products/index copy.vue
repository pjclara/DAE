<template>
    <div>
        <nuxt-link :to="`/manufacturers/${username}/details`">Back to manufacturer details</nuxt-link>
         | 
        <nuxt-link :to="`/manufacturers/${username}/products/create`">Add a product</nuxt-link>

        <v-data-table
          :headers="headers"
          :items="products"
          :items-per-page="5"
        >
            <template v-slot:item.actions="{ item }">
                <v-icon
                    size="small"
                    class="me-2"
                    @click="editItem(item)"
                >
                    mdi-pencil
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>
<script setup>
import { reactive } from 'vue';

const headers = ref([
    { text: 'Name', value: 'name' },
    { text: 'Stock', value: 'stock' },
    { text: 'Package', value: 'packageId' },
    { text: 'Image', value: 'image' },
    { text: 'Actions', value: 'actions', sortable: false }
])
const editItem =  (item)=> {
        navigateTo('/manufacturers/' + route.params.username + '/products/' + item.id + '/edit/')
      }
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const { data: products, error: productsErr } = await
useFetch(`${api}/manufacturers/${username}/products`)

</script>