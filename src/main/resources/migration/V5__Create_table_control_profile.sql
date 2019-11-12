create sequence security.control_profile_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table if not exists security.tb_control_profile
(
	id bigint not null  default nextval('security.control_profile_id_seq'::regclass)
		constraint tb_control_profile_pkey primary key,
	id_application bigint not null
		constraint control_profile_application_fk
			references security.tb_application (id) match simple
			    on update no action on delete no action,
	id_profile bigint not null
		constraint control_profile_profile_fk
			references security.tb_profile (id) match simple
			    on update no action on delete no action,
	id_user bigint not null
		constraint control_profile_user_fk
			references security.tb_user (id) match simple
			    on update no action on delete no action
);

alter table security.tb_control_profile
    owner to postgres;

alter sequence security.control_profile_id_seq
    owner to postgres;