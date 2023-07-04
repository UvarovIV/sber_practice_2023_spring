export const Cart = () => {

    const isEdit = true;

    const productsInCarts = [
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

    const drop = () => {
        alert('Товар удален из корзины')
    }

    return (
        productsInCarts.map(product => {

            return (
                <div key={product.id} className={"cart-item"}>
                    <p>
                        <img
                            src={product.imageUrl}
                            alt={'Фото ' + product.name}
                        />
                    </p>
                    <div className={"product-title-cart"}>{product.name}</div>
                    <div className={"product-amount-cart"}>Количество:</div>
                    <div className={"product-title-cart"}>{isEdit ? (
                        <input type="number"
                               defaultValue={product.amount}/>
                    ) : (
                        <div>{product.amount}</div>
                    )
                    }
                    </div>
                    <div className={"product-title-cart"}>{product.price * product.amount} руб.</div>
                    <p>
                        <button onClick={drop}>Удалить</button>
                    </p>

                </div>

            );
        })
    );
}

export default Cart;
