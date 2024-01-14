<template>

    <v-app>
      <HeaderTemplate></HeaderTemplate>
      <v-main>
        <Default>
          <NuxtPage class="pa-2" />
        </Default>
      </v-main>
    </v-app>

</template>

<script>
import Default from '/pages/layouts/default.vue'
import HeaderTemplate from "@/components/Header.vue";

export default {
  components: {
    HeaderTemplate,
    Default
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