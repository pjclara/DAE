<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Criar um Fabricante</h1>

            <v-form @submit.prevent="createManufacturer">
                <v-text-field v-model.trim="manufacturerForm.username" label="Username"
                    :rules="isUsernameValid ? [] : [formFeedback.username]" :counter="15" required></v-text-field>

                <v-text-field v-model.trim="manufacturerForm.password" label="Password" type="password"
                    :rules="isPasswordValid ? [] : [formFeedback.password]" :counter="15" required></v-text-field>

                <v-text-field v-model.trim="manufacturerForm.name" label="Name"
                    :rules="isNameValid ? [] : [formFeedback.name]" :counter="30" required></v-text-field>

                <v-text-field v-model.trim="manufacturerForm.email" label="E-mail" type="email"
                    :rules="isEmailValid ? [] : [formFeedback.email]" required></v-text-field>

                <v-btn block rounded="xl" size="x-large" class="mt-2" @click="createManufacturer">Criar Fabricante</v-btn>
            </v-form>
            {{ message }}
        </v-col>
    </v-col>
</template>

<script setup>
const manufacturerForm = reactive({
    username: null,
    password: null,
    email: null,
    name: null,
    role: 'manufacturer'
})
const formFeedback = reactive({
    username: '',
    password: '',
    email: '',
    name: '',
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL


const isUsernameValid = computed(() => {
    if (!manufacturerForm.username) {
        formFeedback.username = 'username is required'
        return false
    }
    if (manufacturerForm.username.length < 3) {
        formFeedback.username = 'username must be at least 3 characters'
        return false
    }
    if (manufacturerForm.username.length > 15) {
        formFeedback.username = 'username must be at most 15 characters'
        return false
    }
    return true
})

const isPasswordValid = computed(() => {
    if (!manufacturerForm.password) {
        formFeedback.password = 'password is required'
        return false
    }
    if (manufacturerForm.password.length < 3) {
        formFeedback.password = 'password must be at least 3 characters'
        return false
    }
    if (manufacturerForm.password.length > 15) {
        formFeedback.password = 'password must be at most 15 characters'
        return false
    }
    return true
})

const isNameValid = computed(() => {
    if (!manufacturerForm.name) {
        formFeedback.name = 'name is required'
        return false
    }
    if (manufacturerForm.name.length < 3) {
        formFeedback.name = 'name must be at least 3 characters'
        return false
    }
    if (manufacturerForm.name.length > 30) {
        formFeedback.name = 'name must be at most 30 characters'
        return false
    }
    return true
})

const isEmailValid = computed(() => {
    if (!manufacturerForm.email) {
        formFeedback.email = 'email is required'
        return false
    }
    const emailRegex = /\S+@\S+\.\S+/
    if (!emailRegex.test(manufacturerForm.email)) {
        formFeedback.email = 'email must be a valid email'
        return false
    }
    return true
})

const isFormValid = computed(() => {
    return isUsernameValid.value &&
        isPasswordValid.value &&
        isNameValid.value &&
        isEmailValid.value
})

async function createManufacturer() {
    if(!isFormValid.value) {
        alert('Por favor preencha os campos corretamente')
        return;
    }
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(manufacturerForm)
    }
    const { error } = await useFetch(`${api}/manufacturers`, requestOptions)
    if (!error.value) {
        navigateTo('/auth/login')
    }
    message.value = error.value
}
</script>