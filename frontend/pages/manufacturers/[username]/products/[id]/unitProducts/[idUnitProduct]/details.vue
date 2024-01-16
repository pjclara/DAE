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
                <v-row>
                    <h1>List of sensors</h1>
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
    </v-card>
    <v-btn color="primary" @click="goBack">Back</v-btn>
</template>

<script setup>
const message = ref('')
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

const goBack = () => {
    navigateTo('/manufacturers/' + username + '/products/' + id + '/unitProducts/')
}

</script>