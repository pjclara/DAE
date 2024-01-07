<template>
  <v-app>
    <v-app-bar color="deep-purple">
      <v-col align="center">
        <v-row>
          <v-app-bar-title justify="center">Atualização de Sensores</v-app-bar-title>
        </v-row>
      </v-col>
    </v-app-bar>
    <v-main>
      <div>
        <v-col align="center">
            <v-col cols="6">
              <div v-if="!sensor">
                <h3>Coloque o ID do Sensor</h3>
                <form @submit.prevent="fetchSensor">
                  <div>
                      <v-text-field v-model="sensorId" label="Id" />
                  </div>
                  <v-btn block rounded="xl" size="x-large" @click="fetchSensor">Obter Dados</v-btn>
                </form>
              </div>
              <div v-else>
                <h1 v-if="!showUpdateForm">Detalhes Sensor</h1>
                <h1 v-else>Editar Sensor</h1>
                <form @submit.prevent="update">
                  <div>
                      <v-text-field v-model="sensor.source" :disabled="!showUpdateForm" label="Fonte" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.type" :disabled="!showUpdateForm" label="Tipo" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.value" :disabled="!showUpdateForm" label="Valor" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.unit" :disabled="!showUpdateForm" label="Unidade" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.max" :disabled="!showUpdateForm" label="Max" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.min" :disabled="!showUpdateForm" label="Min" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.packageId" :disabled="!showUpdateForm" label="Embalagem ID" />
                  </div>
                  <div>
                      <v-text-field v-model="sensor.timestamp" :disabled="!showUpdateForm" label="Timestamp" />
                  </div>
                  <v-switch v-model="showUpdateForm" label="Modificar valores"></v-switch>
                  <v-btn v-if="showUpdateForm" block rounded="xl" size="x-large" @click="update">Atualizar</v-btn>
                </form>
              </div>
            </v-col>
        </v-col>
      </div>
    </v-main>
  </v-app>
</template>

<script setup>
import fetch from 'node-fetch';
import { ref } from "vue";

const sensorId = ref(null)
const sensor = ref(null)
const showUpdateForm = ref(false);

async function fetchSensor() {
  const response = await fetch(`http://localhost:8080/backend/api/sensors/` + sensorId.value, {
    method: 'get',
    headers: {
      'Accept': 'application/json'
    }
  })
  const data = await response.json();
  if(data){
    sensor.value = data
  }
}

async function update() {
  const requestOptions = {
      method: 'PUT',
      headers: {
          "Content-Type": "application/json",
          'Accept': 'application/json'
      },
      body: JSON.stringify(sensor.value)
  }
  const response = await fetch(`http://localhost:8080/backend/api/sensors/` + sensorId.value, requestOptions)
  //console.log("response: ", response)
  const data = await response.json();
    if (data)
      sensor.value = data
      showUpdateForm.value = false
}
</script>

<style>

</style>
