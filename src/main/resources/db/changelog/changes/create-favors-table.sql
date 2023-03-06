-- liquibase formatted sql
-- changeset <masaltsev>:<create-favors-table>

create table favors
(
    id          bigint not null,
    price       numeric(38, 2),
    status      varchar(255),
    ordering_id bigint,
    worker_id   bigint,
    primary key (id)
)

-- rollback DROP TABLE favors;
