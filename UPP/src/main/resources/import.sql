insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('ruma','srbija','user1@yahoo.com',false,'peric','mika','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'user1@yahoo.com');

insert into authority(id,name) values (1,'ADMIN');

insert into user_authority(user_id, authority_id) VALUES (1,1);