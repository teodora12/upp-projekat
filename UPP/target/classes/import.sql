insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('novi sad','srbija','admin@yahoo.com',false,'relic','zika','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'admin@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('ruma','srbija','user1@yahoo.com',false,'peric','mika','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'user1@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('beograd','srbija','author@yahoo.com',false,'zikic','mirko','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'author@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('beograd','srbija','reviewer1@yahoo.com',false,'alagic','jugoslav','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'reviewer1@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('beograd','srbija','reviewer2@yahoo.com',false,'dekic','zoran','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'reviewer2@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('beograd','srbija','mainredactor@yahoo.com',false,'urednik','glavni','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'mainredactor@yahoo.com');
insert into user(city, country, email, is_reviewer, lastname, name, password, title,enabled,username) values ('beograd','srbija','redactor@yahoo.com',false,'urednik','urednik','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy','title',true,'redactor@yahoo.com');


insert into authority(id,name) values (1,'ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'USER_ROLE');
INSERT INTO authority (id, name) VALUES (3, 'REVIEWER');
INSERT INTO authority (id, name) VALUES (4, 'AUTHOR');
INSERT INTO authority (id, name) VALUES (5, 'MAIN_REDACTOR');
INSERT INTO authority (id, name) VALUES (6, 'REDACTOR');



insert into scientific_field (id, name) values (1, 'knjizevnost');
insert into scientific_field (id, name) values (2, 'hemija');
insert into scientific_field (id, name) values (3, 'tehnologija');
insert into scientific_field (id, name) values (4, 'elektrotehnika');
insert into scientific_field (id, name) values (5, 'fizika');


insert into user_scientific_fields (user_id, scientific_field_id) values (2,1);


insert into user_authority(user_id, authority_id) VALUES (1,1);
insert into user_authority(user_id, authority_id) VALUES (2,2);
insert into user_authority(user_id, authority_id) VALUES (3,4);
insert into user_authority(user_id, authority_id) VALUES (4,3);
insert into user_authority(user_id, authority_id) VALUES (5,3);
insert into user_authority(user_id, authority_id) VALUES (6,5);
insert into user_authority(user_id, authority_id) VALUES (7,6);


insert into magazine (id, date, is_active, title, way_of_payment, is_open_access) values (1, null ,true,'MAGAZIN','', false );
insert into magazine (id, date, is_active, title, way_of_payment, is_open_access) values (2, null ,true,'MAGAZIN2','banka', true );
insert into magazine (id, date, is_active, title, way_of_payment, is_open_access) values (3, null ,true,'casopis','banka', false );


insert into magazine_scientific_fields (magazine_id, scientific_field_id) values (1,1);
insert into magazine_scientific_fields (magazine_id, scientific_field_id) values (1,2);
insert into magazine_scientific_fields (magazine_id, scientific_field_id) values (1,3);
insert into magazine_scientific_fields (magazine_id, scientific_field_id) values (2,4);
insert into magazine_scientific_fields (magazine_id, scientific_field_id) values (2,5);


insert into magazine_users (magazine_id, users_id) values (1,4);
insert into magazine_users (magazine_id, users_id) values (1,5);
insert into magazine_users (magazine_id, users_id) values (2,4);
insert into magazine_users (magazine_id, users_id) values (2,5);
insert into magazine_users (magazine_id, users_id) values (1,6);
insert into magazine_users (magazine_id, users_id) values (1,7);
