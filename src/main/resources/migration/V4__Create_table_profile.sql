create sequence security.profile_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table if not exists security.tb_profile
(
	id bigint not null default nextval('security.profile_id_seq'::regclass)
		constraint tb_profile_pkey
			primary key,
	name varchar(255) not null
		constraint profile_unique_name
			unique,
    key varchar(255) not null
		constraint profile_unique_key
			unique
);

alter table security.tb_profile owner to postgres;

alter sequence security.profile_id_seq owner to postgres;