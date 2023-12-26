<template>
    <div>
        <h1>Edit endConsumer</h1>
        <form @submit.prevent="update">
            <div>Username:
                <input v-model="endConsumerForm.username" type="text" placeholder="user name">
            </div>
            <div>Password:
                <input v-model="endConsumerForm.password" type="password" placeholder="password">
            </div>
            <div>Name:
                <input v-model="endConsumerForm.name" type="text" placeholder="name">
            </div>
            <div>E-mail:
                <input v-model="endConsumerForm.email" type="text" placeholder="email">
            </div>           
            <button type="reset">RESET</button>
            <button type="submit">update</button>
            <nuxt-link class="link" to="/endConsumers">Return</nuxt-link>
        </form>
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const { data: endConsumer, error: endConsumerErr } = await
useFetch(`${api}/endConsumers/${username}`)
const endConsumerForm = reactive(endConsumer)
async function update() {
    console.log(endConsumerForm)
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(endConsumerForm.value)
    }
    const { error } = await useFetch(`${api}/endConsumers/` + username, requestOptions)
    if (!error.value)
        navigateTo('/endConsumers')
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