-- liquibase formatted sql
-- changeset <masaltsev>:<create-create-workers_orderings-table>

create table workers_orderings
(
    worker_id   bigint not null,
    ordering_id bigint not null,
    primary key (worker_id, ordering_id)
)

-- rollback DROP TABLE workers_orderings;
