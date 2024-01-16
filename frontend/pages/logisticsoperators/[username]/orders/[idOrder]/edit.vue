<template>
    <div>
        <h1>Order Edit</h1>
        <v-card rounded="xl" color="blue">
            <v-card-title>
                <span class="grey--text">Consumer Name: </span>
                <span class="headline">{{ order.endConsumerName }}</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12" sm="6">
                            <span class="grey--text">Status: </span>
                            <span>{{ order.status }}</span>
                        </v-col>
                        <v-col cols="12" sm="6">
                            <span class="grey--text">Package: </span>
                            <select v-model="packageOrderId" style="background-color: aliceblue; width: 300px;">
                                <option value="0">Please select one</option>
                                <option v-for="packageOrder in packagesList" :key="packageOrder.id"
                                    :value="packageOrder.id">{{ packageOrder.packagingMaterial }}</option>
                            </select>
                        </v-col>
                    </v-row>

                </v-container>
            </v-card-text>
        </v-card>
        <br>
        <v-btn block rounded="xl" size="x-large" @click="update()" color="green">Update order</v-btn>
        <br>
        <v-btn block rounded="xl" size="x-large" @click="back" color="black">Back</v-btn>
    </div>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const idOrder = route.params.idOrder
const { data: order, error } = await useFetch(`${api}/orders/${idOrder}`)
const packageOrderId = ref(order.packageId)

const back = () => navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packageProducts/type/SECONDARY`)

const update = async () => {
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
    }
    const { error } = await useFetch(`${api}/orders/${idOrder}/package/${packageOrderId.value}`, requestOptions)

    if (error) {
        console.log(error)
    } else {
        navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
    }
}
</script>