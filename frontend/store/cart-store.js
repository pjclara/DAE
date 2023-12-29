import {defineStore} from "pinia";

export const useCartStore = defineStore("cartStore", () => {
    const modalOpen = ref(false)
    const cartItems = ref([])
    const orderData = ref({})

    const openDialog = () => {
        modalOpen.value = true;
        console.log("open")
    }

    const closeDialog = () => {
        modalOpen.value = false;
        console.log("close")
    }

    const add = (product) => {
        cartItems.value.push(product)
    }

    const createOrderCart = (customer) => {
        data.value = {
            status: 'Pending',
            endConsumer: customer,
        }
        createOrderAPI();
    }

    async function createOrderAPI() {
        const { data, error } = await useFetch(`${api}/orders`, {
            method: "post",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(data.value)
        })
        if (error.value) {
            console.log('error.value :', error.value)
        }
        if (data.value) {
            console.log('data :', data.value)
            //navigateTo('/')
        }
    }

    return { add, openDialog, closeDialog, modalOpen, cartItems }
})