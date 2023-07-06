import {Button, Card, Form, Input} from "antd";
import React from "react";
import Title from "antd/es/skeleton/Title";
import UserService from "../services/userService";
import {useDispatch} from "react-redux";

const LoginPage = () => {

    const dispatch = useDispatch();
    const onFinish = (values) => {
        const loginData = {
            login: values.login,
            password: values.password
        };
        UserService.authorize(loginData, dispatch)
    };

    return (
        <Card>
            <Title level={4}>Регистрация</Title>
            <Form onFinish={onFinish}>
                <Form.Item
                    name="login"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш email!'}]}
                >
                    <Input placeholder="Email"/>
                </Form.Item>

                <Form.Item
                    name="password"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш пароль!'}]}
                >
                    <Input.Password placeholder="Пароль"/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Войти
                    </Button>
                </Form.Item>
            </Form>
        </Card>
    );
};

export default LoginPage;