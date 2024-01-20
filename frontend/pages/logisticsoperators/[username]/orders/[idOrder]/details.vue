<template>
  <div>
    <h1>Detalhes de encomenda</h1>
    <v-card color="secondary" rounded="xl">
      <v-card-title>
        <span class="grey--text">Nome de Cliente: </span>
        <span class="headline">{{ order.endConsumerName }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="4">
              <h3 class="grey--text">Estado: </h3>
              <span>{{ order.status }}</span>
            </v-col>
            <v-col cols="12" sm="4">
              <h3 class="grey--text">Embalagem: </h3>
              <span v-if="order.packageOrder">{{ order.packageOrder?.packagingMaterial }}</span>
              <span v-else>Sem Embalagem ...</span>
            </v-col>
            <v-col cols="12" sm="4">
              <h3>Sensores:</h3>
              <span v-if="order.packageSensorDTO?.sensorValueDTOS != []">
                <v-chip v-for="sensor in order.packageSensorDTO?.sensorValueDTOS" :key="sensor.id" color="black" text-color="white">
                  {{ sensor.sensorDTO.type }}: {{ sensor.value == null ? '--':sensor.value  }} {{ sensor.sensorDTO.unit }}
                </v-chip>
              </span>
            </v-col> 
          </v-row>
        </v-container>
        <v-container>
          <v-row>
            <h3>Lista de produtos</h3>
          </v-row>
          <v-row v-for="item in order.orderItems">
            <v-col> 
              <h3>Nome do producto:</h3>{{ item.unitProductDTO.productDTO.name }}
            </v-col>
            <v-col>
              <h3>Quantidade:</h3>{{ item.quantity }}
            </v-col>
            <v-col>
              <h3>Sensores:</h3>
              {{ }}
              <span v-if="item.unitProductDTO.packageSensorDTO.sensorValueDTOS.size != []">
                <v-chip v-for="sensor in item.unitProductDTO.packageSensorDTO.sensorValueDTOS" :key="sensor.id" color="black" text-color="white">
                  {{ sensor.sensorDTO.type }}: {{ sensor.value == null ? '--':sensor.value  }} {{ sensor.sensorDTO.unit }}
                </v-chip>
              </span> 
              <span v-else>Sem sensores ...</span>
            </v-col>
          </v-row>
        </v-container>

      </v-card-text>
    </v-card>
    <br>
    <v-btn block rounded="xl" size="x-large" @click="editItem()" color="green">Editar encomenda</v-btn>
    <br>
    <v-btn block rounded="xl" size="x-large" @click="back" color="gray">Voltar</v-btn>
  </div>
</template>
  
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const route = useRoute()
const username = route.params.username
const idOrder = route.params.idOrder
const { data: order, error } = await useFetch(`${api}/orders/${idOrder}`)
//const { data: sensorsInOrder } = await useFetch(`${api}/orders/${idOrder}/sensorsInOrder`)


const back = () => navigateTo(`/logisticsoperators/${username}/orders`)
const editItem = () => navigateTo(`/logisticsoperators/${username}/orders/${idOrder}/edit`)

</script>
