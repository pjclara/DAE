<template>
    <div>
        add endConsumer
        <nuxt-link class="link" :to="`/endConsumers/create`">add endConsumer</nuxt-link>


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
                <tr v-for="endConsumer in endConsumers" :key="endConsumer.id">
                    <td>{{ endConsumer.name }}</td>
                    <td>{{ endConsumer.email }}</td>
                    <td>
                        <nuxt-link class="link" :to="`/endConsumers/${endConsumer.username}/details`">Details</nuxt-link>
                    </td>
                    <td>
                        <nuxt-link class="link" :to="`/endConsumers/${endConsumer.username}/edit`">Edit</nuxt-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: endConsumers, error, refresh } = await useFetch(`${api}/endConsumers`)
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

