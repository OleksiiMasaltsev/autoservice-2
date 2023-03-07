-- liquibase formatted sql
-- changeset <masaltsev>:<alter-products-table>

alter table if exists products add constraint fk_products_orderings foreign key (ordering_id) references orderings

-- rollback ALTER TABLE products;