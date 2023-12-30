import {defineStore} from "pinia";


export const useCartStore = defineStore("cartStore", () => {
    const config = useRuntimeConfig()
    const api = config.public.API_URL

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
        console.log("customer: ", customer)

        orderData.value = {
            status: 'Pending',
            endConsumer: customer,
            products: cartItems.value
        }
        console.log("orderData.value: ", orderData.value)
        createOrderAPI();
    }

    async function createOrderAPI() {
        const { data, error } = await useFetch(`${api}/orders`, {
            method: "post",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(orderData.value)
        })
        if (error.value) {
            console.log('error.value :', error.value)
        }
        if (data.value) {
            console.log('data :', data.value)
            //navigateTo('/')
        }
    }

    return { createOrderCart, add, openDialog, closeDialog, modalOpen, cartItems }
})