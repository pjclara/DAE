<template>
    <v-card color="green">
        <h1>List of unit products</h1>
        <v-data-table :headers="headers" :items="unitProductList" :items-per-page="5" class="elevation-1">
            <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            </template>
            <template v-slot:item.packageSensorDTO.sensorValueDTOS="{ item }">
                <v-chip v-for="sensor in item.packageSensorDTO.sensorValueDTOS" :key="sensor.id" class="mr-2"
                    color="primary" text-color="white">
                    {{ sensor.sensorDTO.type }}: {{ sensor.value }}</v-chip>
            </template>
        </v-data-table>
        <v-btn color="primary" @click="goBack">Back</v-btn>
    </v-card>
    <v-card>
        {{ unitProductList[0] }}
        <v-container>
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
const { data: unitProducts, error, refresh } = await useFetch(`${api}/products/${id}/unitProducts`)
const { data: sensors, errorSensors, refreshSensors } = await useFetch(`${api}/products/${id}/sensorsNotAttribute`)

const unitProductList = ref([])
const sensorList = ref([])

onMounted(async () => {
    unitProductList.value = unitProducts.value
    sensorList.value = sensors.value
})

const headers = ref([
    { title: 'SerialNumber', value: 'serialNumber', align: 'center' },
    { title: 'Available', value: 'available', align: 'center' },
    { title: 'Product', value: 'productDTO.name', align: 'center' },
    { title: 'Sensors', value: 'packageSensorDTO.sensorValueDTOS', align: 'center' },
    { title: '', value: 'actions', align: 'center' }
])

const editItem = (item) => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/unitProducts/' + item.id + '/details/')
}

const goBack = () => {
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
            console.log(message.value)
            alert(message.value)
        }
    });

    if (!error.value) {
        navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/unitProducts/')
        message.value = "Sensors added successfully"
        alert(message.value)
    }
    else {
        message.value = error.value
        console.log(message.value)
    }
}
</script>