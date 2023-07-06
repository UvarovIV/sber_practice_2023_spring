import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Card} from "antd";
import UserService from "../services/userService";

const {Meta} = Card;

const UserProfilePage = () => {
    const user = JSON.parse(localStorage.getItem("user"));

    const dispatch = useDispatch();

    useEffect(() => {
        UserService.getUser(user.id, dispatch);
    }, []);

    return (
        <div>
            <h2>Профиль пользователя</h2>
            <Card>
                <Meta
                    title="Выбранный пользователь"
                    description={`Имя: ${user.username} | Email: ${
                        user.email
                    } | ID: ${user.id}`}
                />
            </Card>
        </div>
    );
};

export default UserProfilePage;