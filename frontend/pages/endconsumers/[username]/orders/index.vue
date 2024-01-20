<template>
    <div>
        <h1 class="text-center">Lista de Encomendas</h1>
        <div v-if="orders">
            <v-data-table :headers="headers" :items="orders" :items-per-page="5" class="elevation-1">
                <template v-slot:item.actions="{ item }">
                    <v-btn icon size="small" class="mr-2"
                        @click="detailsOrder(item)"><v-icon>mdi-eye-circle</v-icon></v-btn>
                </template>
                <template v-slot:item.productsIds="{ item }">
                    <span v-for="(item, index) in item.orderItems" :key="index" style="align-items: center;">
                        <v-img width="50" height="50" :src="item.unitProductDTO.productDTO.image" />{{ item.unitProductDTO.productDTO.name }}
                        <br>
                    </span>
                </template>
            </v-data-table>
        </div>
        <div class="text-center mt-2" v-else>
            Sem Encomendas
        </div>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)
const config = useRuntimeConfig()
const api = config.public.API_URL
const router = useRouter()
const route = useRoute()
const username = route.params.username

const { data: orders, error, refresh } = await useFetch(`${api}/endConsumers/${username}/orders`)

if (error.value) {
    //console.log(error.value)
}

const headers = ref([
    { title: 'Estado', value: 'status', align: 'center' },
    { title: 'Produtos', value: 'productsIds', align: 'center' },
    { title: '', value: 'actions', align: 'center' }
])

const detailsOrder = (item) => {
    navigateTo('/endConsumers/' + username + '/orders/' + item.id + '/details')
}

</script>