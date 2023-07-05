import {createSlice} from "@reduxjs/toolkit";

const productsCartSlice = createSlice({
    name: "cart",
    initialState: {
        items: [],
    },
    reducers: {
        setCart: (state, action) => {
            state.items = action.payload;
        }
    }

});

export const {setCart} = productsCartSlice.actions;
export default productsCartSlice.reducer;