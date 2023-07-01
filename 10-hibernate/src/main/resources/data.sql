/* Вставка клиентов */
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Игорь', 'No211', '12345', 'No211@gmail.com');
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Сергей', 'LordOfTheHouse', '12345', 'LordOfTheHouse@gmail.com');
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Андрей', 'andrey', '12345', 'andrey@gmail.com');
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Артем', 'artem', '12345', 'artem@gmail.com');
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Марина', 'marina', '12345', 'marina@gmail.com');
insert into products_uvarov_iv.clients (name, login, password, email)
values ('Александра', 'alexandra', '12345', 'alexandra@gmail.com');

/*Вставка продуктов*/
insert into products_uvarov_iv.products (name, price, amount)
values ('Яблоко', 50.0, 20);
insert into products_uvarov_iv.products (name, price, amount)
values ('Апельсин', 70.0, 20);
insert into products_uvarov_iv.products (name, price, amount)
values ('Кофе', 500.0, 10);
insert into products_uvarov_iv.products (name, price, amount)
values ('Арбуз', 110.0, 5);
insert into products_uvarov_iv.products (name, price, amount)
values ('Ананас', 130.0, 10);
insert into products_uvarov_iv.products (name, price, amount)
values ('Капуста', 75.0, 20);
insert into products_uvarov_iv.products (name, price, amount)
values ('Кабачок', 110.0, 20);
insert into products_uvarov_iv.products (name, price, amount)
values ('Мясо', 300.0, 10);

/*Вставка продуктов в корзины*/
/*Первый покупатель всё купит нормально*/
/*У второго покупателя превышено количество товаров*/
/*Третьему покупателю не хватит денег*/
insert into products_uvarov_iv.products_carts (client_id, product_id, amount)
values (1, 1, 3);
insert into products_uvarov_iv.products_carts (client_id, product_id, amount)
values (1, 2, 5);
insert into products_uvarov_iv.products_carts (client_id, product_id, amount)
values (2, 4, 6);
insert into products_uvarov_iv.products_carts (client_id, product_id, amount)
values (3, 3, 10);
insert into products_uvarov_iv.products_carts (client_id, product_id, amount)
values (3, 6, 10);