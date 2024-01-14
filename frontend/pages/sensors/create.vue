<template>
        <v-col align="center">
            <v-col cols="6">
                <h1>Criar Sensor</h1>
                <form @submit.prevent="create">
                    <div>
                        <v-select v-model="sensorForm.source" :items="['Embalagem', 'Produto']" label="Fonte">
                        </v-select>
                    </div>
                    <div>
                        <v-select v-model="sensorForm.type"
                            :items="['Temperatura', 'Humidade', 'Pressão', 'Integridade', 'Localização']" label="Tipo">
                        </v-select>
                    </div>
                    <div>
                        <v-text-field v-model="sensorForm.value" label="Valor" />
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
                </form>
            </v-col>
        </v-col>
</template>

<script setup>
const sensorForm = reactive({
    source: null,
    type: null,
    value: null,
    unit: null,
    unit: null,
    max: null,
    min: null,
    packageId: null,
    timestamp: null
})

const config = useRuntimeConfig()
const api = config.public.API_URL


async function create() {
    const sensor = { ...sensorForm, packageId: 0, timestamp: Date.now() }
    console.log("JSON.stringify(sensor) : ", JSON.stringify(sensor))
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(sensor)
    }
    const { error } = await useFetch(`${api}/sensors`, requestOptions)
    if (!error.value) navigateTo('/sensors')
    console.log("error.value: ", error.value)
}
</script>