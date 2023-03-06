-- liquibase formatted sql
-- changeset <masaltsev>:<create-create sequence product_seq start with 1 increment by 1-seq>

create sequence product_seq start with 1 increment by 1

-- rollback DROP SEQUENCE product_seq;