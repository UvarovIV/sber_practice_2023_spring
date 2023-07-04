import './App.css';
import {Products} from "./components/Products";
import {Profile} from "./components/Profile";
import Cart from "./components/Cart";
import {AddProduct} from "./components/AddProduct";
import {ManagerOfProducts} from "./components/ManagerOfProducts";
import {ChangeProduct} from "./components/ChangeProduct";
import {Search} from "./components/Search";

function App() {

    const pay = () => {
        alert('Заказ оплачен')
    }

    return (
        <div className={"main-page"}>

            <h1>React-приложение</h1>
            <h2>1. Отображение профиля пользователя</h2>
            <Profile/>

            <h2>2. Отображение списка товаров</h2>
            <Products/>

            <h2>3. Корзина</h2>
            <Cart/>
            <button onClick={pay} className={"pay-button"}>Оплатить</button>

            <h2>4. Добавление товара</h2>
            <AddProduct/>

            <h2>5. Редактирование товара</h2>
            <ChangeProduct/>

            <h2>6. Меню удаления, добавления и изменения товаров</h2>
            <ManagerOfProducts/>

            <h3>7. Строка поиска</h3>
            <Search/>
        </div>
    );
}

export default App;
