import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { List, Button, InputNumber } from "antd";
import { removeFromCart, updateQuantity, clearCart } from "../slices/productsCartSlice";

const Cart = () => {
    const cartItems = useSelector((state) => state.cart.items);
    const dispatch = useDispatch();

    const handleRemoveProduct = (productId) => {
        dispatch(removeFromCart(productId));
    };

    const handleUpdateQuantity = (productId, quantity) => {
        dispatch(updateQuantity({ productId, quantity }));
    };

    const handleClearCart = () => {
        dispatch(clearCart());
        alert("Оплата прошла успешно")
    };

    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => {
            return total + item.price * item.amount;
        }, 0);
    };

    return (
        <div>
            <h2>Корзина</h2>
            {cartItems && cartItems.length > 0 ? (
                <List
                    dataSource={cartItems}
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
                                    min={1}
                                    defaultValue={item.amount}
                                    onChange={(quantity) =>
                                        handleUpdateQuantity(item.id, quantity)
                                    }
                                />
                            </div>
                        </List.Item>
                    )}
                />
            ) : (
                <p>Корзина пуста</p>
            )}

            <p>Общая стоимость: {getTotalPrice()} руб.</p>

            <Button type="primary" style={{ marginTop: "16px" }} onClick={handleClearCart}>
                Оформить заказ
            </Button>
        </div>
    );
};

export default Cart;
