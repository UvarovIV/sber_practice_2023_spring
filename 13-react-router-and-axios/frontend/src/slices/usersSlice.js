import {createSlice} from "@reduxjs/toolkit";

const usersSlice = createSlice({
    name: "user",
    initialState: {
        user: {
            id: 1,
        }
    },
    reducers: {
        set: (state, action) => {
            state.user = action.payload;
        },
    },
});

export const {set} = usersSlice.actions;
export default usersSlice.reducer;