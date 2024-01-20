<template>
    <v-card>
        <v-toolbar color="primary" dark>
            <v-btn icon @click="goBack">
                <v-icon>mdi-arrow-left</v-icon>
            </v-btn>
            <v-toolbar-title>Voltar</v-toolbar-title>
        </v-toolbar>
    </v-card>
    <div>
        <h1>Editar embalagem</h1>
        <form @submit.prevent="update">
            <div>
                <v-select v-model="packageForm.packagingType" :items="packagingTypes" item-value="value" item-text="title"
                    label="Tipo de Embalagem">
                </v-select>
            </div>
            <div>
                <v-text-field label="Material" v-model="packageForm.packagingMaterial" type="text"
                    placeholder="material"></v-text-field>
            </div>
            <v-btn type="reset">RESET</v-btn>
            <v-btn type="submit">Atualizar</v-btn>
        </form>
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const idPackageProduct = route.params.idPackageProduct
const username = route.params.username

const { data: packageForm, error: manufacturerErr } = await useFetch(`${api}/packageProducts/${idPackageProduct}`)


const packagingTypes = ref([
    { title: 'Primário', value: 0 },
    { title: 'Secundário', value: 1 },
    { title: 'Terciário', value: 2 },
])
async function update() {
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(packageForm.value)
    }
    const { error } = await useFetch(`${api}/packageProducts/` + idPackageProduct, requestOptions)
    if (!error.value) {
        navigateTo(`/manufacturers/${username}/packageProducts/`)
        alert("Package updated successfully")
    }
    else {
        message.value = error.value
    }
}

const goBack = () => {
    navigateTo(`/manufacturers/${username}/packageProducts/`)
}

</script>
