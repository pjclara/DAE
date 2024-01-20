<template>
    <v-toolbar color="primary" dark>
        <v-btn icon @click="back">
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
        <v-toolbar-title>Go Back</v-toolbar-title>
    </v-toolbar>
    <br>
    <v-card color="green" class="m-2">
        <h1>Lista de productos unitários</h1>
        <v-data-table :headers="headers" :items="unitProductList" :items-per-page="5" class="elevation-1">
            <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            </template>
            <template v-slot:item.available="{ item }">
                <v-icon small class="mr-2" v-if="item.available">mdi-check</v-icon>
                <v-icon small class="mr-2" v-else>mdi-close</v-icon>
            </template>
            <template v-slot:item.packageSensorDTO="{ item }">
                <div v-if="item.packageSensorDTO">
                    <v-chip v-for="sensor in item.packageSensorDTO.sensorValueDTOS" color="primary" text-color="white">{{
                        sensor.sensorDTO.type }}</v-chip>
                </div>
                <div v-else>
                    <v-chip color="primary" text-color="white">No sensors</v-chip>
                </div>
            </template>

        </v-data-table>
    </v-card>
    <br>
    <v-card>
        <v-container v-if="sensorList.length > 0 && unitProductList[0]?.productDTO?.packageProductId != 0">
            <v-row>
                <h3>Add a Sensor to all Unit Product</h3>
            </v-row>
            <v-row>
                <v-select v-model="sensorId" :items="sensorList" item-title="type" item-value="id" label="Sensor"
                    multiple></v-select>
            </v-row>
            <v-row>
                <v-btn color="primary" @click="addSensor">Add Sensors</v-btn>
            </v-row>
        </v-container>
    </v-card>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const sensorId = ref(null)
const message = ref('')
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)
const route = useRoute()
const id = route.params.id
const username = route.params.username

const unitProductList = ref([])
const sensorList = ref([])

//console.log(unitProductList.value)
//console.log(sensorList.value)

const getData = async () => {
    const { data: unitProducts, error, refresh } = await useFetch(`${api}/products/${id}/unitProducts`)
    const { data: sensors, errorSensors, refreshSensors } = await useFetch(`${api}/products/${id}/sensorsNotAttribute`)
    unitProductList.value = unitProducts.value
    sensorList.value = sensors.value
}

onMounted(async () => {
    getData()
})

const headers = ref([
    { title: 'SerialNumber', value: 'serialNumber', align: 'center' },
    { title: 'Available', value: 'available', align: 'center' },
    { title: 'Product', value: 'productDTO.name', align: 'center' },
    { title: 'Sensors', value: 'packageSensorDTO', align: 'center' },
    { title: '', value: 'actions', align: 'center' }
])

const editItem = (item) => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/unitProducts/' + item.id + '/details/')
}

const back = () => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/details/')
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
        const { error } = useFetch(`${api}/products/${id}/addSensor/` + element, requestOptions)
        if (error.value) {
            message.value = error.value
            //console.log(message.value)
            alert(message.value)
        } else {
            alert("Sensor adicionado com successo")
            getData()
        }
    });
}
</script>