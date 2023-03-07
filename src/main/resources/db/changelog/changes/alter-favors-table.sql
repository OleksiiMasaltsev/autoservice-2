-- liquibase formatted sql
-- changeset <masaltsev>:<alter-favors-table>

alter table if exists favors add constraint fk_favors_orderings foreign key (ordering_id) references orderings;

alter table if exists favors add constraint fk_favors_workers foreign key (worker_id) references workers;

-- rollback ALTER TABLE favors;