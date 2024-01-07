<script setup>
import { onMounted } from "vue";
import {useAuthStore} from "~/store/auth-store.js"
const authStore = useAuthStore()
const {token, user} = storeToRefs(authStore)

function logout() {
    authStore.logout()
    router.push('/')
}
onMounted(() => {
    // check if token exists in local storage
    const tokenLocal = localStorage.getItem('token')
    const userLocal = localStorage.getItem('user')
    if (userLocal) {
        user.value = JSON.parse(userLocal)
    }
    if (tokenLocal) {
        token.value = tokenLocal
    }
    if (!token.value) {
        navigateTo('/auth/login')
    }
})
</script>
<template>
    <div>
        <slot />
    </div>
</template>