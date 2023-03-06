-- liquibase formatted sql
-- changeset <masaltsev>:<create-workers-table>

create table workers
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
)

-- rollback DROP TABLE workers;
