import {createSlice} from "@reduxjs/toolkit";

const initialState = {
    usersList: [
        {
            id: 1,
            name: "Человек 1",
            email: "email1@example.com",
            login: "login",
            password: "password",
        },
        {
            id: 2,
            name: "Человек 2",
            email: "email2@example.com",
            login: "login",
            password: "password",
        },
    ],
    userInfo: null,
};

const usersSlice = createSlice({
    name: "users",
    initialState,
    reducers: {
        setUserInfo: (state, action) => {
            state.userInfo = action.payload;
        },
    },
});

export const {setUserInfo} = usersSlice.actions;
export default usersSlice.reducer;