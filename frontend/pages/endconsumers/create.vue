<template>
    <div>
        <Header titulo="Dashboard do consumidor final" /> 
        <h1>Create a endconsumer</h1>
        <form @submit.prevent="create">
        <div>Username:
            <input v-model.trim="endconsumerForm.username" type="text">
            <span v-if="endconsumerForm.username !== null && !isUsernameValid" class="error">
                ERROR: {{ formFeedback.username }}</span>
        </div>
        <div>Password:
            <input v-model.trim="endconsumerForm.password" type="password">
            <span v-if="endconsumerForm.username !== null && !isPasswordValid" class="error">
                ERROR: {{ formFeedback.password }}</span>
        </div>
        <div>Name:
            <input v-model.trim="endconsumerForm.name" type="text">
            <span v-if="endconsumerForm.username !== null && !isNameValid" class="error">
                ERROR: {{ formFeedback.name }}</span>
        </div>
        <div>E-mail:
            <input v-model.trim="endconsumerForm.email" type="email">
            <span v-if="endconsumerForm.username !== null && !isEmailValid" class="error">
                ERROR: {{ formFeedback.email }}</span>
        </div>       
        <button type="reset">RESET</button>
        <button type="submit" :disabled="!isFormValid">CREATE</button>
        <nuxt-link to="/">Return</nuxt-link>
    </form>
    {{ message }} 
    </div>
</template>

<script setup>
const endconsumerForm = reactive({
    username: null,
    password: null,
    email: null,
    name: null,
    courseCode: null
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
    if (!endconsumerForm.username) {
        formFeedback.username = 'username is required'
        return false
    }
    if (endconsumerForm.username.length < 3) {
        formFeedback.username = 'username must be at least 3 characters'
        return false
    }
    if (endconsumerForm.username.length > 15) {
        formFeedback.username = 'username must be at most 15 characters'
        return false
    }
    return true
})

const isPasswordValid = computed(() => {
    if (!endconsumerForm.password) {
        formFeedback.password = 'password is required'
        return false
    }
    if (endconsumerForm.password.length < 3) {
        formFeedback.password = 'password must be at least 3 characters'
        return false
    }
    if (endconsumerForm.password.length > 15) {
        formFeedback.password = 'password must be at most 15 characters'
        return false
    }
    return true
})

const isNameValid = computed(() => {
    if (!endconsumerForm.name) {
        formFeedback.name = 'name is required'
        return false
    }
    if (endconsumerForm.name.length < 3) {
        formFeedback.name = 'name must be at least 3 characters'
        return false
    }
    if (endconsumerForm.name.length > 15) {
        formFeedback.name = 'name must be at most 15 characters'
        return false
    }
    return true
})

const isEmailValid = computed(() => {
    if (!endconsumerForm.email) {
        formFeedback.email = 'email is required'
        return false
    }
    if (endconsumerForm.email.length < 3) {
        formFeedback.email = 'email must be at least 3 characters'
        return false
    }
    if (endconsumerForm.email.length > 15) {
        formFeedback.email = 'email must be at most 15 characters'
        return false
    }
    // is a email format
    const emailRegex = /\S+@\S+\.\S+/
    if (!emailRegex.test(endconsumerForm.email)) {
        formFeedback.email = 'email must be a valid email'
        return false
    }
    return true
})


// Form validation rules
const isFormValid = computed(() => {
    return isUsernameValid.value &&
        isPasswordValid.value &&
        isNameValid.value &&
        isEmailValid.value 
})

async function create() {
    const requestOptions = {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(endconsumerForm)
    }
    const { error } = await useFetch(`${api}/endconsumers`, requestOptions)
    if (!error.value) navigateTo('/endconsumers')
    message.value = error.value
}
</script>