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
                <table class="table">
                    <tr>
                        <th>Produto</th>
                        <th>Image</th>
                        <th>Quantity</th>
                    </tr>
                    <tr v-for="item in cartStore.productsInCart()">
                        <td>{{ item.name }}</td>
                        <td><v-img :width="25" aspect-ratio="4/3" cover :src="item.image"></v-img></td>
                        <td>{{ item.count }}</td>
                        <td>

                            <v-btn @click="remove(item)">-</v-btn>
                            <v-btn @click="add(item.id)">+</v-btn>
                            

                        </td>
                    </tr>
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

const cartItems = ref(cartStore.cartItems)


const add = (id) => {
    // change count of product in cart
   cartItems.value.forEach((item) => {
    console.log("item.id: ", id)
        if (item.id === id) {
            item.count++
            console.log("item.count: ", item.count)
        }
    })
}

const remove = (item) => {
    if (item.count > 1) {
        item.count--
    }
}

onMounted(() => {
    // count products in cart
    console.log("cartItems: ", cartStore.productsInCart())
})

const createOrder = () => {
    console.log("user.username: ", user.value.username)
    cartStore.createOrderCart(user.value.username);
}
</script>