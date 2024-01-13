import {defineStore} from "pinia";

export const useAuthStore = defineStore("authStore", () => {
    const token = ref(null)
    const userRole = ref(null)
    const username = ref(null)
    const user = ref(null)


    function logout() {
        token.value = null
        userRole.value = null
        username.value = null
        user.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('user')
    }

    function getUser() {
        return user.value
    }
    
    return { token, user, userRole, username, logout }
})