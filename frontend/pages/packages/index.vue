<template>
    <div class="w-100">
        <v-data-table :headers="headers" :items="getPackages()" :items-per-page="5" class="elevation-1">
            <template v-slot:top>
                <v-toolbar flat>
                    <v-toolbar-title>Embalagens disponiveis</v-toolbar-title>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-btn>OPEN</v-btn>
            </template>
        </v-data-table>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const config = useRuntimeConfig()
const api = config.public.API_URL

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
            type: package_.packagingType || '-',
            material: package_.packagingMaterial || '-',
        }
    })
    return items;
}
console.log("All Packages: ", getPackages());

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
]

</script>

<style></style>

