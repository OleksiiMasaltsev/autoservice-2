-- liquibase formatted sql
-- changeset <masaltsev>:<alter-workers_orderings-table>

alter table if exists workers_orderings add constraint FKm52al921el4y9sbfrx6b9u44a foreign key (ordering_id) references orderings;
alter table if exists workers_orderings add constraint FK9pw3yy6wbr762wolybqom3ayj foreign key (worker_id) references workers;

-- rollback ALTER TABLE workers_orderings;