<template>
    <div>
        <v-toolbar color="primary" dark fixed app>
            <v-btn icon @click="back">
                <v-icon>mdi-arrow-left</v-icon>
            </v-btn>
            <v-toolbar-title>Order Edit</v-toolbar-title>
        </v-toolbar>
        <h1>Order Edit</h1>
        <v-card rounded="xl" style="margin: 20px;">
            <v-card-title>
                <span class="grey--text">Consumer Name: </span>
                <span class="headline">{{ order.endConsumerName }}</span>
            </v-card-title>
        </v-card>

        <v-card rounded="xl" style="margin: 20px;">
            <v-card-text>
                <v-container>
                    <v-row>
                        <h3>Add or update the package</h3>
                    </v-row>
                    <v-row>
                        <v-col cols="12" sm="6">
                            <v-select :items="packagesList" item-title="packagingMaterial" item-value="id"
                                v-model="packageOrderId" label="Package order">
                            </v-select>
                        </v-col>
                        <v-col>
                            <v-btn block rounded="xl" size="x-large" @click="updatePackage()" color="green">Update
                                Package</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
        </v-card>

        <v-card rounded="xl" style="margin: 20px;" v-if="packageOrderId">
            <v-card-text>
                <v-container>
                    <v-row>
                        <h3>Add or update the sensors</h3>
                    </v-row>
                    <v-row>
                        <v-col cols="12" sm="6">
                            <v-row>
                                <v-select v-model="sensorId" :items="sensorNotInList"
                                    item-title="type" item-value="id" label="Sensor" multiple></v-select>
                            </v-row>
                        </v-col>
                        <v-col cols="12" sm="6">
                            <v-btn block rounded="xl" size="x-large" @click="updateSensor()" color="green">Update
                                Sensors</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
        </v-card>

        <v-card rounded="xl" style="margin: 20px;">
            <v-card-text>
                <v-container>
                    <v-row>
                        <h3>Update the status of the order</h3>
                    </v-row>
                    <v-row>
                        <v-col cols="12" sm="6">
                            <v-select :items="['IN_STOCK', 'IN_ORDER', 'CONFIRMED', 'RECEIVED', 'REJECTED']"
                                v-model="order.status" label="Status">
                            </v-select>
                        </v-col>
                        <v-col cols="12" sm="6">
                            <v-btn block rounded="xl" size="x-large" @click="updateStatus()" color="green">Update
                                Status</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
        </v-card>
    </div>
</template>
<script setup>
import { ref } from "vue";
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)

const config = useRuntimeConfig()
const api = config.public.API_URL

const route = useRoute()
const username = route.params.username
const idOrder = route.params.idOrder
const { data: order, error } = await useFetch(`${api}/orders/${idOrder}`)

const packageOrderId = ref(order.value.status)

const status = ref(order.value.status)

const sensorId = ref([])

// get all sensors in the order

const { data: sensorInList } = await useFetch(`${api}/orders/${idOrder}/sensorsInOrder`)

const { data: sensorNotInList } = await useFetch(`${api}/orders/${idOrder}/sensorsNotInOrder`)

// get all packages in the order
const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packageOrders`)
const pck = ref(packagesList.value)

// functions

const updatePackage = async () => {
    const requestOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        },
    }
    const { data: data, errorData } = await useFetch(`${api}/logisticsOperators/${username}/order/${idOrder}/package/${packageOrderId.value}`, requestOptions)
    if (errorData) {
        console.log(errorData)
    } else {
        //navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
        alert("Order updated successfully")
    }
}

const updateStatus = async () => {
    const requestOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        },
    }
    const { data: data, errorData } = await useFetch(`${api}/orders/${idOrder}/status/${order.value.status}`, requestOptions)
    if (errorData) {
        console.log(errorData)
    } else {
        //navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
        alert("Status updated successfully")
    }
}

const updateSensor = async () => {
    const requestOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        },
    }
    sensorId.value.forEach(element => {
        const { data: data, errorData } = useFetch(`${api}/orders/${idOrder}/addSensor/${element}`, requestOptions)
        if (errorData) {
            console.log(errorData)
        } else {
            //navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
            alert("Sensors updated successfully")
        }
    });
}

const back = () => navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/details`)
</script>