import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Card} from "antd";
import UserService from "../services/userService";

const {Meta} = Card;

const UserProfile = () => {
    const userId = useSelector((state) => state.users.user.id);
    const user = useSelector((state) => state.users.user);
    const dispatch = useDispatch();

    useEffect(() => {
        UserService.getUser(userId, dispatch);
    }, []);

    return (
        <div>
            <h2>Профиль пользователя</h2>
            <Card>
                <Meta
                    title="Выбранный пользователь"
                    description={`Имя: ${user.name} | Email: ${
                        user.email
                    } | ID: ${user.id}`}
                />
            </Card>
        </div>
    );
};

export default UserProfile;