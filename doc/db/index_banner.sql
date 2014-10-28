drop table if exists tb_index_banner;

/*==============================================================*/
/* Table: tb_index_banner                                       */
/*==============================================================*/
create table tb_index_banner
(
   id                   int(11) not null auto_increment,
   alt					varchar(128) comment 'ͼƬalt',
   pic_url				varchar(256) comment 'ͼƬ����',
   link_url				varchar(256) comment 'Banner������',
   sort					tinyint(2) default 0 comment '����',
   is_del				tinyint(2) default 0 commit '�Ƿ�ɾ��',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_index_banner comment '��ҳBanner';