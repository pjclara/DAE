<template>
    <div>
        <v-col align="center">
            <v-col cols="6">
              <h1>Editar Producto</h1>
                <form @submit.prevent="update">
                    <div>
                        <v-text-field v-model="productForm.name" type="text" placeholder="Nome"/>
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
                    <v-btn block rounded="xl" size="x-large" @click="update">Update</v-btn>
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
    const message = ref('')
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const route = useRoute()
    const id = route.params.id
    const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`)
    const productForm = reactive(product)
    async function update() {
        console.log(productForm)
        const requestOptions = {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(productForm.value)
        }
        const { error } = await useFetch(`${api}/products/` + id, requestOptions)
        if (!error.value)
            navigateTo('/products')
        else {
            message.value = error.value
            console.log(message.value)
        }
    }
</script>

<style>
</style>