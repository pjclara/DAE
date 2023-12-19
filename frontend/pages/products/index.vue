<template>
    <v-col align="center">
        <h1> Listagem de Produtos </h1>

        <v-container class="grid">  
            {{ products  }}
            <v-card v-for="product in products" :key="product.id">
                <v-card-title justify="center">
                    {{ product.name }}
                </v-card-title>

                <v-card-text>
                    <div v-if="product.image">
                        <v-img :src="product.image" />
                    </div>
                    <div v-else>
                        <v-img width="200" height="200" src="https://t4.ftcdn.net/jpg/00/89/55/15/360_F_89551596_LdHAZRwz3i4EM4J0NHNHy2hEUYDfXc0j.jpg" />
                    </div>
                </v-card-text>

                <v-card-actions class="justify-center">
                    <v-btn>Add to Card</v-btn>
                </v-card-actions>

            </v-card>
        </v-container>
    </v-col>
</template>

<script setup>
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: products, error, refresh } = await useFetch(`${api}/products`)

    console.log("products: ", products);
</script>

<style scoped>
    .grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 16px;
    }
</style>

