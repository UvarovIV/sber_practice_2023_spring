import axios from "axios";
import UserService from "./userService";

const API_URL = "http://localhost:8080/payment";

const pay = (payment, dispatch) => {

    return axios.post(API_URL, payment).then(
        () => {
            UserService.getUser(payment.userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const paymentService = {
    pay,
};

export default paymentService