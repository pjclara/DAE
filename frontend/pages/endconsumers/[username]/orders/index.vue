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
                    <v-btn icon size="small" class="mr-2" @click="detailsOrder(item)"><v-icon>mdi-eye-circle</v-icon></v-btn>
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
        { title: 'Estado', value: 'status', align: 'center' },
        { title: 'Cliente', value: 'endConsumerName', align: 'center' },
        { title: 'Embalagem', value: 'packageId', align: 'center' },
        { title: 'Produtos', value: 'productsIds', align: 'center' },
        { title: '', value: 'actions', align: 'center' }
    ])

    const detailsOrder = (item) => {
        navigateTo('/orders/' + item.id + '/details')
    }

</script>