<template>
    <div>
        <v-row justify="space-between" class="ma-3">
            <h2>Sensores</h2>
            <v-btn><nuxt-link to="/sensors/create">Criar sensor</nuxt-link></v-btn>
        </v-row>
        <div v-if="user?.role === 'Manufacturer'">
            <v-data-table
              :headers="headers"
              :items="manufacturerSensors"
              :items-per-page="15"
              class="elevation-1"
            />
        </div>
        <div v-else>
            <v-data-table
              :headers="headers"
              :items="logisticOperatorSensors"
              :items-per-page="15"
              class="elevation-1"
            />
        </div>
    </div>
</template>

<script setup>
    import {useAuthStore} from "~/store/auth-store.js"
    const authStore = useAuthStore()
    const {user} = storeToRefs(authStore)

    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: sensors, error, refresh } = await useFetch(`${api}/sensors`)

    const headers = ref([
        { title: 'Fonte', value: 'source', align: 'center' },
        { title: 'Tipo', value: 'type', align: 'center' },
        { title: 'Unidade', value: 'unit', align: 'center' },
        { title: 'Máximo', value: 'max', align: 'center' },
        { title: 'Mínimo', value: 'min', align: 'center' }
    ])

    const manufacturerSensors = (sensors.value || []).filter(sensor => sensor.source === 'Product')
    const logisticOperatorSensors = (sensors.value || []).filter(sensor => sensor.source === 'Order')
    
</script>