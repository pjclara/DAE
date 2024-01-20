<template>
    <v-card>
        <v-toolbar color="primary" dark>
            <v-btn icon @click="goBack">
                <v-icon>mdi-arrow-left</v-icon>
            </v-btn>
            <v-toolbar-title>Voltar</v-toolbar-title>
        </v-toolbar>
    </v-card>
    <v-col align="center">
        <v-col cols="6">
        <h1>Editar embalagem</h1>
            <form @submit.prevent="update">
                <div>
                    <v-select v-model="packageForm.packagingType" :items="packagingTypes" item-value="value" item-text="title"
                        label="Tipo de Embalagem"
                        :rules="isPackagingTypeValid ? [] : [formFeedback.packagingType]">
                    </v-select>
                </div>
                <div>
                    <v-text-field label="Material" v-model="packageForm.packagingMaterial" type="text"
                        placeholder="material"
                        :rules="isPackagingMaterialValid ? [] : [formFeedback.packagingMaterial]"></v-text-field>
                </div>
                <v-btn type="reset">RESET</v-btn>
                <v-btn type="submit">Atualizar</v-btn>
            </form>
        </v-col>
    </v-col>
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

const isPackagingTypeValid = computed(() => {
    if (!packageForm.value.packagingType) {
        formFeedback.packagingType = 'Tipo de Embalagem é obrigatório';
        return false;
    }
    return true;
});

const isPackagingMaterialValid = computed(() => {
    if (!packageForm.value.packagingMaterial) {
        formFeedback.packagingMaterial = 'Material da Embalagem é obrigatório';
        return false;
    }
    return true;
});

const isFormValid = computed(() => {
    return isPackagingTypeValid.value && isPackagingMaterialValid.value;
});

const formFeedback = reactive({
    packagingType: '',
    packagingMaterial: '',
});

async function update() {
    if(!isFormValid.value) {
        alert('Por favor preencha os campos corretamente')
        return;
    }
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
