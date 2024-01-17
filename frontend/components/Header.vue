<template>
  <v-navigation-drawer class="bg-deep-purple" theme="dark" v-model="sidebar" permanent>

    <v-list color="transparent" v-if="user?.role == 'Manufacturer'">
      <v-list-item prepend-icon="mdi-account-box" title="Fabricante"></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/manufacturers/${user?.username}`">Home page</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/manufacturers/${user?.username}/products`">Lista de Produtos</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/manufacturers/${user?.username}/packageProducts`">Lista de Embalagens</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/manufacturers/${user?.username}/sensors`">Lista de Sensores</nuxt-link></v-btn></v-list-item>
    </v-list>
    <v-list color="transparent" v-if="user?.role == 'LogisticsOperator'">
      <v-list-item prepend-icon="mdi-account-box" title="O. Logistica"></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/logisticsoperators/${user?.username}/`">Home page</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/logisticsoperators/${user?.username}/orders`">Lista de Encomendas</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/logisticsoperators/${user?.username}/packageOrders`">Lista de Embalagens</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link :to="`/logisticsoperators/${user?.username}/sensors`">Lista de Sensores</nuxt-link></v-btn></v-list-item>
    </v-list>
    <v-list color="transparent" v-if="user?.role == 'EndConsumer'">
      <v-list-item prepend-icon="mdi-account-box" title="Cliente"></v-list-item>
      <!-- <v-list-item><nuxt-link to="/customerOrders">Encomendas</nuxt-link></v-list-item> -->
      <v-list-item><v-btn @click="customerOrders">Encomendas</v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link to="/products">Comprar Produtos</nuxt-link></v-btn></v-list-item>
    </v-list>
    <v-list color="transparent" v-if="user?.role == 'Administrator'">
      <v-list-item prepend-icon="mdi-account-box" title="Administrador"></v-list-item>
      <v-list-item><v-btn><nuxt-link to="/logisticsOperator/create">Criar O.Logistica</nuxt-link></v-btn></v-list-item>
      <v-list-item><v-btn><nuxt-link to="/manufacturers/create">Criar Fabricante</nuxt-link></v-btn></v-list-item>
    </v-list>

    <template v-slot:append>
      <div class="pa-2">
        <v-btn block @click="cartStore.openDialog" v-if="user?.role === 'EndConsumer'" class="mb-4">Carrinho</v-btn>
        <v-btn block @click="sair()" v-if="user">Logout</v-btn>
        <v-btn block v-else><nuxt-link to="/auth/login">Login</nuxt-link></v-btn>
      </div>
    </template>
  </v-navigation-drawer>
  <v-app-bar color="deep-purple">
    <v-app-bar-nav-icon button @click="sidebar = !sidebar"></v-app-bar-nav-icon>

    <v-col align="center">
      <v-row>
        <v-app-bar-title justify="center"><nuxt-link to="/">PROJETO DAE</nuxt-link></v-app-bar-title>
      </v-row>
    </v-col>
  </v-app-bar>
  <cart-modal v-if="cartStore.modalOpen"></cart-modal>
</template>

<script setup>
import { useAuthStore } from '@/store/auth-store';
import CartModal from '@/components/CardModal.vue'
import {useCartStore} from "@/store/cart-store"

const cartStore = useCartStore()
const authStore = useAuthStore();
const { user } = storeToRefs(authStore)

const route = useRoute()

const username = route.params.username
const sidebar = ref(true)

const customerOrders = () => {
  navigateTo('/endconsumers/' + user.value.username + '/orders')
}

const sair = () => {
  authStore.logout()
  navigateTo('/auth/login')
}
</script>

<style scoped></style>

