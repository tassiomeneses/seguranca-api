
create unique index user_unique_cpf
    on security.tb_user (cpf);

alter table security.tb_user
    add constraint user_unique_cpf
    unique using index user_unique_cpf;