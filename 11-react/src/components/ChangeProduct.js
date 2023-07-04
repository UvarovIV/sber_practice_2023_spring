export const ChangeProduct = () => {

    const change = () => {
        alert('Информация о товаре обновлена')
    }

    return (
        <>
            <h3>Введите название товара</h3>
            <input className="input-field" type="String"/>
            <h3>Введите цену товара</h3>
            <input className="input-field" type="number"/>
            <h3>Введите количество товара</h3>
            <input className="input-field" type="String"/>
            <h3>Введите URL фотографии товара</h3>
            <input className="input-field" type="String"/>
            <p>
                <button onClick={change}>
                    Обновить
                </button>
            </p>
        </>
    );
}