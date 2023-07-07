import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Button, Card, Col, Input, message, Row} from "antd";
import ProductService from "../services/productService";
import CartService from "../services/cartService";

const {Meta} = Card;

const ProductList = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const products = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);

    useEffect(() => {
        ProductService.getProducts(dispatch);
    }, []);

    const [searchValue, setSearchValue] = useState("");
    const [sortedProducts, setSortedProducts] = useState([]);

    const handleAddToCart = (product) => {
        if (isLoggedIn) {
            CartService.addToCart(user.id, product.id, dispatch);
        } else {
            message.error("Для добавления товара в корзину вы должны быть авторизованы")
        }

    };

    const handleSearch = (e) => {
        setSearchValue(e.target.value);
    };

    const handleAddRandomToCart = () => {
        if (isLoggedIn) {
            const randomProductIndex = Math.floor(Math.random() * products.length);
            const randomProduct = products[randomProductIndex];
            CartService.addToCart(user.id, randomProduct.id, dispatch);
        } else {
            message.error("Для добавления товара в корзину вы должны быть авторизованы")
        }
    };

    useEffect(() => {
        if (!searchValue) {
            setSortedProducts([]);
        }
    }, [searchValue]);

    const filteredProducts = sortedProducts.length
        ? sortedProducts.filter((product) =>
            product.name.toLowerCase().includes(searchValue.toLowerCase())
        )
        : products.filter((product) =>
            product.name.toLowerCase().includes(searchValue.toLowerCase())
        );

    return (
        <div>
            <h2>Список товаров</h2>
            <Input
                placeholder="Поиск по названию"
                value={searchValue}
                onChange={handleSearch}
                style={{marginBottom: 16}}
            />
            <Row gutter={[16, 16]}>
                {filteredProducts.map((product) => (
                    <Col lg={4} key={product.id}>
                        <Card
                            size={"small"}
                            cover={<img alt={product.name} src={product.imageUrl}/>}
                            style={{marginBottom: 40, flex: "auto"}}
                            actions={[
                                <Button
                                    size="small"
                                    onClick={() => handleAddToCart(product)}
                                >
                                    Добавить в корзину
                                </Button>,
                            ]}
                        >
                            <Meta
                                title={product.name}
                                description={`Цена: ${product.price} руб. | Количество: ${product.amount} шт.`}
                            />
                        </Card>
                    </Col>
                ))}
            </Row>
            <Button type="primary" onClick={handleAddRandomToCart}>
                Добавить случайный товар в корзину
            </Button>
        </div>
    );
};

export default ProductList;
