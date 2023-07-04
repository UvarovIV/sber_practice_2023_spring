export const Search = () => {

    const search = () => {
        alert('Поиск не работает')
    }

    return (
        <>
            <input className="search-field" type="String"/>
            <button className="search-button" onClick={search}>Поиск</button>
        </>
    )
}