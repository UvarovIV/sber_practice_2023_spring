import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Button, InputNumber, List} from "antd";
import CartService from "../services/cartService";
import PaymentService from "../services/paymentService";
import cartService from "../services/cartService";

const CartPage = () => {
    const userId = useSelector((state) => state.auth.user.id);
    const cartItems = useSelector((state) => state.cart.items);
    const dispatch = useDispatch();

    useEffect(() => {
        cartService.getCart(userId, dispatch);
    }, []);

    const handleRemoveProduct = (productId) => {
        CartService.deleteFromCart(userId, productId, dispatch);
    };

    const handleUpdateQuantity = (productId, quantity) => {
        const newProductAmount = {
            amount: quantity,
        };
        CartService.updateAmount(userId, productId, newProductAmount, dispatch);
    };

    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => {
            return total + item.price * item.amount;
        }, 0);
    };

    const handlePayment = () => {
        const payment = {cardNumber: 123456, userId: userId}
        PaymentService.pay(payment, dispatch)
        cartService.getCart(userId, dispatch)
    }

    const [sortType, setSortType] = useState("name"); // Установите начальное значение сортировки по имени

    const sortedCartItems = [...cartItems]; // Создайте копию товаров из корзины
    sortedCartItems.sort((a, b) => {
        if (a[sortType] < b[sortType]) return -1;
        if (a[sortType] > b[sortType]) return 1;
        return 0;
    });

    const handleSort = (type) => {
        setSortType(type);
    };

    return (
        <div>
            <h2>Корзина</h2>
            {cartItems && cartItems.length > 0 ? (
                <div>
                    <div>
                        Сортировка:
                        <Button style={{margin: "0 8px"}} onClick={() => handleSort("name")}
                                type={sortType === "name" ? "primary" : "default"}>
                            По имени
                        </Button>
                        <Button style={{marginRight: "8px"}} onClick={() => handleSort("price")}
                                type={sortType === "price" ? "primary" : "default"}>
                            По цене
                        </Button>
                        <Button style={{marginRight: "8px"}} onClick={() => handleSort("amount")}
                                type={sortType === "amount" ? "primary" : "default"}>
                            По количеству
                        </Button>
                    </div>
                    <List
                        dataSource={sortedCartItems}
                        renderItem={(item) => (
                            <List.Item
                                actions={[
                                    <Button
                                        type="link"
                                        onClick={() => handleRemoveProduct(item.id)}
                                        key="delete"
                                    >
                                        Удалить
                                    </Button>,
                                ]}
                            >
                                <List.Item.Meta
                                    title={item.name}
                                    description={`Цена: ${item.price} руб. | Количество: ${item.amount} шт.`}
                                />
                                <div>
                                    Количество:
                                    <InputNumber
                                        style={{marginLeft: "8px"}}
                                        min={1}
                                        value={item.amount}
                                        onChange={(quantity) =>
                                            handleUpdateQuantity(item.id, quantity)
                                        }
                                    />
                                </div>
                            </List.Item>
                        )}
                    />
                </div>
            ) : (
                <p>Корзина пуста</p>
            )}

            <p>Общая стоимость: {getTotalPrice()} руб.</p>

            <Button
                type="primary"
                style={{marginTop: "16px"}}
                onClick={handlePayment}
            >
                Оформить заказ
            </Button>
        </div>
    );
};

export default CartPage;