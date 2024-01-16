<template>
    <h1>List of orders</h1>
    {{ orders  }}
    <v-data-table :headers="headers" :items="orders" :items-per-page="5" class="elevation-1">
        <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="detailsOrder(item)">mdi-eye</v-icon>
            <v-icon small class="mr-2" @click="sendOrder(item)">mdi-send</v-icon>
            <v-icon small @click="cancelOrder(item)">mdi-close</v-icon>
        </template>
    </v-data-table>
</template>

<script setup>
    import {useAuthStore} from "~/store/auth-store.js"
    const authStore = useAuthStore()
    const {user} = storeToRefs(authStore)

    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: orders, error, refresh } = await useFetch(`${api}/orders`)
    
</script>