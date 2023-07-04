export const AddProduct = () => {

    const add = () => {
        alert('Товар создан')
    }

    return (
        <>
            <h3>Введите название товара</h3>
            <input className="input-field" type="String"/>
            <h3>Введите цену товара</h3>
            <input className="input-field" type="number"/>
            <h3>Введите количество добавляемого товара</h3>
            <input className="input-field" type="String"/>
            <h3>Введите URL фотографии добавляемого товара</h3>
            <input className="input-field" type="String"/>
            <p>
                <button onClick={add}>
                Добавить
                </button>
            </p>
        </>
    );
}