import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Card, Select} from "antd";
import {setUserInfo} from "../slices/usersSlice";

const {Meta} = Card;
const {Option} = Select;

const UserProfile = () => {
    const users = useSelector((state) => state.users.usersList);
    const [selectedUser, setSelectedUser] = useState(null);
    const dispatch = useDispatch();

    const handleSetUserInfo = (userInfo) => {
        dispatch(setUserInfo(userInfo));
        setSelectedUser(userInfo);
    };

    return (
        <div>
            <h2>Профиль пользователя</h2>
            <Select
                defaultValue="Выберите пользователя"
                style={{width: 200, marginBottom: 16}}
                onChange={(userId) =>
                    handleSetUserInfo(users.find((user) => user.id === userId))
                }
            >
                {users.map((user) => (
                    <Option key={user.id} value={user.id}>
                        {user.name}
                    </Option>
                ))}
            </Select>
            {selectedUser ? (
                <Card>
                    <Meta
                        title="Выбранный пользователь"
                        description={`Имя: ${selectedUser.name} | Email: ${
                            selectedUser.email
                        } | ID: ${selectedUser.id}`}
                    />
                </Card>
            ) : (
                <Card>
                    <Meta title="Выбранный пользователь" description="Нет выбранного пользователя"/>
                </Card>
            )}


        </div>
    );
};

export default UserProfile;