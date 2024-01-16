<template>
    <div>
        <div v-if="productForm">
            <v-col align="center">
                <v-col cols="6">
                    <h1>Editar Producto</h1>
                    <form @submit.prevent="update">
                        <div>
                            <v-text-field v-model="productForm.name" type="text" placeholder="Nome" />
                        </div>
                        <div>
                            <v-text-field v-model="productForm.stock" disabled label="Stock" />
                        </div>
                        <div>
                            <v-select v-model="productForm.packageProductId" :items="packagesList" item-title="packagingMaterial"
                            item-value="id" label="Package" />
                        </div>
                        <div>
                            <v-file-input @change="createImage" label="Imagem" />
                        </div>
                        <div>
                            <div>
                                <v-btn block rounded="xl" size="x-large" @click="update" class="mb-2">Update</v-btn>
                            </div>
                            <div>
                                <v-btn block rounded="xl" size="x-large" @click="cancel">Cancel</v-btn>
                            </div>
                        </div>
                        <div v-if="message?.length > 0">
                            <h2>Messages</h2>
                            <div v-for="msg in message">
                                <pre>{{ msg }}</pre>
                            </div>
                        </div>
                    </form>
                </v-col>
            </v-col>
        </div>
        <div v-else>
            <h1>Product not found</h1>
        </div>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
import { onMounted, ref } from "vue";
const message = ref('')

const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const id = route.params.id
const username = route.params.username
//const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packages/packagingType/PRIMARY`)

const base64 = ref('')

function createImage(e) {
    const file = e.target.files[0]
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => {
        base64.value = reader.result
        productForm.value.image = base64.value
    }
}
const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packageProducts/type/PRIMARY`)
const { data: product, error: productErr } = await useFetch(`${api}/products/${id}`, {
    method: 'get',
    headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + token.value
    }
})

console.log("product: ", product.value)

const productForm = reactive(product)

async function update() {
    const requestOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        },
        body: JSON.stringify(productForm.value)
    }
    const { error } = await useFetch(`${api}/products/` + id, requestOptions)
    console.log(base64.value)
    if (!error.value)
        navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/details/')
    else {
        message.value = error.value
        console.log(message.value)
    }
}

function cancel() {
    navigateTo('/manufacturers/' + route.params.username + '/products/' + id + '/details/')
}
</script>

<style></style>