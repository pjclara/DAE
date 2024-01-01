<template>
    <div>
        <h1>Edit package</h1>
        <form @submit.prevent="update">
            <div>
                <v-text-field label="Type" v-model="packageForm.packagingType" type="text" placeholder="type"></v-text-field>
            </div>
            <div>
                <v-text-field label="Material" v-model="packageForm.packagingMaterial" type="text" placeholder="material"></v-text-field>
            </div>
            <v-btn type="reset">RESET</v-btn>
            <v-btn type="submit">update</v-btn>
            <nuxt-link class="link" to="/packages">Return</nuxt-link>
        </form>       
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const id = route.params.id
const { data: packageForm, error: manufacturerErr } = await
useFetch(`${api}/packages/${id}`)

async function update() {
    const requestOptions = {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(packageForm.value)
    }
    const { error } = await useFetch(`${api}/packages/` + id, requestOptions)
    if (!error.value)
        navigateTo('/packages')
    else {
        message.value = error.value
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