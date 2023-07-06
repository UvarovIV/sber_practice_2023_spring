import axios from "axios";
import {set} from "../slices/productsSlice";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/products";

const getProducts = (dispatch) => {
    return axios.get(API_URL).then(
        (response) => {
            dispatch(set(response.data));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });

};

const createProduct = (product, dispatch) => {

    return axios.post(API_URL, product, {headers: authHeader()}).then(
        (response) => {
            getProducts(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProduct = (product, dispatch) => {
    return axios.put(API_URL, product, {headers: authHeader()}).then(
        (response) => {
            getProducts(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProduct = (id, dispatch) => {
    return axios.delete(API_URL + `/${id}`, {headers: authHeader()}).then(
        (response) => {
            getProducts(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const productService = {
    getProducts,
    createProduct,
    updateProduct,
    deleteProduct,
};

export default productService