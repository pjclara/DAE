<template>
    <header>
      <h1>{{ titulo }}</h1>
      <!-- Adicione mais elementos do cabeçalho conforme necessário -->
      <nav>
        <ul>
          <li><router-link to="/">Página Inicial</router-link></li>
          <!-- Adicione mais itens de navegação -->
        </ul>
      </nav>
    </header>
  </template>
  
  <script>
  export default {
    props: {
      titulo: String,
    },
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
  