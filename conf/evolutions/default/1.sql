# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table commentaire (
  id                        bigint auto_increment not null,
  pseudo                    varchar(255),
  texte                     varchar(255),
  constraint pk_commentaire primary key (id))
;

create table joueur (
  id                        bigint auto_increment not null,
  nom                       varchar(255),
  nfqsdsf                   varchar(255),
  constraint pk_joueur primary key (id))
;

create table journee_top14 (
  id                        bigint auto_increment not null,
  numero                    integer,
  constraint pk_journee_top14 primary key (id))
;

create table match_c (
  id                        bigint auto_increment not null,
  journee_top14_id          bigint not null,
  domicile                  varchar(255),
  exterieur                 varchar(255),
  date                      datetime,
  cote_domicile             double,
  cote_exterieur            double,
  bonus_exterieur           varchar(255),
  bonus_domicile            varchar(255),
  resultat                  varchar(255),
  gagnant                   varchar(255),
  perdant                   varchar(255),
  nul                       tinyint(1) default 0,
  constraint pk_match_c primary key (id))
;

create table prono_journee (
  id                        bigint auto_increment not null,
  journee_id                bigint,
  joueur_id                 bigint,
  id_journee_manual         bigint,
  id_joueur_manual          bigint,
  constraint pk_prono_journee primary key (id))
;

create table prono_match (
  id                        bigint auto_increment not null,
  prono_match_id            bigint not null,
  match_id                  bigint,
  match_pronostique_id      bigint,
  victoire_adomicile        tinyint(1) default 0,
  gagnant                   varchar(255),
  perdant                   varchar(255),
  bonus_offensif            tinyint(1) default 0,
  bonus_defensif            tinyint(1) default 0,
  match_nul                 tinyint(1) default 0,
  constraint pk_prono_match primary key (id))
;

alter table match_c add constraint fk_match_c_journee_top14_1 foreign key (journee_top14_id) references journee_top14 (id) on delete restrict on update restrict;
create index ix_match_c_journee_top14_1 on match_c (journee_top14_id);
alter table prono_journee add constraint fk_prono_journee_journee_2 foreign key (journee_id) references journee_top14 (id) on delete restrict on update restrict;
create index ix_prono_journee_journee_2 on prono_journee (journee_id);
alter table prono_journee add constraint fk_prono_journee_joueur_3 foreign key (joueur_id) references joueur (id) on delete restrict on update restrict;
create index ix_prono_journee_joueur_3 on prono_journee (joueur_id);
alter table prono_match add constraint fk_prono_match_prono_journee_4 foreign key (prono_match_id) references prono_journee (id) on delete restrict on update restrict;
create index ix_prono_match_prono_journee_4 on prono_match (prono_match_id);
alter table prono_match add constraint fk_prono_match_matchPronostique_5 foreign key (match_id) references match_c (id) on delete restrict on update restrict;
create index ix_prono_match_matchPronostique_5 on prono_match (match_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table commentaire;

drop table joueur;

drop table journee_top14;

drop table match_c;

drop table prono_journee;

drop table prono_match;

SET FOREIGN_KEY_CHECKS=1;

