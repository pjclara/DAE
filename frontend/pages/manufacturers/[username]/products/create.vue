<template>
    <div>
        <v-col align="center">
            <v-col cols="6">
                <h1>Create a product</h1>
                <form @submit.prevent="create">
                    <div>
                        <v-text-field v-model="productForm.name" placeholder="Nome" required/>
                    </div>
                    <div>
                        <v-text-field v-model="productForm.stock" label="Stock" required/>
                    </div>
                    <div>
                            <v-select v-model="productForm.packageId" :items="packagesList" item-title="packagingMaterial"
                                item-value="id" label="Package" />
                    </div>
                    <div>
                        <v-text-field v-model="productForm.image" label="URL Imagem de Produto"/>
                    </div>
                    <v-btn block rounded="xl" size="x-large" @click="create">Create</v-btn>
                   
                </form>
            </v-col>
        </v-col>
        
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)

const headers = [
    { text: 'Name', value: 'name' },
    { text: 'Stock', value: 'stock' },
    { text: 'Package', value: 'packageId' },
    { text: 'Image', value: 'image' }
]

const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const productForm = reactive({
    name: null,
    stock: null,
    manufacturerUsername: route.params.username,
    packageId: null,
    image: null
})
const { data: packagesList, packageError: productsErr } = await
useFetch(`${api}/packages/packagingType/PRIMARY`)


async function create() {
    const requestOptions = {
        method: 'POST',
        headers: { 
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value 
        },
        body: JSON.stringify(productForm)
    }
    const { error } = await useFetch(`${api}/products`, requestOptions)
    if (!error.value) navigateTo('/manufacturers/' + route.params.username + '/products')

}
</script>