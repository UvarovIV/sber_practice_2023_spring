import { createSlice } from "@reduxjs/toolkit";

export const productsSlice = createSlice({
    name: "products",
    initialState: {
        products: [
            {
                id: 1,
                name: "Кофе",
                price: 500,
                amount: 10,
                imageUrl: "https://i.imgur.com/kuJAwpO.jpg",
            },
            {
                id: 2,
                name: "Чай",
                price: 500,
                amount: 15,
                imageUrl: "https://roscontrol.com/wp-content/uploads/2021/09/3ba875bde8eb635297da.jpg",
            },
            {
                id: 3,
                name: "Мохито",
                price: 350,
                amount: 30,
                imageUrl: "https://i.imgur.com/pCKmRvE.jpg",
            },
        ],
    },
    reducers: {
        push: (state, action) => {
            const product = action.payload;
            product.id = Math.floor(Math.random() * 1_000_000);
            state.products = [action.payload, ...state.products];
        },
        update: (state, action) => {
            const updatedProduct = action.payload;
            state.products = state.products.map((product) => {
                if (product.id === updatedProduct.id) {
                    return {
                        ...product,
                        name: updatedProduct.name,
                        price: updatedProduct.price,
                        amount: updatedProduct.amount,
                        imageUrl: updatedProduct.imageUrl,
                    };
                }
                return product;
            });
        },
        remove: (state, action) => {
            state.products = state.products.filter(
                (product) => product.id !== action.payload.id
            );
        },
    },
});

export const { push, update, remove } = productsSlice.actions;

export default productsSlice.reducer;