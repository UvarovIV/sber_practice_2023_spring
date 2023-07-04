import './App.css';
import {Products} from "./components/Products";
import {Profile} from "./components/Profile";
import Cart from "./components/Cart";
import {ManagerOfProducts} from "./components/ManagerOfProducts";

import {Search} from "./components/Search";

const products = [
    {
        id: 1, name: "Кофе",
        price: 500, amount: 10,
        imageUrl: 'https://i.imgur.com/kuJAwpO.jpg'
    },
    {
        id: 2, name: "Чай",
        price: 250, amount: 15,
        imageUrl: 'https://basket-03.wb.ru/vol384/part38429/38429499/images/big/1.jpg'
    },
    {
        id: 3, name: "Мохито",
        price: 350, amount: 15,
        imageUrl: 'https://i.imgur.com/pCKmRvE.jpg'
    },
]

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
            <Products products={products}/>

            <h2>3. Корзина</h2>
            <Cart/>
            <button onClick={pay} className={"pay-button"}>Оплатить</button>

            <h2>4. Меню удаления, добавления и изменения товаров</h2>
            <ManagerOfProducts/>

            <h3>5. Строка поиска</h3>
            <Search/>
        </div>
    );
}

export default App;
