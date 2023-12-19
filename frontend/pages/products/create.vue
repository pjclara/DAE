<template>
    <div>
        <v-col align="center">
            <v-col cols="6">
                <h1>Create a product</h1>
                <form @submit.prevent="create">
                    <div>
                        <v-text-field v-model="productForm.name" placeholder="Nome"/>
                    </div>
                    <div>
                        <v-text-field v-model="productForm.stock" label="Stock"/>
                    </div>
                    <div>
                        <v-text-field v-model="productForm.manufacturerUsername" label="Nome do Fabricante"/>
                    </div>
                    <div>
                        <v-text-field v-model="productForm.packageId" label="ID da embalagem do Produto"/>
                    </div>
                    <div>
                        <v-text-field v-model="productForm.image" label="URL Imagem de Produto"/>
                    </div>
                    <v-btn block rounded="xl" size="x-large" @click="create">Create</v-btn>
                    <div v-if="messages?.length > 0">
                        <h2>Messages</h2>
                        <div v-for="message in messages">
                            <pre>{{ message }}</pre>
                        </div>
                    </div>
                </form>
            </v-col>
        </v-col>
    </div>
</template>

<script setup>
const productForm = reactive({
    name: null,
    stock: null,
    manufacturer: null,
    productPackage: null,
    image: null
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL

async function create() {
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(productForm)
    }
    const { error } = await useFetch(`${api}/products`, requestOptions)
    if (!error.value) navigateTo('/products')
    message.value = error.value
}
</script>