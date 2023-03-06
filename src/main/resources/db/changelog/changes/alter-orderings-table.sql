-- liquibase formatted sql
-- changeset <masaltsev>:<alter-orderings-table>

alter table if exists orderings add constraint FKdsqa0gtm2bl590ft95bhxle0f foreign key (car_id) references cars;
alter table if exists orderings add constraint FK20puyj5yr9o0hr88in0yq01dx foreign key (owner_id) references owners;

-- rollback ALTER TABLE orderings;