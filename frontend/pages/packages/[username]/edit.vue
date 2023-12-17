<template>
    <div>
        <Header titulo="Dashboard do consumidor final" />  
        <h1>Edit manufacturer</h1>
        <form @submit.prevent="update">
            <div>Username:
                <input v-model="manufacturerForm.username" type="text" placeholder="user name">
            </div>
            <div>Password:
                <input v-model="manufacturerForm.password" type="password" placeholder="password">
            </div>
            <div>Name:
                <input v-model="manufacturerForm.name" type="text" placeholder="name">
            </div>
            <div>E-mail:
                <input v-model="manufacturerForm.email" type="text" placeholder="email">
            </div>
            <div>Course:
                <select v-model="manufacturerForm.courseCode">
                    <option value="">Select a course</option>
                    <option v-for="course in courses" :value="course.code">
                        {{ course.name }}
                    </option>
                </select>
            </div>
            <button type="reset">RESET</button>
            <button type="submit">update</button>
            <nuxt-link class="link" to="/manufacturers">Return</nuxt-link>
        </form>
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const { data: manufacturer, error: manufacturerErr } = await
useFetch(`${api}/manufacturers/${username}`)
const manufacturerForm = reactive(manufacturer)
async function update() {
    console.log(manufacturerForm)
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(manufacturerForm.value)
    }
    const { error } = await useFetch(`${api}/manufacturers/` + username, requestOptions)
    if (!error.value)
        navigateTo('/manufacturers')
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