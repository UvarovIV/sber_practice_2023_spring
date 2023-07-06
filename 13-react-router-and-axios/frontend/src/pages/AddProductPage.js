import React, {useState} from "react";
import {Button, Form, Input} from "antd";
import {useDispatch} from "react-redux";
import ProductService from "../services/productService";

const AddProduct = () => {
    const [form] = Form.useForm();
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const onFinish = (values) => {
        setLoading(true);

        setTimeout(() => {
            ProductService.createProduct(values, dispatch);
            form.resetFields();
            setLoading(false);
        }, 1000);
    };

    return (
        <div>
            <h2>Добавление товара</h2>
            <Form form={form} layout="vertical" onFinish={onFinish}>
                <Form.Item
                    label="Наименование"
                    name="name"
                    rules={[{required: true, message: "Пожалуйста, введите наименование товара"}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    label="Цена"
                    name="price"
                    rules={[{required: true, message: "Пожалуйста, введите цену товара"}]}
                >
                    <Input type="number"/>
                </Form.Item>
                <Form.Item
                    label="Количество"
                    name="amount"
                    rules={[{required: true, message: "Пожалуйста, введите количество товара"}]}
                >
                    <Input type="number"/>
                </Form.Item>
                <Form.Item
                    label="URL изображения"
                    name="ImageUrl"
                    rules={[{required: true, message: "Пожалуйста, введите Url изображения"}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Добавить
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default AddProduct;