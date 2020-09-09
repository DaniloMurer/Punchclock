insert into role(name) values('EMPLOYEE'), ('ADMINISTRATOR');

insert into application_user(username, password) values ('danjak', '$2y$12$SqReRCe/HoeoJHqaVjoAqu0eZqwo4TuAMkg0RvadWBPMJHV33wcoC');

insert into application_user_roles(application_user_id, roles_id) values (1, 1);

insert into entry(check_in, check_out, confirmed, application_user_id) VALUES ('20201212', '20201213', false, 1);