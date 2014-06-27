drop table if exists tb_admin;

drop table if exists tb_admin_log;

drop table if exists tb_dict_city;

drop table if exists tb_power_item;

drop table if exists tb_private_letter;

drop table if exists tb_role;

drop table if exists tb_role_power;

drop table if exists tb_travel_collect;

drop table if exists tb_travel_content;

drop table if exists tb_travel_info;

drop table if exists tb_travel_info_stat;

drop table if exists tb_travel_like;

drop table if exists tb_travel_share;

drop table if exists tb_travel_visit_log;

drop table if exists tb_user_follow;

drop table if exists tb_user_info;

drop table if exists tb_user_planner;

/*==============================================================*/
/* Table: tb_admin                                              */
/*==============================================================*/
create table tb_admin
(
   id                   int(11) not null auto_increment,
   name                 varchar(128) not null comment '�û���',
   password             varchar(64) not null comment '����',
   role_id              int(11) not null comment '��ɫID',
   last_login_time      datetime comment '�ϴε�¼ʱ��',
   login_num            int(11) not null default 0 comment '��¼����',
   status               tinyint(6) comment '״̬[0:ͣ��, 1:����]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_admin comment 'ϵͳ����Ա';

/*==============================================================*/
/* Table: tb_admin_log                                          */
/*==============================================================*/
create table tb_admin_log
(
   id                   int(11) not null auto_increment,
   admin_id             int(11) not null comment '����ԱID',
   oper_type            tinyint(6) not null comment '��������',
   oper_context         varchar(512),
   oper_time            timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   oper_ip              varchar(64),
   primary key (id)
);

alter table tb_admin_log comment '����Ա������־';

/*==============================================================*/
/* Table: tb_dict_city                                          */
/*==============================================================*/
create table tb_dict_city
(
   id                   int(11) not null auto_increment comment '����ID',
   code                 int(11) not null comment '����',
   name                 varchar(128) not null comment '����',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_dict_city comment '�����ֵ��';

/*==============================================================*/
/* Table: tb_power_item                                         */
/*==============================================================*/
create table tb_power_item
(
   id                   int(11) not null auto_increment,
   p_id                 int(11) not null comment '����ID',
   level                tinyint(6) not null comment 'Ȩ�޼���',
   name                 varchar(128) not null comment '����',
   url                  varchar(512) not null comment 'URL��֧������',
   status               tinyint(6) comment '״̬[0:ͣ��, 1:����]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_power_item comment 'Ȩ����';

/*==============================================================*/
/* Table: tb_private_letter                                     */
/*==============================================================*/
create table tb_private_letter
(
   id                   int(11) not null auto_increment comment '����ID',
   sender_uid           int(11) not null comment '������',
   receiver_uid         int(11) not null comment '������',
   context              varchar(512) not null comment '����',
   has_read             tinyint(6) default 0 comment '�Ƿ��Ѷ�[0:δ��; 1:�Ѷ�]',
   has_reply            tinyint(6) default 0 comment '�Ƿ�ظ�[0:δ�ظ�; 1:�ѻظ�]',
   is_deleted           tinyint(6) default 0 comment '�Ƿ�ɾ��[0:��; 1:��]',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_private_letter comment '˽��';

/*==============================================================*/
/* Table: tb_role                                               */
/*==============================================================*/
create table tb_role
(
   id                   int(11) not null auto_increment,
   name                 varchar(128) not null comment '��ɫ����',
   status               tinyint(6) not null comment '״̬[0:ͣ��, 1:����]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_role comment '��ɫ��';

/*==============================================================*/
/* Table: tb_role_power                                         */
/*==============================================================*/
create table tb_role_power
(
   id                   int(11) not null auto_increment,
   role_id              int(11) not null comment '��ɫID',
   power_id             int(11) not null comment 'Ȩ��ID',
   create_uid           int(11),
   create_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_ip            varchar(64),
   primary key (id)
);

alter table tb_role_power comment '��ɫȨ�޶�Ӧ��';

/*==============================================================*/
/* Table: tb_travel_collect                                     */
/*==============================================================*/
create table tb_travel_collect
(
   id                   int(11) not null auto_increment comment '����ID',
   uid                  int(11) not null comment '�û�UID',
   tid                  int(11) not null comment '�μ�ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_collect comment '�μ��ղر�';

/*==============================================================*/
/* Table: tb_travel_content                                     */
/*==============================================================*/
create table tb_travel_content
(
   id                   int(11) not null auto_increment comment '����ID',
   tid                  int(11) not null comment '�μ�ID',
   travel_date          datetime,
   content              text not null comment '��ϸ����',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_content comment '�μ������';

/*==============================================================*/
/* Table: tb_travel_info                                        */
/*==============================================================*/
create table tb_travel_info
(
   id                   int(11) not null auto_increment comment '����ID',
   city                 int(11) not null comment '����',
   planner_uid          int(11) not null comment '�߻�ʦID',
   title                varchar(128) not null comment '����',
   is_elite             tinyint(6) default 0 comment '�Ƿ񾫻�[0:����; 1:��]',
   is_top               tinyint(6) default 0 comment '�Ƿ��ö�[0:��; 1:��]',
   status               tinyint(6) comment '״̬[1:�����; 2:���ͨ��; 3:��˲�ͨ��; -1:ɾ��]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_travel_info comment '�μ���Ϣ��';

/*==============================================================*/
/* Table: tb_travel_info_stat                                   */
/*==============================================================*/
create table tb_travel_info_stat
(
   id                   int(11) not null auto_increment comment '����ID',
   tid                  int(11) not null comment '�μ�ID',
   view_cnt             int(11) not null comment '�����',
   like_cnt             int(11) default 0 comment 'ϲ����',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_info_stat comment '�μ�ͳ�Ʊ�';

/*==============================================================*/
/* Table: tb_travel_like                                        */
/*==============================================================*/
create table tb_travel_like
(
   id                   int(11) not null auto_increment comment '����ID',
   tid                  int(11) not null comment '�μ�ID',
   uid                  int(11) not null comment '�û�ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'ʱ���',
   primary key (id)
);

alter table tb_travel_like comment '�μ�ϲ����';

/*==============================================================*/
/* Table: tb_travel_share                                       */
/*==============================================================*/
create table tb_travel_share
(
   id                   int(11) not null auto_increment,
   uid                  int(11) not null comment '������ID',
   tid                  int(11) not null comment '�μ�ID',
   share_type           tinyint(6) comment '��������',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '����ʱ��',
   primary key (id)
);

alter table tb_travel_share comment '�μǷ����';

/*==============================================================*/
/* Table: tb_travel_visit_log                                   */
/*==============================================================*/
create table tb_travel_visit_log
(
   id                   int(11) not null auto_increment comment '����ID',
   tid                  int(11) not null comment '�μ�ID',
   uid                  int(11) not null comment '�û�ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_visit_log comment '�μ������־��';

/*==============================================================*/
/* Table: tb_user_follow                                        */
/*==============================================================*/
create table tb_user_follow
(
   id                   int(11) not null auto_increment comment '����ID',
   fans_uid             int(11) not null comment '��ע��ID',
   star_uid             int(11) not null comment '����ע��ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'ʱ���',
   primary key (id)
);

alter table tb_user_follow comment '��ע��';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
create table tb_user_info
(
   id                   int(11) not null auto_increment comment '����ID',
   name                 varchar(128) not null default '' comment '��¼�û���',
   email                varchar(128) comment '����',
   user_pass            varchar(128) not null default '' comment '����',
   history_passwd       varchar(128) comment '��ʷ����',
   avatar               varchar(128) comment 'ͷ��',
   nickname             varchar(128) comment '�ǳ�',
   user_type            tinyint(6) not null default 0 comment '�û�����[0:��ͨ�û�; 1:�滮ʦ]',
   weibo_name           varchar(128) comment '΢���˺�',
   weibo_url            varchar(256) comment '΢������',
   wechat_name          varchar(128) comment '΢���˺�',
   user_intro           varchar(1024),
   status               tinyint(6) comment '״̬',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_user_info comment '�û���Ϣ��';

/*==============================================================*/
/* Table: tb_user_planner                                       */
/*==============================================================*/
create table tb_user_planner
(
   id                   int(11) not null auto_increment comment '����ID',
   uid                  int(11) not null comment '�û�ID',
   target_city          varchar(256) comment '�ɲ߻�����',
   price                numeric(10,2) comment '�߻��۸�',
   charge_mode          tinyint(6) comment '�շ�ģʽ[1:����; 2:����; 3:����;]',
   status               tinyint(6) comment '״̬',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_user_planner comment '�߻�ʦ';





INSERT INTO tb_dict_city (CODE,NAME) VALUES (1001,'ŦԼNYC���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1002,'��ɼ�LA���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1003,'�ɽ�ɽSan Francisco���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1004,'�������ҹ�԰National Parks');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1005,'����ͼSeattle���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1006,'����˹��Alaska');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1007,'��������Cruise');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1008,'������Orlando���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1009,'��������Florida');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1010,'����С��Small Towns');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1011,'��������Bay Area���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1012,'֥�Ӹ�Chicago���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1013,'����Other');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1014,'��ʿ��Boston���ܱ�');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1015,'�Լ��Σ�����һ�Ź�·Pacific Coast Highway');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1016,'�������Puerto Rico');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1017,'ʥ���Ǹ�San Diego���ܱ�');