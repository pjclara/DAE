<template>
    <div>
        <h1>Create a manufacturer</h1>
        <form @submit.prevent="create">
        <div>Username:
            <input v-model.trim="manufacturerForm.username" type="text">
            <span v-if="manufacturerForm.username !== null && !isUsernameValid" class="error">
                ERROR: {{ formFeedback.username }}</span>
        </div>
        <div>Password:
            <input v-model.trim="manufacturerForm.password" type="password">
            <span v-if="manufacturerForm.username !== null && !isPasswordValid" class="error">
                ERROR: {{ formFeedback.password }}</span>
        </div>
        <div>Name:
            <input v-model.trim="manufacturerForm.name" type="text">
            <span v-if="manufacturerForm.username !== null && !isNameValid" class="error">
                ERROR: {{ formFeedback.name }}</span>
        </div>
        <div>E-mail:
            <input v-model.trim="manufacturerForm.email" type="email">
            <span v-if="manufacturerForm.username !== null && !isEmailValid" class="error">
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
const manufacturerForm = reactive({
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
    if (manufacturerForm.name.length > 15) {
        formFeedback.name = 'name must be at most 15 characters'
        return false
    }
    return true
})

const isEmailValid = computed(() => {
    if (!manufacturerForm.email) {
        formFeedback.email = 'email is required'
        return false
    }
    if (manufacturerForm.email.length < 3) {
        formFeedback.email = 'email must be at least 3 characters'
        return false
    }
    if (manufacturerForm.email.length > 15) {
        formFeedback.email = 'email must be at most 15 characters'
        return false
    }
    // is a email format
    const emailRegex = /\S+@\S+\.\S+/
    if (!emailRegex.test(manufacturerForm.email)) {
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
        body: JSON.stringify(manufacturerForm)
    }
    const { error } = await useFetch(`${api}/manufacturers`, requestOptions)
    if (!error.value) navigateTo('/manufacturers')
    message.value = error.value
}
</script>