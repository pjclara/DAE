<template>
    <v-dialog
        v-model="cartModal"
        width="500"
    >
    asdasd
    </v-dialog>
</template>

<script setup>
const props = defineProps({
  modalOpen: Boolean
})

const cartModal = props.modalOpen;

</script>