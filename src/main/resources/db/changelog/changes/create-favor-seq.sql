-- liquibase formatted sql
-- changeset <masaltsev>:<create-create sequence favor_seq start with 1 increment by 1-seq>

create sequence favor_seq start with 1 increment by 1

-- rollback DROP SEQUENCE favor_seq;