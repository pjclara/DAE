<template>
    <v-card>
        <v-card-title>
            <span class="headline">Unit product details</span>
        </v-card-title>
        <v-card-text>
            <v-container>
                <v-row>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="unitProduct.serialNumber" label="Serial Number" readonly></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="unitProduct.available" label="Available" readonly></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="unitProduct.productDTO.name" label="Product Name" readonly></v-text-field>
                    </v-col>
                </v-row>
            </v-container>
            <v-container>
                <v-row>
                    <h3>List of sensors</h3>
                </v-row>
                <v-row v-for="sensor in unitProduct.packageSensorDTO.sensorValueDTOS">
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="sensor.sensorDTO.source" label="Source" readonly></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="sensor.sensorDTO.type" label="Type" readonly></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="sensor.value" label="Value" readonly></v-text-field>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-text>
            <v-container>
                <v-row>
                    <h3>Add a Sensor</h3>
                    <v-select v-model="sensorId" :items="sensors" item-title="type" item-value="id" label="Sensor"
                        multiple></v-select>
                </v-row>
                <v-row>
                    <v-btn color="primary" @click="addSensor">Add Sensors</v-btn>
                </v-row>
            </v-container>
        </v-card-text>
    </v-card>
    <v-btn color="primary" @click="goBack">Back</v-btn>
</template>

<script setup>
const message = ref('')
const sensorId = ref(null)
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const id = route.params.id
const idUnitProduct = route.params.idUnitProduct
const { data: unitProduct, error, refresh } = await useFetch(`${api}/unitProducts/${idUnitProduct}`)
const { data: sensors, errorSensors, refreshSensors } = await useFetch(`${api}/unitProducts/${idUnitProduct}/sensorsNotAttribute`)

const goBack = () => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/unitProducts/')
}

const addSensor = async () => {
    if (sensorId.value == null) {
        message.value = "Please select a sensor"
        alert(message.value)
        return
    }
    const requestOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        },
    }
    sensorId.value.forEach(element => {
        const { error } = useFetch(`${api}/unitProducts/${idUnitProduct}/addSensor/` + element, requestOptions)
        if (!error.value) {
            message.value = "Sensors added successfully"
            alert(message.value)
        }
        else {
            message.value = error.value
            console.log(message.value)
        }
    });

    if (!error.value) {
        navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/unitProducts/')
        alert(message.value)
    }
    else {
        message.value = error.value
        console.log(message.value)
    }
}   
</script>