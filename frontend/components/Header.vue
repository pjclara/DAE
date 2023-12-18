<template>
  <v-app-bar
      color="primary"
    >
    <v-col align="center">
      <v-row>
        <v-app-bar-title justify="center">PROJETO DAE</v-app-bar-title>
      </v-row>
      <v-row justify="space-around">
        <nuxt-link to="/">Home</nuxt-link>
        |
        <nuxt-link to="/endconsumers">Clientes</nuxt-link>
        |
        <nuxt-link to="/manufacturers">Fabricantes</nuxt-link>
        |
        <nuxt-link to="/logisticsoperator">Operadores Logistica</nuxt-link>
        |
        <nuxt-link to="/products">Produtos</nuxt-link>
        |
        <nuxt-link to="/orders">Encomendas</nuxt-link>
        |
        <nuxt-link to="/packages">Embalagens</nuxt-link>
        |
        <nuxt-link to="/sensors">Sensores</nuxt-link>
        <div>
          <v-btn
            color="primary"
            @click="logout"
          >
            Logout
          </v-btn>
          |
          <nuxt-link to="/auth/login">Login</nuxt-link>
          |
          <v-btn><v-icon>cart-outlined</v-icon></v-btn>
        </div>
      </v-row>
    </v-col>
    </v-app-bar>
</template>

<script setup>
  import { useAuthStore } from '@/store/auth-store';

  const authStore = useAuthStore();
  const { logout } = authStore;

</script>

<style scoped>  
</style>

