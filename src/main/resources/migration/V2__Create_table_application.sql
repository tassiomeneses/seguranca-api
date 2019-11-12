create sequence security.application_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table if not exists security.tb_application
(
	id bigint not null default nextval('security.application_id_seq'::regclass)
		constraint tb_application_pkey
			primary key,
	initials varchar(255) not null
        constraint application_unique_initials
            unique,
	name varchar(255) not null
		constraint application_unique_name
			unique
);

alter table security.tb_application
    owner to postgres;

alter sequence security.application_id_seq
    owner to postgres;