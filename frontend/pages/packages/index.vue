<template>
    <div>
        <v-row justify="space-between" class="ma-3">
            <h2>Embalagens</h2>
            <v-btn><nuxt-link to="/packages/create">Criar embalagem</nuxt-link></v-btn>
        </v-row>
        <div class="w-100" v-if="user.role === 'LogisticsOperator'">
            <v-data-table :headers="headers" :items="logisticsOperatorsPackages" :items-per-page="15" class="elevation-1">
    
                <template v-slot:item.action="{ item }">
                    <v-btn class="w-25 me-2" @click="open(item)">OPEN</v-btn>
                    <v-btn class="w-25" @click="edit(item)">EDIT</v-btn>
                </template>
            </v-data-table>
        </div>
        <div class="w-100" v-else>
            <v-data-table :headers="headers" :items="manufacturersPackages" :items-per-page="15" class="elevation-1">    
                <template v-slot:item.action="{ item }" class="d-flex">
                    <v-btn class="w-25 me-2" @click="open(item)">OPEN</v-btn>
                    <v-btn class="w-25" @click="edit(item)">EDIT</v-btn>
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

const { user } = storeToRefs(authStore)

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
const manufacturersPackages = packages.value.filter(productPackage => productPackage.packagingType !== 'TRANSPORT')
const logisticsOperatorsPackages = packages.value.filter(productPackage => productPackage.packagingType === 'TRANSPORT')


//console.log("packages: ", packages.value);

// const getPackages = () => {
//     const items = (packages.value || []).map(package_ => {
//         return {
//             id: package_.id,
//             type: package_.packagingType || '-',
//             material: package_.packagingMaterial || '-',
//         }
//     })
//     return items;
// }
const edit = (item) => {
    navigateTo(`/packages/${item.id}/edit`)
}

const open = (item) => {
    navigateTo(`/packages/${item.id}/details`)
}

const headers = [
    {
        title: 'Tipo',
        align: 'center',
        value: 'packagingType',
    },
    {
        title: 'Material de embalamento',
        align: 'center',
        value: 'packagingMaterial',
    },
    {
        title: '',
        align: 'center',
        value: 'action',
    }
]

</script>

<style></style>

