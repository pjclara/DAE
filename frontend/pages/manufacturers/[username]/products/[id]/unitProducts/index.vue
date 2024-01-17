<template>
    <h1>List of unit products</h1>
    <v-data-table :headers="headers" :items="unitProducts" :items-per-page="5" class="elevation-1">
        <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
        </template>
        <template v-slot:item.packageSensorDTO.sensorValueDTOS="{ item }">
            <v-chip v-for="sensor in item.packageSensorDTO.sensorValueDTOS" :key="sensor.id" class="mr-2" color="primary" text-color="white">
                {{ sensor.sensorDTO.type }}: {{ sensor.value  }}</v-chip>   
        </template>
    </v-data-table>
    <v-btn color="primary" @click="goBack">Back</v-btn>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const id = route.params.id
const username = route.params.username
const { data: unitProducts, error, refresh } = await useFetch(`${api}/products/${id}/unitProducts`)

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
</script>