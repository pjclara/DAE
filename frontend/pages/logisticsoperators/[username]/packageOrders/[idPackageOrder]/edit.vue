<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Editar Embalagem</h1>
            <form @submit.prevent="edit">
                <div>
                    <v-select v-model="packageForm.packagingType" :items="packagingTypes" item-value="value"
                        item-text="title" label="Tipo de Embalagem"
                        :rules="isPackagingTypeValid ? [] : [formFeedback.packagingType]">
                    </v-select>
                </div>
                <div>
                    <v-text-field v-model="packageForm.packagingMaterial" 
                    label="Material da Embalagem"
                    :rules="isPackagingMaterialValid ? [] : [formFeedback.packagingMaterial]" />
                </div>
                <v-btn block rounded="xl" size="x-large" class="mb-2" @click="update">Update</v-btn>
                <v-btn block rounded="xl" size="x-large" @click="back">Cancelar</v-btn>

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
const idPackageOrder = route.params.idPackageOrder

const packagingTypes = ref([])
const packageForm = ref({
    packagingType: null,
    packagingMaterial: null,
});
const { data: packageOrder, error } = await useFetch(`${api}/packageOrders/${idPackageOrder}`)



onMounted(() => {
    packagingTypes.value = [
        { title: 'Secundário', value: 1 },
        { title: 'Terciário', value: 2 },
        { title: 'Transporte', value: 3 },

    ];
    packageForm.value.packagingType = packageOrder.value.packagingType
    packageForm.value.packagingMaterial = packageOrder.value.packagingMaterial
});

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
    console.log("JSON.stringify(packageForm.value) : ", JSON.stringify(packageForm.value))
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(packageForm.value)
    }
    const { error } = await useFetch(`${api}/packageOrders/${idPackageOrder}`, requestOptions)
    if (!error.value) navigateTo("/logisticsoperators/" + username + "/packageOrders")

    console.log("error: ", error)
    console.log("error.value: ", error.value)
}

const back = () => navigateTo(`/logisticsOperators/${username}/packageOrders`)

</script>