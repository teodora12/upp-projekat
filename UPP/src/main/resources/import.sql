insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('ruma','srbija','user1@yahoo.com',false,'peric','mika','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'user1@yahoo.com');

insert into authority(id,name) values (1,'ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'USER_ROLE');

insert into scientific_field (id, name) values (1, 'knjizevnost');

insert into user_scientific_fields (user_id, scientific_field_id) values (1,1);

insert into user_authority(user_id, authority_id) VALUES (1,1);