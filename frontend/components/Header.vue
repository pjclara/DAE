<template>

  <v-navigation-drawer
    class="bg-deep-purple"
    theme="dark"
    v-model="sidebar"
    permanent
  >
    <v-list color="transparent">
      <v-list-item prepend-icon="mdi-account-box" title="Fabricante"></v-list-item>
      <v-list-item><nuxt-link to="/products/manufacturersProducts">Produtos</nuxt-link></v-list-item> <!-- Rota precisa de ID (mover para pasta [id] quando a rota tiver feita) -->
      <v-list-item><nuxt-link to="/packages">Embalagens</nuxt-link></v-list-item>

      <v-list-item prepend-icon="mdi-account-box" title="O. Logistica"></v-list-item>
      <v-list-item><nuxt-link to="/orders">Encomendas</nuxt-link></v-list-item>
      <v-list-item><nuxt-link to="/packages">Embalagens</nuxt-link></v-list-item>

      <v-list-item prepend-icon="mdi-account-box" title="Cliente"></v-list-item>
      <v-list-item><nuxt-link to="/orders">Encomendas</nuxt-link></v-list-item>
      <v-list-item><nuxt-link to="/products">Comprar Produtos</nuxt-link></v-list-item>

    </v-list>

    <template v-slot:append>
      <div class="pa-2">
        <v-btn block v-if="user">Logout</v-btn>
        <v-btn block v-else><nuxt-link to="/auth/login">Login</nuxt-link></v-btn>
      </div>
    </template>
  </v-navigation-drawer>
  <v-app-bar
    color="primary"
  >
    <v-app-bar-nav-icon button @click="sidebar = !sidebar" ></v-app-bar-nav-icon>
    
    <v-col align="center">
      <v-row>
        <v-app-bar-title justify="center"><nuxt-link to="/">PROJETO DAE</nuxt-link></v-app-bar-title>
      </v-row>
    </v-col>
  </v-app-bar>
</template>

<script setup>
  import { useAuthStore } from '@/store/auth-store';

  const authStore = useAuthStore();
  const { user, logout } = authStore;

  const sidebar = ref(false)

</script>

<style scoped>  
</style>

