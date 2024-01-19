<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Create a endConsumer</h1>

            <v-form @submit.prevent="create">
                <v-text-field v-model.trim="endConsumerForm.username" label="Username"
                    :rules="isUsernameValid ? [] : [formFeedback.username]" :counter="15" required></v-text-field>

                <v-text-field v-model.trim="endConsumerForm.password" label="Password" type="password"
                    :rules="isPasswordValid ? [] : [formFeedback.password]" :counter="15" required></v-text-field>

                <v-text-field v-model.trim="endConsumerForm.name" label="Name"
                    :rules="isNameValid ? [] : [formFeedback.name]" :counter="30" required></v-text-field>

                <v-text-field v-model.trim="endConsumerForm.email" label="E-mail" type="email"
                    :rules="isEmailValid ? [] : [formFeedback.email]" required></v-text-field>

                <v-btn block rounded="xl" size="x-large" class="mt-2" @click="create">Registar</v-btn>
            </v-form>
            {{ message }}
        </v-col>
    </v-col>
</template>

<script setup>
const endConsumerForm = reactive({
    username: null,
    password: null,
    email: null,
    name: null
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
    if (!endConsumerForm.username) {
        formFeedback.username = 'username is required'
        return false
    }
    if (endConsumerForm.username.length < 3) {
        formFeedback.username = 'username must be at least 3 characters'
        return false
    }
    if (endConsumerForm.username.length > 15) {
        formFeedback.username = 'username must be at most 15 characters'
        return false
    }
    return true
})

const isPasswordValid = computed(() => {
    if (!endConsumerForm.password) {
        formFeedback.password = 'password is required'
        return false
    }
    if (endConsumerForm.password.length < 3) {
        formFeedback.password = 'password must be at least 3 characters'
        return false
    }
    if (endConsumerForm.password.length > 15) {
        formFeedback.password = 'password must be at most 15 characters'
        return false
    }
    return true
})

const isNameValid = computed(() => {
    if (!endConsumerForm.name) {
        formFeedback.name = 'name is required'
        return false
    }
    if (endConsumerForm.name.length < 3) {
        formFeedback.name = 'name must be at least 3 characters'
        return false
    }
    if (endConsumerForm.name.length > 30) {
        formFeedback.name = 'name must be at most 30 characters'
        return false
    }
    return true
})

const isEmailValid = computed(() => {
    if (!endConsumerForm.email) {
        formFeedback.email = 'email is required'
        return false
    }
    const emailRegex = /\S+@\S+\.\S+/
    if (!emailRegex.test(endConsumerForm.email)) {
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

async function create() {
    if(isFormValid.value){
        const requestOptions = {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(endConsumerForm)
        }
        const { error } = await useFetch(`${api}/endConsumers`, requestOptions)
        if (!error.value) {
            navigateTo('/auth/login')
        }
        message.value = error.value
    }
}
</script>