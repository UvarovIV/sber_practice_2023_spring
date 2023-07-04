export const Products = ({products}) => {
    const addToCart = () => {
        alert('Товар добавлен в корзину');
    };

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