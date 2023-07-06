/* Вставка клиентов */
insert into uvaroviv.users (username, password, email)
values ('No211', '12345', 'No211@gmail.com');
insert into uvaroviv.users (username, password, email)
values ('LordOfTheHouse', '12345', 'LordOfTheHouse@gmail.com');
insert into uvaroviv.users (username, password, email)
values ('andrey', '12345', 'andrey@gmail.com');
insert into uvaroviv.users (username, password, email)
values ('artem', '12345', 'artem@gmail.com');
insert into uvaroviv.users (username, password, email)
values ('marina', '12345', 'marina@gmail.com');
insert into uvaroviv.users (username, password, email)
values ('alexandra', '12345', 'alexandra@gmail.com');

/*Вставка продуктов*/
insert into uvaroviv.products (name, price, amount)
values ('Яблоко', 50.0, 20);
insert into uvaroviv.products (name, price, amount)
values ('Апельсин', 70.0, 20);
insert into uvaroviv.products (name, price, amount)
values ('Кофе', 500.0, 10);
insert into uvaroviv.products (name, price, amount)
values ('Арбуз', 110.0, 5);
insert into uvaroviv.products (name, price, amount)
values ('Ананас', 130.0, 10);
insert into uvaroviv.products (name, price, amount)
values ('Капуста', 75.0, 20);
insert into uvaroviv.products (name, price, amount)
values ('Кабачок', 110.0, 20);
insert into uvaroviv.products (name, price, amount)
values ('Мясо', 300.0, 10);

/*Вставка продуктов в корзины*/
/*Первый покупатель всё купит нормально*/
/*У второго покупателя превышено количество товаров*/
/*Третьему покупателю не хватит денег*/
insert into uvaroviv.products_carts (client_id, product_id, amount)
values (1, 1, 3);
insert into uvaroviv.products_carts (client_id, product_id, amount)
values (1, 2, 5);
insert into uvaroviv.products_carts (client_id, product_id, amount)
values (2, 4, 6);
insert into uvaroviv.products_carts (client_id, product_id, amount)
values (3, 3, 10);
insert into uvaroviv.products_carts (client_id, product_id, amount)
values (3, 6, 10);

insert into uvaroviv.roles (name)
values ('ROLE_USER');
insert into uvaroviv.roles (name)
values ('ROLE_ADMIN');

insert into uvaroviv.user_roles
values (1, 2);
insert into uvaroviv.user_roles
values (2, 1);
insert into uvaroviv.user_roles
values (3, 1);
insert into uvaroviv.user_roles
values (4, 1);
insert into uvaroviv.user_roles
values (5, 1);
insert into uvaroviv.user_roles
values (6, 1);