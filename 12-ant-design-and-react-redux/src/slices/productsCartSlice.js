import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    items: [],
};

const productsCartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
        addToCart: (state, action) => {
            const existingItem = state.items.find(
                (item) => item.id === action.payload.id
            );

            if (existingItem) {
                existingItem.amount++;
            } else {
                state.items.push({
                    id: action.payload.id,
                    name: action.payload.name,
                    price: action.payload.price,
                    amount: 1,
                });
            }
        },
        removeFromCart: (state, action) => {
            state.items = state.items.filter(
                (item) => item.id !== action.payload
            );
        },
        updateQuantity: (state, action) => {
            const { productId, quantity } = action.payload;
            const item = state.items.find((item) => item.id === productId);

            if (item) {
                item.amount = quantity;
            }
        },
        clearCart: (state) => {
            state.items = [];
        },
    },
});

export const {
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
} = productsCartSlice.actions;
export default productsCartSlice.reducer;