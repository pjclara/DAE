import { defineStore } from "pinia";

export const useCartStore = defineStore("cartStore", () => {
  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  const router = useRouter();

  const modalOpen = ref(false);
  const cartItems = ref([]);
  const orderData = ref({});

  const totalItens = ref(0);

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
    totalItens.value = cartItems.value.length;
    alert("Product added to cart");
  };

  const createOrderCart = (customer) => {
    // confirm the order has items
    if (cartItems.value.length == 0) {
      alert("Please add some items to your cart before checking out.");
      return;
    }
    /// get id of cartItems
    let ids = cartItems.value.map((item) => item.id);

    let idCounts = ids.reduce((acc, id) => {
      acc[id] = (acc[id] || 0) + 1;
      return acc;
    }, {});

    // Converting the object back to an array of unique IDs and their counts
    let itemsIds = Object.keys(idCounts).map((id) => ({
      productId: id,
      quantity: idCounts[id],
    }));

    orderData.value = {
      status: "Pending",
      orderItems: itemsIds,
    };

    let boolean = confirm("Are you sure you want to place this order?") 
    if (boolean)
      createOrderAPI(customer);
    else
      closeDialog();
  };

  async function createOrderAPI(customer) {
    if (!customer) {
      console.log("username :", customer);
      return;
    }
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(orderData.value),
    };
    console.log("requestOptions :", requestOptions.body);
    const { data, error } = await useFetch(`${api}/endConsumers/${customer}/orders`, requestOptions);
    if (error.value) {
      console.log("error :", error.value);
      return;
    }
    console.log("data :", data);
    if (data) {
      console.log("data :", data);
      alert("Order created");
      cartItems.value = [];
      totalItens.value = 0;
      closeDialog();
      router.push(`endConsumers/${customer}/orders`);
    }

  }

  const productsInCart = () => {
    let ids = cartItems.value.map((item) => item.id);
    // count the number of each id
    let idCounts = ids.reduce((acc, id) => {
      acc[id] = (acc[id] || 0) + 1;
      return acc;
    }, {});
    return Object.keys(idCounts).map((id) => ({
      id: id,
      count: idCounts[id],
      name: cartItems.value.find((item) => item.id == id).name,
      image: cartItems.value.find((item) => item.id == id).image,
    }));
  };

  // increment the count of the item with id
  const increment = (id) => {
    let item = cartItems.value.find((item) => item.id == id);
    if (productsInCart().find((item) => item.id == id).count < item.stock) {
      cartItems.value.push(item);
      totalItens.value = cartItems.value.length;
    }
  };

  // decrement the count of the item with id
  const decrement = (id) => {
    let item = cartItems.value.find((item) => item.id == id);
    if (productsInCart().find((item) => item.id == id).count > 0) {
      // remove the item from the cart
      cartItems.value.splice(cartItems.value.indexOf(item), 1);
      totalItens.value = cartItems.value.length;
    }
  };

  return {
    createOrderCart,
    add,
    openDialog,
    closeDialog,
    modalOpen,
    cartItems,
    productsInCart,
    increment,
    decrement,
    totalItens,
  };
});
