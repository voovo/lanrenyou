drop table if exists tb_index_banner;

/*==============================================================*/
/* Table: tb_index_banner                                       */
/*==============================================================*/
create table tb_index_banner
(
   id                   int(11) not null auto_increment,
   alt					varchar(128) comment 'Í¼Æ¬alt',
   pic_url				varchar(256) comment 'Í¼Æ¬Á´½Ó',
   link_url				varchar(256) comment 'Banner³¬Á´½Ó',
   sort					tinyint(2) default 0 comment 'ÅÅÐò',
   is_del				tinyint(2) default 0 commit 'ÊÇ·ñÉ¾³ý',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_index_banner comment 'Ê×Ò³Banner';