-- liquibase formatted sql
-- changeset <masaltsev>:<alter-cars-table>

alter table if exists cars add constraint FKhcsx2hgskre1qwetp67r7qfr foreign key (owner_id) references owners

-- rollback ALTER TABLE cars;