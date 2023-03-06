-- liquibase formatted sql
-- changeset <masaltsev>:<create-create sequence owner_seq start with 1 increment by 1-seq>

create sequence owner_seq start with 1 increment by 1

-- rollback DROP SEQUENCE owner_seq;