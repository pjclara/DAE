<template>
    <div>
        <v-col align="center">
            <v-col cols="6">
                <h1>Criar Produto</h1>
                <form @submit.prevent="create">
                    <div>
                        <v-text-field v-model="productForm.name" placeholder="Nome" required
                            :rules="isNameValid ? [] : [formFeedback.name]" />
                    </div>
                    <div>
                        <v-text-field v-model="productForm.stock" label="Stock" required
                        :rules="isStockValid ? [] : [formFeedback.stock]" />
                    </div>
                    <div>
                        <v-select v-model="productForm.packageProductId" :items="packagesList"
                            item-title="packagingMaterial" item-value="id" label="Package" />
                    </div>
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
    image: null,
    packageProductId: null
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

const { data: packagesList, packageError: productsErr } = await useFetch(`${api}/packageProducts/type/PRIMARY`)

const formFeedback = reactive({
    name: '',
    stock: '',
})

const isNameValid = computed(() => {
    if (!productForm.name) {
        formFeedback.name = 'name is required'
        return false
    }
    if (productForm.name.length < 3) {
        formFeedback.name = 'name must be at least 3 characters'
        return false
    }
    if (productForm.name.length > 15) {
        formFeedback.name = 'name must be at most 15 characters'
        return false
    }
    return true
})

const isStockValid = computed(() => {
    if (!productForm.stock) {
        formFeedback.stock = 'stock is required'
        return false
    }
    if (productForm.stock < 1) {
        formFeedback.stock = 'stock must be at least 1'
        return false
    }
    return true
})

const isFormValid = computed(() => {
    return isNameValid.value && isStockValid.value
})

async function create() {
    if (!isFormValid.value) {
        message.value = 'Please fill the form correctly'
        alert(message.value)
        return
    }
    if (productForm.image == null) {
        productForm.image = "data:image/jpeg;base64,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"
    }
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