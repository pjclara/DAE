<template>
    <div>
        <v-data-table
          :headers="headers"
          :items="orders"
          :items-per-page="5"
          class="elevation-1"
        >
            <template v-slot:item.actions="{ item }">
                <v-btn icon size="small" class="mr-2" @click="detailsOrder(item)"><v-icon>mdi-eye-circle</v-icon></v-btn>
                <v-btn v-if="item.status === 'Pending'" class="mr-2" icon size="small" @click="sendOrder(item)"><v-icon>mdi-shipping-pallet</v-icon></v-btn>
                <v-btn v-if="item.status !== 'Canceled' && item.status !== 'Delivered'" icon size="small" @click="cancelOrder(item)"><v-icon>mdi-cancel</v-icon></v-btn>
            </template>   
        </v-data-table>
    </div>
</template>

<script setup>
    import {useAuthStore} from "~/store/auth-store.js"
    const authStore = useAuthStore()
    const {user} = storeToRefs(authStore)

    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: orders, error, refresh } = await useFetch(`${api}/orders`)
    const order = ref({});

    const headers = ref([
        { title: 'Estado', value: 'status', align: 'center' },
        { title: 'Cliente', value: 'endConsumerName', align: 'center' },
        { title: 'Operador Logistica', value: 'logisticsOperatorName', align: 'center' },
        { title: '', value: 'actions', align: 'center' }
    ])

    const detailsOrder = (item) => {
        navigateTo('/orders/' + item.id + '/details')
    }

    const sendOrder = (item) => {
        console.log("item: ", item)
        order.value = {
            ...item,
            status: "In Transit",
            logisticsOperatorName: user.value.username
        };
        console.log("order.value: ", order.value)

        console.log("user.value: ", user.value)
        sendOrderAPI();
    }

    async function sendOrderAPI() {
        const { data, error } = await useFetch(`${api}/orders/${order.value.id}`, {
            method: "put",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(order.value)
        })
        if (error.value) {
            console.log('error.value :', error.value)
        }
        if (data.value) {
            console.log('data :', data.value)
            //navigateTo('/')
        }
    }

    const cancelOrder = (item) => {
        console.log("item: ", item)
        order.value = {
            ...item,
            status: "Canceled",
            logisticsOperatorName: user.value.username
        };
        console.log("order.value: ", order.value)

        console.log("user.value: ", user.value)
        cancelOrderAPI();
    }

    async function cancelOrderAPI() {
        const { data, error } = await useFetch(`${api}/orders/${order.value.id}`, {
            method: "put",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(order.value)
        })
        if (error.value) {
            console.log('error.value :', error.value)
        }
        if (data.value) {
            console.log('data :', data.value)
            //navigateTo('/')
        }
    }
</script>