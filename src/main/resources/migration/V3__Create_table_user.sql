
create sequence audit.info_id_seq
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 1
  cache 1;

create table if not exists audit.tb_info_aud
(
	id bigint not null default nextval('audit.info_id_seq'::regclass)
		constraint tb_info_aud_pkey
			primary key,
	date_operation timestamp,
	id_user bigint,
	name varchar(255),
	timestamp bigint not null
);

alter table audit.tb_info_aud
    owner to postgres;


create sequence security.user_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table if not exists security.tb_user
(
	id bigint not null default nextval('security.user_id_seq'::regclass)
		constraint tb_user_pkey
			primary key,
	active boolean not null,
	cpf varchar(255) not null,
	email varchar(255) not null
		constraint user_unique_email
			unique,
	last_access timestamp,
	login varchar(255) not null
		constraint user_unique_login
			unique,
	name varchar(255) not null,
	password varchar(255),
	gender varchar(255),
	birth_place varchar(255),
	nationality varchar(255),
	birth_date date
);

alter table security.tb_user
    owner to postgres;

alter sequence security.user_id_seq
    owner to postgres;