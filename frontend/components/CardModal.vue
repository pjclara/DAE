<template>
    <v-dialog v-model="cartStore.modalOpen" max-width="500" @click:outside="cartStore.closeDialog">
        <v-card>
            <v-card-title class="d-flex justify-center">
                Carrinho
            </v-card-title>
            <v-card-text v-if="cartStore.cartItems.length === 0" class="text-center">
                <h2>Sem produtos no carrinho</h2>
            </v-card-text>
            <v-card-text v-else>
                <table style="width: 100%; border: solid;">
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Image</th>
                            <th>Quantity</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody style="text-align: center;">
                        <tr v-for="item in cartStore.productsInCart()">
                            <td>{{ item.name }}</td>
                            <td><v-img :width="25" aspect-ratio="4/3" cover :src="item.image"></v-img></td>
                            <td>{{ item.count }}</td>
                            <td>
                                <v-btn @click="cartStore.decrement(item.id)">-</v-btn>
                                <v-btn @click="cartStore.increment(item.id)">+</v-btn>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </v-card-text>
            <v-card-actions class="d-flex justify-space-around">
                <v-btn color="primary" variant="text" @click="cartStore.closeDialog">
                    Fechar
                </v-btn>
                <v-btn color="primary" variant="text" @click="createOrder()">
                    Confirmar
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import { useCartStore } from "@/store/cart-store"
import { useAuthStore } from "~/store/auth-store.js"
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

const cartStore = useCartStore()

const cartItems = ref(cartStore.productsInCart())


const createOrder = () => {
    cartStore.createOrderCart(user.value.username);
}
</script>