<template>
    <div class="col-12">
        <v-data-table
          :headers="headers"
          :items="productsItems()"
          :items-per-page="5"
          class="elevation-1"
          search
        >
            <template v-slot:item.action="{ item }">
                <v-btn>OPEN</v-btn>
            </template>   
        </v-data-table>                                                                                                                                                                                                             
    </div>
</template>

<script setup>
    const config = useRuntimeConfig()
    const api = config.public.API_URL
    const { data: products, error, refresh } = await useFetch(`${api}/products`)

    console.log("products: ", products);
    console.log("products.value: ", products.value)

    const productsItems = () => {
        const items = (products.value || []).map(product => {
            return {
                name: product.name || '-',
                stock: product.stock || '-',
                manufacturer: product.manufacturerUsername || '-',
                productPackage: product.packageId || '-'
            }
        })
        return items;
    }
    console.log("productsItems: ", productsItems());

    const headers = [
        { text: 'Name', value: 'name', align: 'center', width: '20%' },
        { text: 'Stock', value: 'stock', align: 'center', width: '20%' },
        { text: 'Manufacturer', value: 'manufacturer', align: 'center', width: '20%' },
        { text: 'Product Package', value: 'productPackage', align: 'center', width: '20%' },
        { text: '', value: 'action', width: '10%' }
    ]
</script>