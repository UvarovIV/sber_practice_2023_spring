const user = {
    name: 'Eminem',
    email: 'ptytytyt@mail.ru',
    imageUrl: 'https://imgur.com/PpKxMP4.jpg',
    imageSize: 90,
}
export const Profile = () => {
    return (
        <>
            <h3>{user.name}</h3>
            <h3>Email: {user.email}</h3>
            <img
                className="avatar"
                src={user.imageUrl}
                alt={'Фото ' + user.name}
                style={{
                    width: user.imageSize,
                    height: user.imageSize
                }}
            />
        </>
    );
}