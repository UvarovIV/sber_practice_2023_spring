import axios from "axios";
import {set} from "../slices/usersSlice";
import {setCart} from "../slices/productsCartSlice";

const API_URL = "http://localhost:8080/users";

const getUser = (id, dispatch) => {
    return axios.get(API_URL + `/${id}`).then(
        (response) => {
            dispatch(set(response.data));
            dispatch(setCart(response.data.cart))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });

};

const createUser = (user, dispatch) => {
    return axios.post(API_URL, user).then(
        (response) => {
            getUser(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};


const userService = {
    getUser,
    createUser,
};

export default userService