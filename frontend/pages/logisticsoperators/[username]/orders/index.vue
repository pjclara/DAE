<template>
    <div>
        <h1 class="text-center">Lista de Encomendas</h1>
        <div v-if="orders">
            <v-data-table :headers="headers" :items="orders" :items-per-page="5" class="elevation-1">
                <template v-slot:item.orderItems="{ item }">
                    <ul>
                        <li v-for="orderItem in item.orderItems" :key="orderItem.id">
                            {{ orderItem.unitProductDTO.productDTO.name }} ({{ orderItem.quantity }})
                        </li>
                    </ul>
                </template>
                <template v-slot:item.actions="{ item }">
                    <v-icon small class="mr-2" @click="detailsOrder(item)">mdi-eye</v-icon>
                </template>
            </v-data-table>
        </div>
        <div v-else class="text-center mt-2">
            Sem Encomendas
        </div>
    </div>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: orders, error, refresh } = await useFetch(`${api}/orders`)

const route = useRoute()
const username = route.params.username

const headers = [
    {
        title: 'Consumer Name',
        align: 'endConsumerName',
        sortable: false,
        key: 'endConsumerName',
    },
    { title: 'Status', key: 'status', sortable: false },
    { title: 'Products (quantity)', key: 'orderItems', sortable: false },
    { title: 'Package', key: 'packageOrder.packagingMaterial', sortable: false},
    { title: 'Details', key: 'actions', sortable: false },
]

const detailsOrder = (order) => {
    navigateTo(`/logisticsoperators/${username}/orders/${order.id}/details`)
    //console.log(order)
}
</script>