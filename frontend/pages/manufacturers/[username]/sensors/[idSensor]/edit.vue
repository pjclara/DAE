<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Editar Sensor</h1>
            <form @submit.prevent="update">
                <div>
                    <v-select v-model="sensorForm.type"
                        :items="['Temperatura', 'Humidade', 'Pressão', 'Integridade', 'Localização']" label="Tipo">
                    </v-select>
                </div>
                <div>
                    <v-text-field v-model="sensorForm.unit" label="Unidade" />
                </div>
                <div>
                    <v-text-field v-model="sensorForm.max" label="Max" />
                </div>
                <div>
                    <v-text-field v-model="sensorForm.min" label="Min" />
                </div>
                <v-btn block rounded="xl" size="x-large" @click="update">Editar</v-btn>
            </form>
        </v-col>
    </v-col>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

const route = useRoute()
const username = route.params.username
const idSensor = route.params.idSensor

const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: sensor, error, refresh } = await useFetch(`${api}/sensors/${idSensor}`)
const sensorForm = reactive({
    source: "Product",
    type: sensor.value.type,
    unit: sensor.value.unit,
    max: sensor.value.max,
    min: sensor.value.min
})

async function update() {
    const sensor = { ...sensorForm }
    console.log("JSON.stringify(sensor) : ", JSON.stringify(sensor))
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(sensor)
    }

    const { error } = await useFetch(`${api}/sensors/${idSensor}`, requestOptions)
    if (!error.value) navigateTo(`/manufacturers/${username}/sensors`)
    console.log("error.value: ", error.value)
}

const back = () => navigateTo(`/manufacturers/${username}/sensors`)
</script>