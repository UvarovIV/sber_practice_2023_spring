/*Вставка корзин*/
insert into products_uvarov_iv.carts (promocode)
values ('');
insert into products_uvarov_iv.carts (promocode)
values ('');
insert into products_uvarov_iv.carts (promocode)
values ('');
insert into products_uvarov_iv.carts (promocode)
values ('');
insert into products_uvarov_iv.carts (promocode)
values ('');
insert into products_uvarov_iv.carts (promocode)
values ('');

/* Вставка клиентов */
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Игорь', 'No211', '12345', 'No211@gmail.com', 1);
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Сергей', 'LordOfTheHouse', '12345', 'LordOfTheHouse@gmail.com', 2);
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Андрей', 'andrey', '12345', 'andrey@gmail.com', 3);
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Артем', 'artem', '12345', 'artem@gmail.com', 4);
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Марина', 'marina', '12345', 'marina@gmail.com', 5);
insert into products_uvarov_iv.clients (name, username, password, email, cart_id)
values ('Александра', 'alexandra', '12345', 'alexandra@gmail.com', 6);

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
insert into products_uvarov_iv.products_carts (id_product, id_cart, amount)
values (1, 1, 3);
insert into products_uvarov_iv.products_carts (id_product, id_cart, amount)
values (2, 1, 5);
insert into products_uvarov_iv.products_carts (id_product, id_cart, amount)
values (4, 2, 6);
insert into products_uvarov_iv.products_carts (id_product, id_cart, amount)
values (3, 3, 10);
insert into products_uvarov_iv.products_carts (id_product, id_cart, amount)
values (6, 3, 10);