create schema if not exists products_uvarov_iv;

create table if not exists products_uvarov_iv.products
(
    id    integer generated always as identity primary key,
    name  varchar(255) not null,
    price numeric      not null,
    amount integer      not null
    );

create table if not exists products_uvarov_iv.carts
(
    id        integer generated always as identity primary key,
    promocode varchar(255)
    );

create table if not exists products_uvarov_iv.clients
(
    id       integer generated always as identity primary key,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255),
    cart_id  integer      not null
    constraint client_cart_id_fk
    references products_uvarov_iv.carts
    );

create table if not exists products_uvarov_iv.products_carts
(
    id         integer generated always as identity primary key,
    id_product integer not null
    constraint product_client_products_id_fk
    references products_uvarov_iv.products,
    id_cart    integer not null
    constraint product_client_cart_id_fk
    references products_uvarov_iv.carts,
    amount      integer not null
);