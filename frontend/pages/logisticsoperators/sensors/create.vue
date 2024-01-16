<template>
        <v-col align="center">
            <v-col cols="6">
                <h1>Criar Sensor</h1>
                <form @submit.prevent="create">
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
                    <v-btn block rounded="xl" size="x-large" @click="create">Criar</v-btn>
                    <v-btn block rounded="xl" size="x-large" @click="back">Cancelar</v-btn>

                </form>
            </v-col>
        </v-col>
</template>

<script setup>
const sensorForm = reactive({
    source: "Order",
    type: null,
    unit: null,
    max: null,
    min: null
})

const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username

async function create() {
    const sensor = { ...sensorForm }
    console.log("JSON.stringify(sensor) : ", JSON.stringify(sensor))
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(sensor)
    }

    const { error } = await useFetch(`${api}/sensors`, requestOptions)
    if (!error.value) navigateTo(`/logisticsOperators/${username}/sensors`)
    console.log("error.value: ", error.value)
}

const back = () => navigateTo(`/logisticsOperators/${username}/sensors`)
</script>