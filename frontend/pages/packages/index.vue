<template>
    <v-card
    flat
    title="Packages"
  >
        <div class="w-100">
            <v-data-table 
                :headers="headers"
                :items="getPackages()" 
                :items-per-page="5" 
                class="elevation-1"
            >
                <template v-slot:item.action="{ item }">
                    <v-btn>OPEN</v-btn>
                </template>
            </v-data-table>
        </div>
    </v-card>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL

const { data: packages, error, refresh } = await useFetch(`${api}/packages`)

console.log("packages: ", packages.value);

const getPackages = () => {
    const items = (packages.value || []).map(package_ => {
        return {
            type: package_.packagingType || '-',
            material: package_.packagingMaterial || '-',
            sensors: (package_.sensors.lenght === 0) ? package_.sensors : '-',
        }
    })
    return items;
}
console.log("All Packages: ", getPackages());

const headers = [
    {
      title: 'Type',
      align: 'center',
      value: 'type',
    },
    {
      title: 'Material',
      align: 'center',
      value: 'material',
    },
    {
      title: 'Sensors',
      align: 'center',
      value: 'sensors',
    },
]

</script>

<style>
</style>

