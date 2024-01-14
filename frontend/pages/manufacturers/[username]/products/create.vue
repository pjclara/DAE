<template>
        <div>
            <v-col align="center">
                <v-col cols="6">
                    <h1>Criar Produto</h1>
                    <form @submit.prevent="create">
                        <div>
                            <v-text-field v-model="productForm.name" placeholder="Nome" required />
                        </div>
                        <div>
                            <v-text-field v-model="productForm.stock" label="Stock" required />
                        </div>
                        <!-- <div>
                            <v-select v-model="productForm.packageId" :items="packagesList" item-title="packagingMaterial" item-value="id" label="Package" />
                        </div> -->
                        <div>
                            <v-file-input @change="createImage" label="Imagem" />
                        </div>
                        <div>
                            <v-btn block rounded @click="create" class="mb-2">Criar Produto</v-btn>
                        </div>
                        <div>
                            <v-btn block rounded @click="cancel">Cancelar</v-btn>
                        </div>

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
    image: null
})

const base64 = ref('')
function createImage(e) {
    const file = e.target.files[0]
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => {
        base64.value = reader.result
        productForm.image = base64.value
    }
}

//const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packages/packagingType/PRIMARY`)

async function create() {
    console.log("JSON.stringify(productForm): ",JSON.stringify(productForm))
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

function cancel() {
    navigateTo('/manufacturers/' + route.params.username + '/products')
}
</script>