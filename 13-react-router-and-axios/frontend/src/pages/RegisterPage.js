import {Button, Card, Form, Input} from "antd";
import React from "react";
import Title from "antd/es/skeleton/Title";

const RegisterPage = () => {
    const onFinish = (values) => {
        console.log('Received values of form: ', values);
    };

    return (
        <Card>
            <Title level={4}>Регистрация</Title>
            <Form onFinish={onFinish}>
                <Form.Item
                    name="name"
                    rules={[{required: true, message: 'Пожалуйста, введите ваше имя!'}]}
                >
                    <Input placeholder="Имя"/>
                </Form.Item>

                <Form.Item
                    name="email"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш email!'}]}
                >
                    <Input placeholder="Email"/>
                </Form.Item>

                <Form.Item
                    name="login"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш логин!'}]}
                >
                    <Input placeholder="Логин"/>
                </Form.Item>

                <Form.Item
                    name="password"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш пароль!'}]}
                >
                    <Input.Password placeholder="Пароль"/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Зарегистрироваться
                    </Button>
                </Form.Item>
            </Form>
        </Card>
    );
};

export default RegisterPage;