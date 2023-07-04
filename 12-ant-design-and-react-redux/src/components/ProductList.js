import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Row, Col, Card, Button, Input } from "antd";
import { addToCart } from "../slices/productsCartSlice";

const { Meta } = Card;

const ProductList = () => {
    const products = useSelector((state) => state.products.products);
    const dispatch = useDispatch();

    const [searchValue, setSearchValue] = useState("");

    const handleAddToCart = (product) => {
        dispatch(addToCart(product));
    };

    const handleSearch = (e) => {
        setSearchValue(e.target.value);
    };

    const filteredProducts = products.filter((product) =>
        product.name.toLowerCase().includes(searchValue.toLowerCase())
    );

    return (
        <div>
            <h2>Список товаров</h2>
            <Input
                placeholder="Поиск по названию"
                value={searchValue}
                onChange={handleSearch}
                style={{ marginBottom: 16 }}
            />
            <Row gutter={[16, 16]}>
                {filteredProducts.map((product) => (
                    <Col lg={4} key={product.id}>
                        <Card
                            size={"small"}
                            cover={<img alt={product.name} src={product.imageUrl} />}
                            style={{ marginBottom: 40, flex: "auto" }}
                            actions={[
                                <Button
                                    size="small"
                                    onClick={() => handleAddToCart(product)}
                                >
                                    Добавить в корзину
                                </Button>
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
        </div>
    );
};

export default ProductList;