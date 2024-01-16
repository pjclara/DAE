<template>
  <div>
    <h1>Order Details</h1>
    <v-card color="secondary" rounded="xl" >
      <v-card-title>
        <span class="grey--text">Consumer Name: </span>
        <span class="headline">{{ order.endConsumerName }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="6">
              <span class="grey--text">Status: </span>
              <span>{{ order.status }}</span>
            </v-col>
            <v-col cols="12" sm="6">
              <span class="grey--text">Package: </span>
              <span v-if="order.packageId == 0">No package ...</span>
              <span v-else>{{ order.packageId }}</span>
            </v-col>
          </v-row>
        
        </v-container>
      </v-card-text>
    </v-card>
    <br>
    <v-btn block rounded="xl" size="x-large" @click="editItem()">Edit order</v-btn>
    <br>
    <v-btn block rounded="xl" size="x-large" @click="back">Back</v-btn>

  </div>
</template>
  
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const idOrder = route.params.idOrder
const { data: order, error } = await useFetch(`${api}/orders/${idOrder}`)

const back = () => navigateTo(`/logisticsoperators/${username}/orders`)
const editItem = () => navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/edit`)

</script>
