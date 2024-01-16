<template>
    <div>
        add logisticsOperator
        <nuxt-link class="link" :to="`/logisticsoperator/create`">add logisticsOperator</nuxt-link>


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
                <tr v-for="logisticsOperator in logisticsOperators" :key="logisticsOperator.id">
                    <td>{{ logisticsOperator.name }}</td>
                    <td>{{ logisticsOperator.email }}</td>
                    <td>
                        <nuxt-link class="link" :to="`/logisticsoperator/${logisticsOperator.username}/details`">Details</nuxt-link>
                    </td>
                    <td>
                        <nuxt-link class="link" :to="`/logisticsoperator/${logisticsOperator.username}/edit`">Edit</nuxt-link>
                    </td>
                </tr>
            </tbody>
        </table>

    </div>
    //

</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: logisticsOperators, error, refresh } = await useFetch(`${api}/logisticsOperators`)
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

