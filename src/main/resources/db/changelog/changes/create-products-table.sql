-- liquibase formatted sql
-- changeset <masaltsev>:<create-products-table>

create table products
(
    id          bigint not null,
    name        varchar(255),
    price       numeric(38, 2),
    ordering_id bigint,
    primary key (id)
)

-- rollback DROP TABLE products;
