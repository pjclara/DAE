<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Criar Embalagem</h1>
            <form @submit.prevent="create">
                <div>
                    <v-select
                        v-model="packageForm.packagingType"
                        :items="packagingTypes"
                        item-value="value"
                        item-text="title"
                        label="Tipo de Embalagem"
                        :rules="isPackagingTypeValid ? [] : [formFeedback.packagingType]">
                    </v-select>
                </div>
                <div>
                    <v-text-field v-model="packageForm.packagingMaterial" label="Material da Embalagem"
                    :rules="isPackagingMaterialValid ? [] : [formFeedback.packagingMaterial]" />
                </div>
                <v-btn block rounded="xl" size="x-large" class="mb-2" @click="create">Criar</v-btn>
                <v-btn block rounded="xl" size="x-large" @click="back()">Cancelar</v-btn>

            </form>
        </v-col>
    </v-col>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const config = useRuntimeConfig()
const api = config.public.API_URL
const { user } = storeToRefs(authStore)
const route = useRoute()
const username = route.params.username

const packagingTypes = ref([])

const packageForm = ref({
  packagingType: null,
  packagingMaterial: null,
});

const isPackagingTypeValid = computed(() => {
    //console.log("packageForm.value.packagingType:", packageForm.value.packagingType)
    if (packageForm.value.packagingType === null) {
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

onMounted(() => {
    if(user.value.role === 'Manufacturer'){
        packagingTypes.value = [
            { title: 'Primário', value: 0},
            { title: 'Secundário', value: 1 },
            { title: 'Terciário', value: 2 },
        ];
    }
    if(user.value.role === 'LogisticsOperator'){
        packagingTypes.value = [
            { title: 'Transporte', value: 'TRANSPORT' },
        ];
    }
});

async function create() {
    if(!isFormValid.value) {
        alert('Por favor preencha os campos corretamente')
        return;
    }
    // const package = {...packageForm, packageId: 0, timestamp: Date.now()}
    //console.log("JSON.stringify(packageForm.value) : ", JSON.stringify(packageForm.value))
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(packageForm.value)
    }
    const { error } = await useFetch(`${api}/packageProducts`, requestOptions)
    if (!error.value) navigateTo("/manufacturers/"+ username + "/packageProducts")

    //console.log("error: ", error)
    //console.log("error.value: ", error.value)
}

const back = () => {
    navigateTo(`/manufacturers/${username}/packageProducts/`)
}
</script>