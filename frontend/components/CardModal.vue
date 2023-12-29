<template>
    <v-dialog
        v-model="cartStore.modalOpen"
        max-width="500"
        @click:outside="cartStore.closeDialog"
    >
    <v-card>
        <v-card-title class="d-flex justify-center">
            Carrinho
        </v-card-title>
        <v-card-text v-if="cartStore.cartItems.length === 0">
            abc
        </v-card-text>
        <v-card-text v-else>
            <v-list>
                <v-list-item v-for="item in cartStore.cartItems" class="bg-grey-lighten-1 rounded-xl my-2">
                    <v-row >
                        <v-col class="justify-center align-center">
                            {{ item.name }}
                        </v-col>
                        <v-col>
                            <v-img
                                :width="33"
                                aspect-ratio="4/3"
                                cover
                                :src="item.image"
                            ></v-img>
                        </v-col>
                        <v-col>
                            {{ item.section4 }}
                        </v-col>
                    </v-row>
                </v-list-item>
            </v-list>
        </v-card-text>
        <v-card-actions class="d-flex justify-space-around" >
        <v-btn
            color="primary"
            variant="text"
            @click="cartStore.closeDialog"
        >
            Fechar
        </v-btn>
        <v-btn
            color="primary"
            variant="text"
            @click="createOrder()"
        >
            Confirmar
        </v-btn>
        </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {useCartStore} from "@/store/cart-store"
import {useAuthStore} from "~/store/auth-store.js"
const authStore = useAuthStore()
const {user} = storeToRefs(authStore)

const cartStore = useCartStore()

const createOrder = () => {
    cartStore.createOrderCart(user.username);
}
</script>