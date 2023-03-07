-- liquibase formatted sql
-- changeset <masaltsev>:<alter-orderings-table>

alter table if exists orderings add constraint fk_orderings_cars foreign key (car_id) references cars;
alter table if exists orderings add constraint fk_orderings_owners foreign key (owner_id) references owners;

-- rollback ALTER TABLE orderings;