import { defineStore } from "pinia";

export const useCartStore = defineStore("cartStore", () => {
  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  const modalOpen = ref(false);
  const cartItems = ref([]);
  const orderData = ref({});

  const openDialog = () => {
    modalOpen.value = true;
    console.log("open");
  };

  const closeDialog = () => {
    modalOpen.value = false;
    console.log("close");
  };

  const add = (product) => {
    cartItems.value.push(product);
    console.log("cart: ", cartItems.value);
  };

  const createOrderCart = (customer) => {

    /// get id of cartItems
    let ids = cartItems.value.map((item) => item.id);

    let idCounts = ids.reduce((acc, id) => {
        acc[id] = (acc[id] || 0) + 1;
        return acc;
      }, {});

      // Converting the object back to an array of unique IDs and their counts
      let itemsIds = Object.keys(idCounts).map(id => (
        [parseInt(id), idCounts[id]])
      );

    orderData.value = {
      status: "Pending",
      endConsumerName: customer,
      orderItems: itemsIds,
    };

    console.log("orderData.value: ", orderData.value);
    console.log(
      "JSON.stringify(orderData.value): ",
      JSON.stringify(orderData.value)
    );
    createOrderAPI();
  };

  async function createOrderAPI() {
    const { data, error } = await useFetch(`${api}/orders`, {
      method: "post",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(orderData.value),
    });
    if (error.value) {
      console.log("error.value :", error.value);
    }
    if (data.value) {
      console.log("data :", data.value);
      //navigateTo('/')
    }
  }

  const productsInCart = () => {
    let ids = cartItems.value.map((item) => item.id);
    // count the number of each id
    let idCounts = ids.reduce((acc, id) => {
      acc[id] = (acc[id] || 0) + 1;
      return acc;
    }, {});
    return Object.keys(idCounts).map(id => (
      //"id: " + id + " count: " + idCounts[id] + " name: " + cartItems.value.find(item => item.id == id).name
      // array of objects
      {
        id: id,
        count: idCounts[id],
        name: cartItems.value.find(item => item.id == id).name,
        image: cartItems.value.find(item => item.id == id).image,
      }
    ));
  };

  return {
    createOrderCart,
    add,
    openDialog,
    closeDialog,
    modalOpen,
    cartItems,
    productsInCart,
  };
});
