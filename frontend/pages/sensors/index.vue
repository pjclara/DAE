<template>
    <div>
        <Header titulo="Dashboard do consumidor final" />    
        add manufacturer
        <nuxt-link class="link" :to="`/manufacturers/create`">add manufacturer</nuxt-link>


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
                <tr v-for="manufacturer in manufacturers" :key="manufacturer.id">
                    <td>{{ manufacturer.name }}</td>
                    <td>{{ manufacturer.email }}</td>
                    <td>
                        <nuxt-link class="link" :to="`/manufacturers/${manufacturer.username}/details`">Details</nuxt-link>
                    </td>
                    <td>
                        <nuxt-link class="link" :to="`/manufacturers/${manufacturer.username}/edit`">Edit</nuxt-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: manufacturers, error, refresh } = await useFetch(`${api}/manufacturers`)
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

