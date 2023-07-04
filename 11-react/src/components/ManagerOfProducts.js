export const ManagerOfProducts = () => {

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

    const change = () => {
        alert('Переход на страницу редактирования')
    }

    const drop = () => {
        alert('Товар удален')
    }

    return (
        products.map(product => {

            return (
                <div key={product.id} className={"manager-item"}>
                    <p>
                        <img
                            src={product.imageUrl}
                            alt={'Фото ' + product.name}
                        />
                    </p>
                    <div className={"product-title-cart"}>{product.name}</div>
                    <p>
                        <button onClick={change}>Редактировать</button>
                    </p>
                    <p>
                        <button onClick={drop}>Удалить</button>
                    </p>

                </div>

            );
        })
    );

}