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

export const Products = () => {

    const addToCart = () => {
        alert('Товар добавлен в корзину')
    }

    return (
        products.map(product => {
            return (
                <div key={product.id} className={"product-item"}>
                    <div className={"product-title"}>{product.name}</div>
                    <p><img
                        src={product.imageUrl}
                        alt={'Фото ' + product.name}
                    /></p>
                    <div className={"product-amount"}>В наличии: {product.amount} шт.</div>
                    <div className={"product-price"}>{product.price} руб.</div>
                    <p>
                        <button onClick={addToCart}>Купить</button>
                    </p>
                </div>
            );
        })
    );


}