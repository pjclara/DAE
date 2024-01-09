<template>
    <v-col align="center">
      <v-col cols="8">
        <v-card>
          <v-card-title class="my-4" justify="center">
            <h2>Detalhes da Embalagem</h2>
          </v-card-title>

          <v-card-text>
            <div class="text-left">
              <h3>Tipo: </h3>
              <v-text-field v-model="item.packagingType" disabled/>
            </div>
            <div class="text-left">
              <h3>Material: </h3>
              <v-text-field v-model="item.packagingMaterial" disabled/>
            </div>
            <div class="text-left">
              <h3 class="mb-2">Sensores associados: </h3>
              <v-text-field v-if="!currentSensors" disabled> Sem sensores... </v-text-field>
              <div v-else>
                <v-chip
                  v-for="(sensor, index) in currentSensors"
                  :key="index"
                  label
                  class="mr-2 mb-2"
                  @click="viewSensor(sensor.id)"
                >
                {{ sensor.id }} - {{ sensor.type }}
                </v-chip>
              </div>
            </div>          

            <!-- Modal Sensores -->
            <div class="w-50 mt-4">
              <v-btn color="primary" @click="openSensorModal">
                Configurar Sensores
              </v-btn>

              <v-dialog v-model="dialog" width="600">
                <v-card>
                  <v-card-title>
                    <h2>Lista de Sensores</h2>
                  </v-card-title>

                  <v-card-text>
                    <!-- Add a v-select for choosing sensors -->
                    <v-select
                      v-model="selectedSensorToAdd"
                      :items="formattedAvailableSensors"
                      item-text="title"
                      item-value="value"
                      label="Adicionar Sensor"
                      multiple
                      chips
                      clearable
                    ></v-select>
                  </v-card-text>

                  <v-card-actions justify="center">
                    <v-btn color="primary" @click="closeSensorModal">Fechar</v-btn>
                    <v-btn color="primary" @click="updateListOfSensors" variant="outlined">Atualizar Lista</v-btn>                    
                  </v-card-actions>
                </v-card>
              </v-dialog>

            </div>
        </v-card-text>

        </v-card>
      </v-col>
    </v-col>
</template>


<script setup>
  import { useAuthStore } from "~/store/auth-store.js"
  const authStore = useAuthStore()
  const { token, user } = storeToRefs(authStore)
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  const route = useRoute()
  const dialog = ref(false);
  const id = route.params.id
  const { data: item, error: packageErr } = await useFetch(`${api}/packages/${id}`)
  console.log("Package Error: ", packageErr)

  const sensorArray = ref([])
  const currentSensors = ref([])

  // get the sensors of package
  const fetchDataAndUpdate = async () => {
  // Fetch all sensors
    const { data: allSensors, error: sensorErr } = await useFetch(`${api}/sensors`)
    const { data: preSelectedSensors, error: selSensorErr } = await useFetch(`${api}/packages/${id}/sensors`)
    
    sensorArray.value = allSensors.value
    currentSensors.value = preSelectedSensors.value
  };

  console.log(currentSensors.value)

  onMounted(() => {
    fetchDataAndUpdate()
  })

  // const { data: allSensors, error: sensorErr } = await useFetch(`${api}/sensors`)
  // const { data: preSelectedSensors, error: selSensorErr } = await useFetch(`${api}/packages/${id}/sensors`)

  // const sensorArray = allSensors.value
  // const currentSensors = preSelectedSensors.value

  const openSensorModal = () => { dialog.value = true; };

  const closeSensorModal = () => { dialog.value = false; };

const selectedSensorToAdd = ref(currentSensors.value.map(sensor => sensor.id) || []); // Pre-select based on ids of current sensors
const formattedAvailableSensors = computed(() =>
  sensorArray.map(sensor => ({
    title: `${sensor.id} - ${sensor.type}`,
    value: sensor.id,
  }))
);

console.log(formattedAvailableSensors.value)


// Check for removed sensors
const removedSensors = computed(() =>
  currentSensors.filter(sensor => !selectedSensorToAdd.value.includes(sensor.id)).map(sensor => sensor.id)
);

// Check for added sensors
const addedSensors = computed(() =>
  selectedSensorToAdd.value.filter(sensorId => !currentSensors.some(sensor => sensor.id === sensorId))
);

const updateListOfSensors = async () => {
  console.log('Removed: ', removedSensors.value)
  console.log('Added: ', addedSensors.value)  

  if (selectedSensorToAdd.value.length > 0) {
    console.log(selectedSensorToAdd.value)

    if(!!removedSensors.value){
      removedSensors.value.map(async sensorId => {

        const {data: response, error} = await useFetch(`${api}/packages/${id}/sensor/` + sensorId, {
            method: 'delete',
            headers: {
                'Authorization': 'Bearer ' + token.value
            }
        })

        console.log(`Sensor ${sensorId} removed successfully. Response:`, response.data);

        if (error.value) {
          console.error(`Error deleting sensor ${sensorId}:`, error);
        }
      })
    }

    if(!!addedSensors.value){
      addedSensors.value.map(async sensorId => {

        const {data: response, error} = await useFetch(`${api}/packages/${id}/sensor/` + sensorId, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + token.value
            }
        })

        console.log(`Sensor ${sensorId} added successfully. Response:`, response.data);

        if (error.value) {
          console.error(`Error adding sensor ${sensorId}:`, error);
        }
      })
    }

    closeSensorModal()
    fetchDataAndUpdate()
  }

  // Clear the selected sensors after processing
  // selectedSensorToAdd.value = [];
};


const viewSensor = (sensor) => {
    navigateTo(`/sensors/${sensor.id}/details`)
}


</script>

<style scoped>
</style>
