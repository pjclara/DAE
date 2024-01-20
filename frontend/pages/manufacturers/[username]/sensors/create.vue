<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Criar Sensor</h1>
            <form @submit.prevent="create">
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
                    <v-text-field v-model="sensorForm.max" label="Max" />
                </div>
                <div>
                    <v-text-field v-model="sensorForm.min" label="Min" />
                </div>
                <v-btn block rounded="xl" size="x-large" @click="create">Criar</v-btn>
                <v-btn block rounded="xl" size="x-large" @click="back">Cancelar</v-btn>

            </form>
        </v-col>
    </v-col>
</template>

<script setup>
const sensorForm = reactive({
    source: "Product",
    type: null,
    unit: null,
    max: null,
    min: null
})

const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username

// validate form

const isTypeValid = computed(() => {
    if (!sensorForm.type) {
        formFeedback.type = 'type is required'
        return false
    }
    return true
})

const isUnitValid = computed(() => {
    if (!sensorForm.unit) {
        {
            formFeedback.unit = 'unit is required'
            return false
        }
    }
    return true

})

const isMaxValid = computed(() => {
    if (!sensorForm.max) {
        formFeedback.max = 'max is required'
        return false
    }
})

const isMinValid = computed(() => {
    if (!sensorForm.min) {
        formFeedback.min = 'min is required'
        return false
    }
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



async function create() {

    const sensor = { ...sensorForm }
    console.log("JSON.stringify(sensor) : ", JSON.stringify(sensor))
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(sensor)
    }

    const { error } = await useFetch(`${api}/sensors`, requestOptions)

    if (!error.value) navigateTo(`/manufacturers/${username}/sensors`)
    console.log("error.value: ", error.value)
}

const back = () => navigateTo(`/manufacturers/${username}/sensors`)
</script>