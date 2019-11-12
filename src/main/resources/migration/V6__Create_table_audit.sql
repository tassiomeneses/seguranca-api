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

create table if not exists audit.tb_application_aud
(
	id bigint not null,
	rev bigint not null
		constraint application_aud_rev_fk
			references audit.tb_info_aud match simple
                on update no action on delete no action,
	revtype smallint,
	initials varchar(255),
	name varchar(255),
	constraint tb_application_aud_pkey
		primary key (id, rev)
);

alter table audit.tb_application_aud
    owner to postgres;

alter table audit.tb_company_type_aud owner to postgres;

create table if not exists audit.tb_control_profile_aud
(
	id bigint not null,
	rev bigint not null
		constraint control_profile_aud_rev_fk
			references audit.tb_info_aud match simple
                on update no action on delete no action,
	revtype smallint,
	id_application bigint,
	id_profile bigint,
	id_user bigint,
	constraint tb_control_profile_aud_pkey
		primary key (id, rev)
);

alter table audit.tb_control_profile_aud owner to postgres;


create table if not exists audit.tb_profile_aud
(
	id bigint not null,
	rev bigint not null
		constraint profile_aud_rev_fk
			references audit.tb_info_aud match simple
                on update no action on delete no action,
	revtype smallint,
	name varchar(255),
	key varchar(255),
	constraint tb_profile_aud_pkey
		primary key (id, rev)
);

alter table audit.tb_profile_aud owner to postgres;


create table if not exists audit.tb_user_aud
(
	id bigint not null,
	rev bigint not null
		constraint user_aud_rev_fk
			references audit.tb_info_aud match simple
                on update no action on delete no action,
	revtype smallint,
	active boolean,
	cpf varchar(255),
	email varchar(255),
	last_access timestamp,
	login varchar(255),
	name varchar(255),
	password varchar(255),
	gender varchar(255),
	birthPlace varchar(255),
	nationality varchar(255),
	birthDate date
	constraint tb_user_aud_pkey
		primary key (id, rev)
);

alter table audit.tb_user_aud
    owner to postgres;

alter sequence audit.info_id_seq
    owner to postgres;