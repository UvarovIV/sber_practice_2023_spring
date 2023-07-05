import {Layout, Menu, theme} from "antd";
import React, {useState} from "react";
import {Link, BrowserRouter as Router} from "react-router-dom";
import AddProduct from "./components/AddProduct";
import EditProduct from "./components/EditProduct";
import ProductList from "./components/ProductList";
import Cart from "./components/Cart";
import UserProfile from "./components/UserProfile";

const {Content, Footer, Sider} = Layout;
const App = () => {
    const {
        token: {},
    } = theme.useToken();

    const [selectedMenuItem, setSelectedMenuItem] = useState(null);

    const handleMenuItemSelect = (menuItem) => {
        setSelectedMenuItem(menuItem);
    };

    return (
        <Layout>
            <Sider
                breakpoint="lg"
                collapsedWidth="0"
                onBreakpoint={(broken) => {
                    console.log(broken);
                }}
                onCollapse={(collapsed, type) => {
                    console.log(collapsed, type);
                }}
            >
                <div className="demo-logo-vertical"/>
                <Router>
                    <Menu theme="dark" mode="inline" defaultSelectedKeys={["1"]}>
                        <Menu.Item key="1" onClick={() => handleMenuItemSelect("addProduct")}>
                            <Link to="/products/add">Добавление товаров</Link>
                        </Menu.Item>
                        <Menu.Item key="2" onClick={() => handleMenuItemSelect("editProduct")}>
                            <Link to="/products/edit">Изменение товара</Link>
                        </Menu.Item>
                        <Menu.Item key="3" onClick={() => handleMenuItemSelect("showProducts")}>
                            <Link to="/products/show">Список товаров</Link>
                        </Menu.Item>
                        <Menu.Item key="4" onClick={() => handleMenuItemSelect("showCart")}>
                            <Link to="/cart">Корзина</Link>
                        </Menu.Item>
                        <Menu.Item key="5" onClick={() => handleMenuItemSelect("accountInformation")}>
                            <Link to="/user">Информация об аккаунте</Link>
                        </Menu.Item>
                    </Menu>
                </Router>
            </Sider>
            <Layout>
                <Content
                    style={{
                        margin: "24px 16px 0",
                    }}
                >
                    {selectedMenuItem === "showCart" ? (
                        <Cart />
                    ) : selectedMenuItem === "editProduct" ? (
                        <EditProduct />
                    ) : selectedMenuItem === "showProducts" ? (
                        <ProductList />
                    ) : selectedMenuItem === "accountInformation" ? (
                        <UserProfile />
                    ) : (
                        <AddProduct />
                    )}
                </Content>
                <Footer
                    style={{
                        textAlign: "center",
                    }}
                >
                    Ant Design ©2023 Created by Ant UED
                </Footer>
            </Layout>
        </Layout>
    );
};

export default App;