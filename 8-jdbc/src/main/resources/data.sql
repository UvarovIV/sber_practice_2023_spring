create schema if not exists products_uvarov_iv;

create table products_uvarov_iv.product
(
    id    integer generated always as identity
        primary key,
    name  varchar(255) not null,
    price numeric      not null
);

create table products_uvarov_iv.cart
(
    id        integer generated always as identity
        primary key,
    promocode varchar(255)
);

create table products_uvarov_iv.client
(
    id       integer generated always as identity,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    cart_id  integer      not null
        constraint client_cart_id_fk
            references products_uvarov_iv.cart,
    email varchar not null
);

create table products_uvarov_iv.product_client
(
    id         integer generated always as identity
        primary key,
    id_product integer not null
        constraint product_client_products_id_fk
            references products_uvarov_iv.product,
    id_cart    integer not null
        constraint product_client_cart_id_fk
            references products_uvarov_iv.cart,
    count      integer not null
);