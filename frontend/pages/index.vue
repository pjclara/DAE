<template>
    <div class="col-6">
        <div class="d-flex flex-column items-center align-center">
            <h2>Welcome back {{ user?.username }}</h2>
        </div>
    </div>
</template>

<script setup>
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)
</script>