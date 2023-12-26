<template>
    <div>
        <nuxt-link :to="`/manufacturers/${username}/details`">Back to manufacturer details</nuxt-link>
         | 
        <nuxt-link :to="`/manufacturers/${username}/products/create`">Add a product</nuxt-link>

        <v-data-table
          :headers="headers"
          :items="products"
          :items-per-page="5"
          class="elevation-1"
        />
    </div>
</template>
<script setup>
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const { data: products, error: productsErr } = await
useFetch(`${api}/manufacturers/${username}/products`)

</script>