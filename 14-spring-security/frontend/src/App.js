import {Layout, Menu} from "antd";
import React, {useState} from "react";
import {Link, Route, Routes, useLocation} from "react-router-dom";
import CartPage from "./pages/CartPage";
import EditProductPage from "./pages/EditProductPage";
import ProductListPage from "./pages/ProductListPage";
import UserProfilePage from "./pages/UserProfilePage";
import AddProductPage from "./pages/AddProductPage";
import {NotFoundPage} from "./pages/NotFoundPage";
import RegisterPage from "./pages/RegistrationPage";
import LoginPage from "./pages/LoginPage";
import {useDispatch, useSelector} from "react-redux";

import authService from "./services/auth.service";
import {logout} from "./slices/authSlice";

const {Content, Footer, Sider, Header} = Layout;
const App = () => {
    const [selectedMenuKey, setSelectedMenuKey] = useState("2"); // Состояние для хранения выбранного пункта меню

    const dispatch = useDispatch();

    const location = useLocation();

    const handleMenuSelect = (key) => {
        setSelectedMenuKey(key);
    };

    const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
    const user = useSelector((state) => state.auth.user);

    const handleLogOut = () => {
        dispatch(logout(user))
        authService.logout()
    }

    return (
        <Layout>
            <Header className="header">
                <div className="logo"/>
                <Menu
                    theme="dark"
                    mode="horizontal"
                    selectedKeys={[selectedMenuKey]} // Устанавливаем выбранный пункт меню
                    onSelect={handleMenuSelect} // Обработчик события выбора пункта меню
                >
                    <Menu.Item key="1">
                        <Link to="/">Товары</Link>
                    </Menu.Item>
                    {isLoggedIn && user !== null ?
                        (<>
                            <Menu.Item key="2">
                                <Link to="/cart">Корзина</Link>
                            </Menu.Item>
                            <Menu.Item key="3">
                                <Link to="/user">Профиль</Link>
                            </Menu.Item>
                            <Menu.Item key="4">
                                <Link to="/api/auth/signin" onClick={handleLogOut}>Выйти</Link>
                            </Menu.Item>
                        </>) :
                        (<>
                            <Menu.Item key="2">
                                <Link to="/api/auth/signup">Регистрация</Link>
                            </Menu.Item>
                            <Menu.Item key="3">
                                <Link to="/api/auth/signin">Авторизация</Link>
                            </Menu.Item>
                        </>)}
                </Menu>
            </Header>
            <Layout>
                <Sider>
                    <div className="demo-logo-vertical"/>
                    <Menu theme="dark" mode="inline" >
                        {(location.pathname === "/" || location.pathname === "/products/add" || location.pathname === "/products/edit") && [ // Пункты меню для страницы "Товары"
                            <>
                                <Menu.Item key="1">
                                    <Link to="/">Список товаров</Link>
                                </Menu.Item>
                                {isLoggedIn && user.roles.includes("ROLE_ADMIN") && user !== null ? (
                                    <>
                                        <Menu.Item key="2">
                                            <Link to="/products/add">Добавление товаров</Link>
                                        </Menu.Item>
                                        <Menu.Item key="3">
                                            <Link to="/products/edit">Изменение товара</Link>
                                        </Menu.Item>
                                    </>) : (<></>)
                                }

                            </>
                        ]}
                        {location.pathname === "/cart" && ( // Пункты меню для страницы "Корзина"
                            <Menu.Item key="4">
                                <Link to="/cart">Корзина</Link>
                            </Menu.Item>
                        )}
                        {location.pathname === "/user" && ( // Пункты меню для страницы "Профиль"
                            <Menu.Item key="5">
                                <Link to="/user">Информация об аккаунте</Link>
                            </Menu.Item>
                        )}
                        {location.pathname === "/api/auth/signup" && ( // Пункты меню для страницы "Регистрация"
                            <Menu.Item key="6">
                                <Link to="/api/auth/signup">Регистрация</Link>
                            </Menu.Item>
                        )}
                        {location.pathname === "/api/auth/signin" && ( // Пункты меню для страницы "Авторизация"
                            <Menu.Item key="7">
                                <Link to="/api/auth/signin">Авторизация</Link>
                            </Menu.Item>
                        )}
                    </Menu>
                </Sider>
                <Layout>
                    <Content style={{margin: "24px 16px 0"}}>
                        <Routes>
                            <Route index element={<ProductListPage/>}/>
                            <Route path="/products/add" element={<AddProductPage/>}/>
                            <Route path="/products/edit" element={<EditProductPage/>}/>
                            <Route path="/cart" element={<CartPage/>}/>
                            <Route path="/user" element={<UserProfilePage/>}/>
                            <Route path="/api/auth/signup" element={<RegisterPage/>}/>
                            <Route path="/api/auth/signin" element={<LoginPage/>}/>
                            <Route path="*" element={<NotFoundPage/>}/>
                        </Routes>
                    </Content>
                    <Footer style={{textAlign: "center"}}>
                        Ant Design ©2023 Created by Ant UED
                    </Footer>
                </Layout>
            </Layout>
        </Layout>
    );
};

export default App;