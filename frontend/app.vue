<template>

    <v-app>
      <HeaderTemplate></HeaderTemplate>
      <v-main>
        <NuxtPage class="pa-2" />
      </v-main>
    </v-app>

</template>

<script>
import HeaderTemplate from "@/components/Header.vue";

export default {
  components: {
    HeaderTemplate,
  },
};
</script>

<style>
a:-webkit-any-link {
    color: inherit;
    cursor: auto;
    text-decoration: none;
}
</style>