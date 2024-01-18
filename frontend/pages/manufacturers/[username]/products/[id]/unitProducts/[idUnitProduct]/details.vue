<template>
    <v-card>
        <v-toolbar color="primary" dark>
            <v-btn icon @click="goBack">
                <v-icon>mdi-arrow-left</v-icon>
            </v-btn>
            <v-toolbar-title>Add Sensors</v-toolbar-title>
        </v-toolbar>
        <v-container>
            <v-row>
                <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="unitProductItem.serialNumber" label="Serial Number" readonly></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">

                    <v-text-field readonly>
                        <v-chip color="green" v-if="unitProductItem.available">Available</v-chip>
                        <v-chip color="red" v-else>Unavailable</v-chip>
                    </v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="12" sm="6" md="4" v-for="sensor in unitProductItem.packageSensorDTO?.sensorValueDTOS">
                    <v-text-field v-if="sensor.sensorDTO.type" v-model="sensor.sensorDTO.type" label="Sensor"
                        readonly></v-text-field>
                </v-col>
            </v-row>
        </v-container>
        <v-container >
            <v-row>
                <h3>Add a Sensor to Unit Product</h3>
            </v-row>
            <v-row>
                <v-col cols="12" sm="6" md="4">
                    <v-select v-model="sensorId" :items="sensorList" item-title="type" item-value="id" label="Sensor"
                        multiple></v-select>
                </v-col>
            </v-row>
            <v-row>
                <v-btn color="primary" @click="addSensor">Add Sensors</v-btn>
            </v-row>
        </v-container>
    </v-card>
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

const unitProductItem = ref([])
const sensorList = ref([])

const getData = async () => {
    const { data: unitProduct, error, refresh } = await useFetch(`${api}/unitProducts/${idUnitProduct}`)
    const { data: sensors, errorSensors, refreshSensors } = await useFetch(`${api}/products/${id}/sensorsNotAttribute`)
    unitProductItem.value = unitProduct.value
    sensorList.value = sensors.value
}

const goBack = () => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/unitProducts/')
}

onMounted(async () => {
    getData()
})

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