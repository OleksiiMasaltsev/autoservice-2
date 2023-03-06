-- liquibase formatted sql
-- changeset <masaltsev>:<alter-products-table>

alter table if exists products add constraint FK43edhvi400owswpr7na89h72i foreign key (ordering_id) references orderings

-- rollback ALTER TABLE products;