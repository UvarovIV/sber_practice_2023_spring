import {Layout, Menu} from "antd";
import React from "react";
import {Link, Routes, Route} from "react-router-dom";
import CartPage from "./pages/CartPage";
import EditProductPage from "./pages/EditProductPage";
import ProductListPage from "./pages/ProductListPage";
import UserProfilePage from "./pages/UserProfilePage";
import AddProductPage from "./pages/AddProductPage";
import {NotFoundPage} from "./pages/NotFoundPage";
import RegisterPage from "./pages/RegisterPage";
import LoginPage from "./pages/LoginPage";

const {Content, Footer, Sider} = Layout;
const App = () => {


    return (
        <Layout>
            <Sider>
                <div className="demo-logo-vertical"/>
                <Menu theme="dark" mode="inline">
                    <Menu.Item key="1">
                        <Link to="/products/add">Добавление товаров</Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Link to="/products/edit">Изменение товара</Link>
                    </Menu.Item>
                    <Menu.Item key="3">
                        <Link to="/">Список товаров</Link>
                    </Menu.Item>
                    <Menu.Item key="4">
                        <Link to="/cart">Корзина</Link>
                    </Menu.Item>
                    <Menu.Item key="5">
                        <Link to="/user">Информация об аккаунте</Link>
                    </Menu.Item>
                    <Menu.Item key="6">
                        <Link to="/user/signup">Регистрация</Link>
                    </Menu.Item>
                    <Menu.Item key="7">
                        <Link to="/user/authorize">Авторизация</Link>
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout>
                <Content style={{margin: "24px 16px 0",}}>
                    <Routes>
                        <Route index element={<ProductListPage/>}/>
                        <Route path="/products/add" element={<AddProductPage/>}/>
                        <Route path="/products/edit" element={<EditProductPage/>}/>
                        <Route path="/cart" element={<CartPage/>}/>
                        <Route path="/user" element={<UserProfilePage/>}/>
                        <Route path="/user/signup" element={<RegisterPage/>}/>
                        <Route path="/user/authorize" element={<LoginPage/>}/>
                        <Route path="*" element={<NotFoundPage/>}/>
                    </Routes>
                </Content>
                <Footer style={{textAlign: "center",}}>
                    Ant Design ©2023 Created by Ant UED
                </Footer>
            </Layout>
        </Layout>
    );
};

export default App;