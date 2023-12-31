import axios from "axios";
import UserService from "./userService";

const API_URL = "http://localhost:8080/cart";

const addToCart = (userId, productId, dispatch) => {

    return axios.post(`${API_URL}/${userId}/product/${productId}`, {amount: 1}).then(
        () => {
            UserService.getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateAmount = (userId, productId, amount, dispatch) => {
    return axios.put(`${API_URL}/${userId}/product/${productId}`, amount).then(
        () => {
            UserService.getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteFromCart = (userId, productId, dispatch) => {

    return axios.delete(`${API_URL}/${userId}/product/${productId}`).then(
        () => {
            UserService.getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const cartService = {
    addToCart,
    deleteFromCart,
    updateAmount
};

export default cartService