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
            <v-btn block class="mb-2" rounded="xl" size="x-large" @click="login">LOGIN</v-btn>
            <v-btn block rounded="xl" size="x-large"><nuxt-link to="/endConsumers/create">REGISTAR</nuxt-link></v-btn>
            <div v-if="messages.length > 0">
                <h2>Something went wrong!</h2>
            </div>
        </v-col>
        <v-col>
            administrator1||manufacturer1||endConsumer2||logisticsOperator1
        </v-col>
    </v-col>
</template>
<script setup>
import {useAuthStore} from "~/store/auth-store.js"
const authStore = useAuthStore()
const {token, user, username, userRole} = storeToRefs(authStore)
//const router = useRouter()

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
        token.value = data.value;
        // save token to local storage
        localStorage.setItem('token', token.value)
        await getUser();
        if (user.value.role === "manufacturer") {
            apiFormData.path = "manufacturers"
           //navigateTo(`/manufacturers/${user.value.username}`)
        } else if (user.value.role === "logisticsOperator") {
            apiFormData.path = "logisticsOperators"
            //navigateTo(`/logisticsOperators/${user.value.username}`)
        } else if (user.value.role === "endConsumer") {
            apiFormData.path = "endConsumers"
            //navigateTo(`/endConsumers/${user.value.username}`)
        }
    }
}
function reset() {
    token.value = null
    // remove token from local storage
    localStorage.removeItem('token')
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
        user.value = data.value;
        localStorage.setItem('user', JSON.stringify(user.value))
        navigateTo('/')
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
