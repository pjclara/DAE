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