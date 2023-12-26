<template>
    <div>
        <v-data-table
          :headers="headers"
          :items="orders"
          :items-per-page="5"
          class="elevation-1"
        />
    </div>
</template>

<script setup>
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: orders, error, refresh } = await useFetch(`${api}/products`)
    
    const headers = ref([
        { text: 'Name', value: 'name', align: 'center' },
        { text: 'Stock', value: 'stock', align: 'center' },
        { text: 'Manufacturer', value: 'manufacturer', align: 'center' },
        { text: 'Product Package', value: 'productPackage', align: 'center' },
        { text: '', value: '', align: 'center' }
    ])
</script>