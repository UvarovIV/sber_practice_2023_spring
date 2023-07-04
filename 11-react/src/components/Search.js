export const Search = () => {

    const search = () => {
        alert('Поиск работает')
    }

    return (
        <>
            <input className="input-field" type="String"/>
            <button onClick={search} style={
                {
                    width: 80,
                    height: 34
                }
            }>Поиск</button>

        </>
    )
}