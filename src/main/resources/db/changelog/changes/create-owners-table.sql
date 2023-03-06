-- liquibase formatted sql
-- changeset <masaltsev>:<create-owners-table>

create table owners
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
)

-- rollback DROP TABLE owners;
