<template>
    <div>
        <Header titulo="Dashboard do consumidor final" />  
        <h1>Edit logisticsOperator</h1>
        <form @submit.prevent="update">
            <div>Username:
                <input v-model="logisticsOperatorForm.username" type="text" placeholder="user name">
            </div>
            <div>Password:
                <input v-model="logisticsOperatorForm.password" type="password" placeholder="password">
            </div>
            <div>Name:
                <input v-model="logisticsOperatorForm.name" type="text" placeholder="name">
            </div>
            <div>E-mail:
                <input v-model="logisticsOperatorForm.email" type="text" placeholder="email">
            </div>
            <div>Course:
                <select v-model="logisticsOperatorForm.courseCode">
                    <option value="">Select a course</option>
                    <option v-for="course in courses" :value="course.code">
                        {{ course.name }}
                    </option>
                </select>
            </div>
            <button type="reset">RESET</button>
            <button type="submit">update</button>
            <nuxt-link class="link" to="/logisticsOperators">Return</nuxt-link>
        </form>
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const { data: logisticsOperator, error: logisticsOperatorErr } = await
useFetch(`${api}/logisticsOperators/${username}`)
const logisticsOperatorForm = reactive(logisticsOperator)
async function update() {
    console.log(logisticsOperatorForm)
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(logisticsOperatorForm.value)
    }
    const { error } = await useFetch(`${api}/logisticsOperators/` + username, requestOptions)
    if (!error.value)
        navigateTo('/logisticsOperators')
    else {
        message.value = error.value
        console.log(message.value)
    }
}
</script>


<style>
h1 {
    margin-bottom: 20px;
    margin-left: 100px;
}

form {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 100px;
    width: 300px;
    height: 300px;
    border: 1 solid black;
}

input {
    width: 100%;
    height: 30px;
    margin-bottom: 10px;
}

button {
    width: 100%;
    height: 30px;
    margin-bottom: 10px;
    background-color: lightskyblue;
    padding: 10px;
}

label {
    margin-bottom: 10px;
}

select {
    width: 100%;
    height: 30px;
    margin-bottom: 10px;
}

.link {
    width: 100%;
    height: 30px;
    margin-bottom: 10px;
    padding: 10px;
}
</style>