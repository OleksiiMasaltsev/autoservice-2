-- liquibase formatted sql
-- changeset <masaltsev>:<create-cars-table>

create table cars
(
    id       bigint not null,
    brand    varchar(255),
    model    varchar(255),
    plate    varchar(255),
    year     smallint,
    owner_id bigint,
    primary key (id)
)

-- rollback DROP TABLE cars;
