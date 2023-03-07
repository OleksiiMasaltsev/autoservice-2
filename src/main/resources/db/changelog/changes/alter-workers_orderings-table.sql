-- liquibase formatted sql
-- changeset <masaltsev>:<alter-workers_orderings-table>

alter table if exists workers_orderings add constraint fk_workers_orderings_orderings foreign key (ordering_id) references orderings;
alter table if exists workers_orderings add constraint fk_workers_orderings_workers foreign key (worker_id) references workers;

-- rollback ALTER TABLE workers_orderings;