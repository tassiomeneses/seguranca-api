/*tb_applicaton*/
insert into security.tb_application (initials, name, id) values ('SEC', 'Segurança', nextval ('security.application_id_seq'));

/*tb_user*/
insert into security.tb_user
    (id,active, cpf, email, last_access, login, name, password, gender, birth_place, nationality, birth_Date) values
    ( nextval ('security.user_id_seq'), true, '111.111.111-11', 'root@root.com.br', null, 'user.root', 'Usuário Root', '$2a$10$.EW8rrDQwX2TmvF2wBAmBOkWBZhzx8NuEfzr/s/BlrArtDDU5b256',
     'M', 'FORTALEZA', 'BRASILEIRO', '1985-06-27');

/*tb_profile*/
insert into security.tb_profile (id, name, key) values ( nextval ('security.profile_id_seq'), 'Super Administrador', 'ROLE_ROOT');
insert into security.tb_profile (id, name, key) values ( nextval ('security.profile_id_seq'), 'Administrador', 'ROLE_ADMIN');
insert into security.tb_profile (id, name, key) values ( nextval ('security.profile_id_seq'), 'Editor', 'ROLE_EDITOR');
insert into security.tb_profile (id, name, key) values ( nextval ('security.profile_id_seq'), 'Visitante', 'ROLE_VISITOR');

/*tb_control_profile*/
insert into security.tb_control_profile (id, id_application, id_profile, id_user) VALUES ( nextval ('security.profile_id_seq'),
  (select id from security.tb_application where name = 'Segurança' limit 1),
  (select id from security.tb_profile where key = 'ROLE_ROOT' limit 1),
  (select id from security.tb_user where login = 'user.root' limit 1)
);
