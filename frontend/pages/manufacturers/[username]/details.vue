<template>
    <div>
        <nuxt-link :to="`/manufacturers/${username}/products`">List of my Products</nuxt-link>
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username

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