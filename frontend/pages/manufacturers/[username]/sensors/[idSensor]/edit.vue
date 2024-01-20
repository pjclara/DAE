<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Editar Sensor</h1>
            <form @submit.prevent="update">
                <div>
                    <v-select v-model="sensorForm.type"
                        :items="['Temperatura', 'Humidade', 'Pressão', 'Integridade', 'Localização']" label="Tipo"
                        :rules="isTypeValid ? [] : [formFeedback.type]">
                    </v-select>
                </div>
                <div>
                    <v-text-field v-model="sensorForm.unit" label="Unidade"
                    :rules="isUnitValid ? [] : [formFeedback.unit]" />
                </div>
                <div>
                    <v-text-field v-model="sensorForm.max" label="Max"
                    :rules="isMaxValid ? [] : [formFeedback.max]" />
                </div>
                <div>
                    <v-text-field v-model="sensorForm.min" label="Min"
                    :rules="isMinValid ? [] : [formFeedback.min]" />
                </div>
                <v-btn block rounded="xl" size="x-large" @click="update">Editar</v-btn>
                <v-btn block rounded="xl" size="x-large" @click="back">Cancelar</v-btn>
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

const isTypeValid = computed(() => {
    if (!sensorForm.type) {
        formFeedback.type = 'type is required'
        return false
    }
    return true
})

const isUnitValid = computed(() => {
    if (!sensorForm.unit) {
        formFeedback.unit = 'unit is required'
        return false
    }
    return true

})

const isMaxValid = computed(() => {
    if (!sensorForm.max) {
        formFeedback.max = 'max is required'
        return false
    }
    return true
})

const isMinValid = computed(() => {
    if (!sensorForm.min) {
        formFeedback.min = 'min is required'
        return false
    }
    return true
})

const isFormValid = computed(() => {
    return isTypeValid.value && isUnitValid.value && isMaxValid.value && isMinValid.value
})

const formFeedback = reactive({
    type: '',
    unit: '',
    max: '',
    min: ''
})


async function update() {
    if(!isFormValid.value) {
        alert('Por favor preencha os campos corretamente')
        return;
    }
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