insert into role(name) values('EMPLOYEE'), ('ADMINISTRATOR');

insert into application_user(username, password) values ('danjak', '$2a$12$pX0pW889L4/Yr3LlFnVPpurn8Tk6vaAhvO5iuHjkRwUEh.7pjl3au');
insert into application_user(username, password) values ('root', '$2a$12$VXhNvMdwvwz2traBOZGAiu4bdSL3bDR/3l1ImsUzWuJQvfhTnzte.');

/* Create new Employee */
insert into application_user_roles(application_user_id, roles_id) values (1, 1);
/* Create new Administrator */
insert into application_user_roles(application_user_id, roles_id) values(2, 2);

/* Add new Employee Entry */
insert into entry(check_in, check_out, confirmed, application_user_id) VALUES ('20201212', '20201213', false, 1);
/* Add new Administrator Entry */
insert into entry(check_in, check_out, confirmed, application_user_id) VALUES ('20201214', '20201216', false, 2);