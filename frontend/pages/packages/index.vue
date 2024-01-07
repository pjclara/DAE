<template>
    <div>
        <v-row justify="space-between" class="ma-3">
            <h2>Embalagens</h2>
            <v-btn><nuxt-link to="/packages/create">Criar embalagem</nuxt-link></v-btn>
        </v-row>
        <div class="w-100">
            <v-data-table :headers="headers" :items="getPackages()" :items-per-page="5" class="elevation-1">
    
                <template v-slot:item.action="{ item }">
                    <v-btn @click="edit(item)">OPEN</v-btn>
                </template>
            </v-data-table>
        </div>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const config = useRuntimeConfig()
const api = config.public.API_URL
const messages = ref([])

const { data: packages, error, refresh } = await useFetch(`${api}/packages`, {
    method: 'get',
    headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + authStore.token
    }
})
if (error.value) {
    messages.value.push({ error: error.value.message })
}

console.log("packages: ", packages.value);

const getPackages = () => {
    const items = (packages.value || []).map(package_ => {
        return {
            id: package_.id,
            type: package_.packagingType || '-',
            material: package_.packagingMaterial || '-',
        }
    })
    return items;
}
const edit = (item) => {
    navigateTo(`/packages/${item.id}/edit`)
}

const headers = [
    {
        title: 'Type',
        align: 'center',
        value: 'type',
    },
    {
        title: 'Material',
        align: 'center',
        value: 'material',
    },
    {
        title: 'Action',
        align: 'center',
        value: 'action',
    },
]

</script>

<style></style>

