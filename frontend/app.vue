<template>
  <div>
    <v-app>
      <HeaderTemplate></HeaderTemplate>
      <v-main>
        <NuxtPage />
      </v-main>
    </v-app>
  </div>
</template>

<script>
import HeaderTemplate from "@/components/Header.vue";

export default {
  components: {
    HeaderTemplate,
  },
};
</script>
