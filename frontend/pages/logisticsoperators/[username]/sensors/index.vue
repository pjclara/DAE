<template>
    <div>
        <v-row justify="space-between" class="ma-3">
            <h2>Lista de Sensores</h2>
            <v-btn @click="addSensor">Criar sensor</v-btn>
        </v-row>
        <v-data-table :headers="headers" :items="sensors" :items-per-page="15" class="elevation-1">
            <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

const route = useRoute()
const username = route.params.username

const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: sensors, error, refresh } = await useFetch(`${api}/sensors/source/Orders`)

const headers = ref([
    { title: 'Fonte', value: 'source', align: 'center' },
    { title: 'Tipo', value: 'type', align: 'center' },
    { title: 'Unidade', value: 'unit', align: 'center' },
    { title: 'Máximo', value: 'max', align: 'center' },
    { title: 'Mínimo', value: 'min', align: 'center' },
    { title: '', value: 'actions', align: 'center' }
])

const addSensor = () => navigateTo(`/logisticsOperators/${username}/sensors/create`)
const editItem = (item) => {
    navigateTo(`/logisticsOperators/${username}/sensors/${item.id}/edit`)
}



</script>