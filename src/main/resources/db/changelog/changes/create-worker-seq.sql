-- liquibase formatted sql
-- changeset <masaltsev>:<create-create sequence worker_seq start with 1 increment by 1-seq>

create sequence worker_seq start with 1 increment by 1

-- rollback DROP SEQUENCE worker_seq;