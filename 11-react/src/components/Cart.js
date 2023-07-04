import {useState} from "react";

export const Cart = () => {

    const isEdit = true;

    let productsInCarts = [
        {
            id: 1, name: "Кофе",
            price: 500, amount: 1,
            imageUrl: 'https://i.imgur.com/kuJAwpO.jpg'
        },
        {
            id: 3, name: "Мохито",
            price: 350, amount: 3,
            imageUrl: 'https://i.imgur.com/pCKmRvE.jpg'
        },
    ]

    const [list, setList] = useState(productsInCarts);

    const addItem = (item) => {
        setList((prevList) => [...prevList, item]);
    };

    const removeItem = (id) => {
        setList((prevList) => prevList.filter((item) => item.id !== id))
    };

    const handleInputChange = (e, productIndex) => {
        const {value} = e.target;

        if (parseInt(value, 10) > 0) {
            setList((prevList) =>
                prevList.map((product, index) => {
                    if (index === productIndex) {
                        return {
                            ...product,
                            amount: parseInt(value, 10),
                        };
                    }
                    return product;
                })
            );
        }
    };

    return (
        <div>
            {list.map((product, index) => (
                <div key={product.id} className={"cart-item"}>
                    <p>
                        <img src={product.imageUrl} alt={`Фото ${product.name}`}/>
                    </p>
                    <div className={"product-title-cart"}>{product.name}</div>
                    <div className={"product-amount-cart"}>
                        Количество:
                        {isEdit ? (
                            <input
                                type="number"
                                value={product.amount}
                                onChange={(e) => handleInputChange(e, index)}
                            />
                        ) : (
                            <div>{product.amount}</div>
                        )}
                    </div>
                    <div className={"product-title-cart"}>
                        {product.price * product.amount} руб.
                    </div>
                    <p>
                        <button onClick={() => removeItem(product.id)}>Удалить</button>
                    </p>
                </div>
            ))}
        </div>
    );
}

export default Cart;
