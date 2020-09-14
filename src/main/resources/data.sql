insert into role(name) values('EMPLOYEE'), ('ADMINISTRATOR');

insert into application_user(username, password) values ('danjak', '$2a$12$pX0pW889L4/Yr3LlFnVPpurn8Tk6vaAhvO5iuHjkRwUEh.7pjl3au');

insert into application_user_roles(application_user_id, roles_id) values (1, 1);

insert into entry(check_in, check_out, confirmed, application_user_id) VALUES ('20201212', '20201213', false, 1);