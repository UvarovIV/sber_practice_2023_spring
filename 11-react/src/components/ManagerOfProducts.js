import React, {useState} from 'react';

export const ManagerOfProducts = () => {
    const [products, setProducts] = useState([]);
    const [name, setName] = useState('');
    const [price, setPrice] = useState(0);
    const [amount, setAmount] = useState(0);
    const [selectedProductId, setSelectedProductId] = useState(null);
    const [editedName, setEditedName] = useState('');
    const [editedPrice, setEditedPrice] = useState(0);
    const [editedAmount, setEditedAmount] = useState(0);
    const [isEditing, setIsEditing] = useState(false);

    const handleNameChange = (e) => {
        if (isEditing) {
            setEditedName(e.target.value);
        } else {
            setName(e.target.value);
        }
    };

    const handlePriceChange = (e) => {
        if (isEditing) {
            setEditedPrice(Number(e.target.value));
        } else {
            setPrice(Number(e.target.value));
        }
    };

    const handleAmountChange = (e) => {
        if (isEditing) {
            setEditedAmount(Number(e.target.value));
        } else {
            setAmount(Number(e.target.value));
        }
    };

    const handleAddProduct = () => {
        if (name.trim() === "" || price <= 0 || amount <= 0) {
            alert("Неправильные значения полей");
            return;
        }
        const newProduct = {
            id: Date.now(),
            name,
            price,
            amount
        };
        setProducts([...products, newProduct]);
        setName('');
        setPrice(0);
        setAmount(0);
    };

    const handleEditProduct = (id) => {
        const selectedProduct = products.find(product => product.id === id);
        if (selectedProduct) {
            setSelectedProductId(id);
            setEditedName(selectedProduct.name);
            setEditedPrice(selectedProduct.price);
            setEditedAmount(selectedProduct.amount);
            setIsEditing(true);
        }
    };

    const handleSaveEditProduct = () => {
        if (editedName.trim() === "" || editedPrice <= 0 || editedAmount <= 0) {
            alert("Неправильные значения полей");
            return;
        }
        const updatedProducts = products.map(product => {

            if (product.id === selectedProductId) {
                return {
                    ...product,
                    name: editedName,
                    price: editedPrice,
                    amount: editedAmount
                };
            }
            return product;
        });
        setProducts(updatedProducts);
        setSelectedProductId(null);
        setEditedName('');
        setEditedPrice(0);
        setEditedAmount(0);
        setIsEditing(false);
    };

    const handleDeleteProduct = (id) => {
        const updatedProducts = products.filter(product => product.id !== id);
        setProducts(updatedProducts);
        if (selectedProductId === id) {
            setSelectedProductId(null);
            setIsEditing(false); // Добавлено
        }
    };

    return (
        <div>
            <h2>Создание продукта</h2>
            <div>
                <label className="input-label">
                    Название:
                    <input className="input-field" type="text" value={name} onChange={handleNameChange}
                           disabled={isEditing}/>
                </label>
            </div>
            <div>
                <label className="input-label">
                    Цена:
                    <input className="input-field" type="number" value={price} onChange={handlePriceChange}
                           disabled={isEditing}/>
                </label>
            </div>
            <div>
                <label className="input-label">
                    Количество:
                    <input className="input-field" type="number" value={amount} onChange={handleAmountChange}
                           disabled={isEditing}/>
                </label>
            </div>
            <button className="input-button" onClick={handleAddProduct}>Создать продукт</button>

            <h2>Редактирование продукта</h2>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        <p className="input-label">{product.name} - Цена: {product.price},
                            Количество: {product.amount}</p>
                        {product.id === selectedProductId ? (
                            <>
                                <div>
                                    <label className="input-label">
                                        Название:
                                        <input
                                            className="input-field"
                                            type="text"
                                            value={editedName}
                                            onChange={handleNameChange}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label className="input-label">
                                        Цена:
                                        <input
                                            className="input-field"
                                            type="number"
                                            value={editedPrice}
                                            onChange={handlePriceChange}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label className="input-label">
                                        Количество:
                                        <input
                                            className="input-field"
                                            type="number"
                                            value={editedAmount}
                                            onChange={handleAmountChange}
                                        />
                                    </label>
                                </div>
                                <button className="input-button" onClick={handleSaveEditProduct}>Сохранить изменения
                                </button>
                            </>
                        ) : (
                            <>
                                <button className="input-button"
                                        onClick={() => handleEditProduct(product.id)}>Редактировать
                                </button>
                            </>
                        )}
                        <button className="input-button" onClick={() => handleDeleteProduct(product.id)}>Удалить
                            продукт
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};