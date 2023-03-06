-- liquibase formatted sql
-- changeset <masaltsev>:<create-car-seq>

create sequence car_seq start with 1 increment by 1

-- rollback DROP SEQUENCE car_seq;