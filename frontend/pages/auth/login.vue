<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Login</h1>
            <div>
                <v-text-field v-model="loginFormData.username" label="Username" placeholder="manufacturer1"/>
            </div>
            <div>
                <v-text-field type="password" v-model="loginFormData.password" label="Password" placeholder="manufacturer1"/>
            </div>
            <v-btn block rounded="xl" size="x-large" @click="login">LOGIN</v-btn>
            <div v-if="messages.length > 0">
                <h2>Messages</h2>
                <div v-for="message in messages">
                    <pre>{{ message }}</pre>
                </div>
            </div>
        </v-col>
    </v-col>
</template>
<script setup>
import {useAuthStore} from "~/store/auth-store.js"
const authStore = useAuthStore()
const {token, user, username, userRole} = storeToRefs(authStore)

const config = useRuntimeConfig()
const api = config.public.API_URL
const loginFormData = reactive({
    username: null,
    password: null
})
const apiFormData = reactive({
    path: "manufacturers"
})

const messages = ref([])
async function login() {
    const { data, error } = await useFetch(`${api}/auth/login`, {
        method: "post",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(loginFormData)
    })
    if (error.value) {
        messages.value.push({ error: error.value.message })
    }
    if (data.value) {
        console.log('token :', data.value)
        token.value = data.value;
        await getUser();
        //navigateTo('/')
    }
}
function reset() {
    token.value = null
    messages.value = []
}

async function getUser() {
    const { data, error } = await useFetch(`${api}/auth/user`, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        }
    })
    if (error.value) {
        messages.value.push({ error: error.value.message })
    }
    if (data.value) {
        messages.value.push({ payload: data.value })
        user.value = data.value;
    }
}


async function sendRequest() {
    const { data, error } = await useFetch(`${api}/${apiFormData.path}`, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        }
    })
    if (error.value) {
        messages.value.push({ error: error.value.message })
    }
    if (data.value) {
        messages.value.push({ payload: data.value })
    }
}
</script>
