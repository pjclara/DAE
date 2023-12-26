<template>
    <div>
        <h1>Create a manlogisticsOperatorufacturer</h1>
        <form @submit.prevent="create">
        <div>Username:
            <input v-model.trim="logisticsOperatorForm.username" type="text">
            <span v-if="logisticsOperatorForm.username !== null && !isUsernameValid" class="error">
                ERROR: {{ formFeedback.username }}</span>
        </div>
        <div>Password:
            <input v-model.trim="logisticsOperatorForm.password" type="password">
            <span v-if="logisticsOperatorForm.username !== null && !isPasswordValid" class="error">
                ERROR: {{ formFeedback.password }}</span>
        </div>
        <div>Name:
            <input v-model.trim="logisticsOperatorForm.name" type="text">
            <span v-if="logisticsOperatorForm.username !== null && !isNameValid" class="error">
                ERROR: {{ formFeedback.name }}</span>
        </div>
        <div>E-mail:
            <input v-model.trim="logisticsOperatorForm.email" type="email">
            <span v-if="logisticsOperatorForm.username !== null && !isEmailValid" class="error">
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
const logisticsOperatorForm = reactive({
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
    if (!logisticsOperatorForm.username) {
        formFeedback.username = 'username is required'
        return false
    }
    if (logisticsOperatorForm.username.length < 3) {
        formFeedback.username = 'username must be at least 3 characters'
        return false
    }
    if (logisticsOperatorForm.username.length > 15) {
        formFeedback.username = 'username must be at most 15 characters'
        return false
    }
    return true
})

const isPasswordValid = computed(() => {
    if (!logisticsOperatorForm.password) {
        formFeedback.password = 'password is required'
        return false
    }
    if (logisticsOperatorForm.password.length < 3) {
        formFeedback.password = 'password must be at least 3 characters'
        return false
    }
    if (logisticsOperatorForm.password.length > 15) {
        formFeedback.password = 'password must be at most 15 characters'
        return false
    }
    return true
})

const isNameValid = computed(() => {
    if (!logisticsOperatorForm.name) {
        formFeedback.name = 'name is required'
        return false
    }
    if (logisticsOperatorForm.name.length < 3) {
        formFeedback.name = 'name must be at least 3 characters'
        return false
    }
    if (logisticsOperatorForm.name.length > 15) {
        formFeedback.name = 'name must be at most 15 characters'
        return false
    }
    return true
})

const isEmailValid = computed(() => {
    if (!logisticsOperatorForm.email) {
        formFeedback.email = 'email is required'
        return false
    }
    if (logisticsOperatorForm.email.length < 3) {
        formFeedback.email = 'email must be at least 3 characters'
        return false
    }
    if (logisticsOperatorForm.email.length > 15) {
        formFeedback.email = 'email must be at most 15 characters'
        return false
    }
    // is a email format
    const emailRegex = /\S+@\S+\.\S+/
    if (!emailRegex.test(logisticsOperatorForm.email)) {
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
        body: JSON.stringify(logisticsOperatorForm)
    }
    const { error } = await useFetch(`${api}/logisticsOperators`, requestOptions)
    if (!error.value) navigateTo('/logisticsOperator')
    message.value = error.value
}
</script>