-- liquibase formatted sql
-- changeset <masaltsev>:<alter-favors-table>

alter table if exists favors add constraint FK7ldhq6mdhxp3nd5bn59oopdo1 foreign key (ordering_id) references orderings;

alter table if exists favors add constraint FKkgxkjlqr09jx10d6ef8n5g7cl foreign key (worker_id) references workers;

-- rollback ALTER TABLE favors;