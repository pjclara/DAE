<template>
    <v-app-bar
        class="flex-wrap"
        color="primary"
        density="compact"
        relative
      >
        <v-app-bar class="flex-grow-1">DAE PROJECT</v-app-bar>
        <v-app-bar-title><nuxt-link to="/">Home</nuxt-link></v-app-bar-title>
        <v-app-bar-title><nuxt-link to="/about">About</nuxt-link></v-app-bar-title>
      </v-app-bar>
  </template>
  
  <script>
  export default {
    name: "Header",
  };
  </script>
  
  <style scoped>
  /* Adicione estilos CSS conforme necessário */
  header {
    background-color: #333;
    color: #fff;
    padding: 20px;
  }
  
  h1 {
    margin: 0;
  }
  
  nav ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }
  
  nav li {
    display: inline;
    margin-right: 15px;
  }
  
  nav a {
    color: #fff;
    text-decoration: none;
  }
  </style>
  