# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table joueur (
  id                        numeric(19) identity(1,1) not null,
  nom                       varchar(255),
  nom2345                   varchar(255),
  constraint pk_joueur primary key (id))
;

create table journee_top14 (
  id                        numeric(19) identity(1,1) not null,
  numero                    integer,
  constraint pk_journee_top14 primary key (id))
;

create table match_c (
  id                        numeric(19) identity(1,1) not null,
  journee_top14_id          numeric(19) not null,
  domicile                  varchar(255),
  exterieur                 varchar(255),
  date                      datetime,
  cote_domicile             float(32),
  cote_exterieur            float(32),
  bonus_exterieur           varchar(255),
  bonus_domicile            varchar(255),
  resultat                  varchar(255),
  gagnant                   varchar(255),
  perdant                   varchar(255),
  nul                       bit default 0,
  constraint pk_match_c primary key (id))
;

create table prono_journee (
  id                        numeric(19) identity(1,1) not null,
  journee_id                numeric(19),
  joueur_id                 numeric(19),
  id_journee_manual         numeric(19),
  id_joueur_manual          numeric(19),
  constraint pk_prono_journee primary key (id))
;

create table prono_match (
  id                        numeric(19) identity(1,1) not null,
  prono_match_id            numeric(19) not null,
  match_id                  numeric(19),
  match_pronostique_id      numeric(19),
  victoire_adomicile        bit default 0,
  gagnant                   varchar(255),
  perdant                   varchar(255),
  bonus_offensif            bit default 0,
  bonus_defensif            bit default 0,
  match_nul                 bit default 0,
  constraint pk_prono_match primary key (id))
;

alter table match_c add constraint fk_match_c_journee_top14_1 foreign key (journee_top14_id) references journee_top14 (id);
create index ix_match_c_journee_top14_1 on match_c (journee_top14_id);
alter table prono_journee add constraint fk_prono_journee_journee_2 foreign key (journee_id) references journee_top14 (id);
create index ix_prono_journee_journee_2 on prono_journee (journee_id);
alter table prono_journee add constraint fk_prono_journee_joueur_3 foreign key (joueur_id) references joueur (id);
create index ix_prono_journee_joueur_3 on prono_journee (joueur_id);
alter table prono_match add constraint fk_prono_match_prono_journee_4 foreign key (prono_match_id) references prono_journee (id);
create index ix_prono_match_prono_journee_4 on prono_match (prono_match_id);
alter table prono_match add constraint fk_prono_match_matchPronostiqu_5 foreign key (match_id) references match_c (id);
create index ix_prono_match_matchPronostiqu_5 on prono_match (match_id);



# --- !Downs

drop table joueur;

drop table journee_top14;

drop table match_c;

drop table prono_journee;

drop table prono_match;

