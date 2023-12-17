<template>
  <div>
    <Header></Header>
    <v-layout>
      <NuxtPage />
    </v-layout>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";

export default {
  components: {
    Header,
  },
};
</script>
