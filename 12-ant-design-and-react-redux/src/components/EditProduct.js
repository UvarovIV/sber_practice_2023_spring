import React, { useState } from "react";
import { Form, Input, Button } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { update, remove } from "../slices/productsSlice";

const EditProduct = () => {
    const products = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const [form] = Form.useForm();

    const [selectedProductIndex, setSelectedProductIndex] = useState(null);

    const handleEditProduct = (index) => {
        setSelectedProductIndex(index);
        form.setFieldsValue(products[index]);
    };

    const handleSaveChanges = () => {
        form.validateFields().then((values) => {
            const updatedProduct = {
                ...values,
                id: products[selectedProductIndex].id,
            };
            dispatch(update(updatedProduct));
            setSelectedProductIndex(null);
            form.resetFields();

        });
        console.log(products)
    };

    const handleDeleteProduct = (product) => {
        dispatch(remove(product));
        form.resetFields();
        console.log(products)
    };

    return (
        <div>
            <h2>Список товаров</h2>
            {products.map((product, index) => (
                <div key={product.id}>
                    <img src={product.imageUrl} alt={product.name} width="200" height="250" />
                    <p>Название: {product.name}</p>
                    <p>Цена: {product.price} руб.</p>
                    <p>Количество: {product.amount} шт.</p>
                    {selectedProductIndex === index ? (
                        <Form
                            form={form}
                            initialValues={product}
                            onFinish={handleSaveChanges}
                            style={{ width: 500 }}
                        >
                            <Form.Item name="name" label="Новое название" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name="price" label="Новая цена" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name="amount" label="Новое количество" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item name="imageUrl" label="Новое изображение" rules={[{ required: true }]}>
                                <Input />
                            </Form.Item>
                            <Form.Item>
                                <Button style={{marginRight: 5}} type="primary" htmlType="submit">Сохранить изменения</Button>
                                <Button type="default" onClick={() => setSelectedProductIndex(null)}>
                                    Отмена
                                </Button>
                            </Form.Item>
                        </Form>
                    ) : (
                        <div>
                            <Button style={{marginRight: 5, marginBottom: 5}} onClick={() => handleEditProduct(index)}>Редактировать</Button>
                            <Button onClick={() => handleDeleteProduct(product)}>Удалить</Button>
                        </div>
                    )}
                    <hr />
                </div>
            ))}
        </div>
    );
};

export default EditProduct;