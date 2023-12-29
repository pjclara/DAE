<template>
    <v-col align="center">
        <v-row class="ma-2" justify="space-between">
            <h1> Listagem de Produtos </h1>
            <v-btn @click="cartStore.openDialog" class="mb-4">Carrinho</v-btn>
        </v-row>

        <v-container class="grid">
            <v-card v-for="product in products" :key="product.id">
                <v-card-title justify="center">
                    {{ product.name }}
                </v-card-title>

                <v-card-text>
                    <div v-if="product.image">
                        <v-img width="200" height="200" :src="product.image" />
                    </div>
                    <div v-else>
                        <v-img width="200" height="200" src="https://t4.ftcdn.net/jpg/00/89/55/15/360_F_89551596_LdHAZRwz3i4EM4J0NHNHy2hEUYDfXc0j.jpg" />
                    </div>
                </v-card-text>

                <v-card-actions class="justify-center">
                    <v-btn @click="addToCart(product)">Add to Card</v-btn>
                </v-card-actions>
            </v-card>
            <cart-modal v-if="cartStore.modalOpen"></cart-modal>
        </v-container>
    </v-col>
</template>

<script setup>
    import {useAuthStore} from "~/store/auth-store.js"
    import CartModal from '@/components/CardModal.vue'
    import {useCartStore} from "@/store/cart-store"

    const cartStore = useCartStore()

    const authStore = useAuthStore()
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: products, error, refresh } = await useFetch(`${api}/products`)

    const modalOpen = ref(cartStore.cartModal);

    const addToCart = (product) => {
        cartStore.add(product);
    }


    console.log("products: ", products);
</script>

<style scoped>
    .grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 16px;
    }
</style>

