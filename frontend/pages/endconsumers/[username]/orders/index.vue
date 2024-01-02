<template>
    <div>
        <div v-if="orders.length > 0">
            <v-data-table
              :headers="headers"
              :items="orders"
              :items-per-page="5"
              class="elevation-1"
            >
                <template v-slot:item.actions="{ item }">
                    <v-btn size="small" class="mr-2" @click="detailsOrder(item)">Detalhes</v-btn>
                </template>   
            </v-data-table>
        </div>
        <div v-else>
            Sem Encomendas
        </div>
    </div>
</template>

<script setup>
    import {useAuthStore} from "~/store/auth-store.js"
    const authStore = useAuthStore()
    const {user} = storeToRefs(authStore)
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: orders, error, refresh } = await useFetch(`${api}/endConsumers/${user.value.username}/orders`)
    const order = ref({});

    const headers = ref([
        { title: 'Status', value: 'status', align: 'center' },
        { title: 'End Consumer', value: 'endConsumerName', align: 'center' },
        { title: 'Logistic Operator', value: 'logisticsOperatorName', align: 'center' },
        { title: 'Products', value: 'productsIds', align: 'center' },
        { title: '', value: 'actions', align: 'center' }
    ])

    const detailsOrder = (item) => {
        navigateTo('/orders/' + item.id + '/details')
    }

</script>