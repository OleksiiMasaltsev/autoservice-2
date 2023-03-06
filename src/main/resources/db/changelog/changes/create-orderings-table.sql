-- liquibase formatted sql
-- changeset <masaltsev>:<create-orderings-table>

create table orderings
(
    completion_time timestamp(6),
    description     varchar(255),
    price           numeric(38, 2),
    receiving_time  timestamp(6),
    status          varchar(255),
    car_id          bigint not null,
    owner_id        bigint,
    primary key (car_id)
)

-- rollback DROP TABLE orderings;
