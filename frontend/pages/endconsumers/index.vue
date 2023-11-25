<!-- pages/DashboardFabricante.vue -->

<template>
    <div>
        <Header titulo="Dashboard do consumidor final" />    
        add endconsumer
        <nuxt-link class="link" :to="`/endconsumers/create`">add endconsumer</nuxt-link>


        <table>
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Details</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="endconsumer in endconsumers" :key="endconsumer.id">
                    <td>{{ endconsumer.name }}</td>
                    <td>{{ endconsumer.email }}</td>
                    <td>
                        <nuxt-link class="link" :to="`/endconsumers/${endconsumer.username}/details`">Details</nuxt-link>
                    </td>
                    <td>
                        <nuxt-link class="link" :to="`/endconsumers/${endconsumer.username}/edit`">Edit</nuxt-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: endconsumers, error, refresh } = await useFetch(`${api}/endconsumers`)
</script>

<style>
table {
    border-collapse: collapse;
    width: 50%;
    margin: 10px;
}
td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}   

</style>

