create table if not exists products_uvarov_iv.clients (id bigserial not null, name varchar(255) not null, login varchar(255) not null, password varchar(255) not null, email varchar(255) not null, primary key (id));
create table if not exists products_uvarov_iv.products (id bigserial not null, name varchar(255) not null, price numeric(38,2) not null, amount integer not null, primary key (id));
create table if not exists products_uvarov_iv.products_carts (id bigserial not null, client_id bigint not null, product_id bigint not null, amount integer not null, primary key (id));
alter table if exists products_uvarov_iv.products_carts add constraint  FKnjanbd4m9fxv8t5jcm3qoi1qt foreign key (client_id) references products_uvarov_iv.clients;
alter table if exists products_uvarov_iv.products_carts add constraint FKoypyla9kn9mxnenu0yaxau8d6 foreign key (product_id) references products_uvarov_iv.products;
