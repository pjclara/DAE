<template>
    <v-row justify="space-between" class="ma-3">
        <h2>Lista de Embalagens</h2>
        <v-btn @click="addPackageProduct">Criar embalagem</v-btn>
    </v-row>
    <div>
        <v-data-table :headers="headers" :items="packageProducts" :items-per-page="5" class="elevation-1">
            <template v-slot:item.actions="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: packageProducts, error, refresh } = await useFetch(`${api}/packageProducts`)
const route = useRoute()
const username = route.params.username

const headers = ref([
    { title: 'Type', value: 'packagingType', align: 'center' },
    { title: 'Material', value: 'packagingMaterial', align: 'center' },
    { title: '', value: 'actions', align: 'center' }
])

const editItem = (item) => {
    console.log(item)
   // this.$router.push(`/manufacturers/${username}/packageProducts/${item.id}/edit`)
}

const addPackageProduct = () => navigateTo(`/manufacturers/${username}/packageProducts/create`)
</script>