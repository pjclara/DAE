<template>
    <v-col align="center">
        <v-col cols="6">
            <h1>Login</h1>
            <div>
                <v-text-field v-model="loginFormData.username" label="Username"/>
            </div>
            <div>
                <v-text-field v-model="loginFormData.password" label="Password"/>
            </div>
            <v-btn block rounded="xl" size="x-large" @click="login">LOGIN</v-btn>
            <div v-if="token">
                <div>Token: {{ token }}</div>
            </div>
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
import { useAuthStore } from '@/store/auth-store';

const config = useRuntimeConfig()
const api = config.public.API_URL

const loginFormData = ref({
    username: "",
    password: ""
})

const authStore = useAuthStore();
const { token, user } = authStore;

const messages = ref([])
async function login() {
    const { data, error } = await useFetch(`${api}/auth/login`, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: loginFormData.value
    })
    if (error.value) {
        messages.value.push({ tokenError: error.value.message })
        return
    }

    token = data.value

    const { data: userData, error: userError } = await useFetch(`${api}/auth/user`, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        }
    })
    if (userError.value) {
        messages.value.push({ userDataError: userError.value.message })
        return
    }

    user = userData.value
}
</script>