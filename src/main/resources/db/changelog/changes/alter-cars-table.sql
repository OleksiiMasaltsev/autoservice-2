-- liquibase formatted sql
-- changeset <masaltsev>:<alter-cars-table>

alter table if exists cars add constraint fk_cars_owners foreign key (owner_id) references owners

-- rollback ALTER TABLE cars;