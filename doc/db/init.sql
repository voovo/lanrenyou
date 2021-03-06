drop table if exists tb_admin_log;

drop table if exists tb_admin_power_item;

drop table if exists tb_admin_role;

drop table if exists tb_admin_role_power;

drop table if exists tb_admin_user;

drop table if exists tb_dict_city;

drop table if exists tb_private_letter;

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
/* Table: tb_admin_log                                          */
/*==============================================================*/
create table tb_admin_log
(
   id                   int(11) not null auto_increment,
   admin_id             int(11) not null comment '管理员ID',
   oper_type            tinyint(6) not null comment '操作类型',
   oper_context         varchar(512),
   oper_time            timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   oper_ip              varchar(64),
   primary key (id)
);

alter table tb_admin_log comment '管理员操作日志';

/*==============================================================*/
/* Table: tb_admin_power_item                                   */
/*==============================================================*/
create table tb_admin_power_item
(
   id                   int(11) not null auto_increment,
   p_id                 int(11) not null comment '父级ID',
   level                tinyint(6) not null comment '权限级别',
   name                 varchar(128) not null comment '名称',
   url                  varchar(512) not null comment 'URL，支持正则',
   status               tinyint(6) comment '状态[0:停用, 1:正常]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_admin_power_item comment '权限项';

/*==============================================================*/
/* Table: tb_admin_role                                         */
/*==============================================================*/
create table tb_admin_role
(
   id                   int(11) not null auto_increment,
   name                 varchar(128) not null comment '角色名称',
   status               tinyint(6) not null comment '状态[0:停用, 1:正常]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_admin_role comment '角色表';

/*==============================================================*/
/* Table: tb_admin_role_power                                   */
/*==============================================================*/
create table tb_admin_role_power
(
   id                   int(11) not null auto_increment,
   role_id              int(11) not null comment '角色ID',
   power_id             int(11) not null comment '权限ID',
   create_uid           int(11),
   create_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_ip            varchar(64),
   primary key (id)
);

alter table tb_admin_role_power comment '角色权限对应表';

/*==============================================================*/
/* Table: tb_admin_user                                         */
/*==============================================================*/
create table tb_admin_user
(
   id                   int(11) not null auto_increment,
   name                 varchar(128) not null comment '用户名',
   password             varchar(64) not null comment '密码',
   role_id              int(11) not null comment '角色ID',
   last_login_time      datetime comment '上次登录时间',
   login_num            int(11) not null default 0 comment '登录次数',
   status               tinyint(6) comment '状态[0:停用, 1:正常]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_admin_user comment '系统管理员';

/*==============================================================*/
/* Table: tb_dict_city                                          */
/*==============================================================*/
create table tb_dict_city
(
   id                   int(11) not null auto_increment comment '主键ID',
   code                 int(11) not null comment '编码',
   name                 varchar(128) not null comment '城市',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_dict_city comment '城市字典表';

/*==============================================================*/
/* Table: tb_private_letter                                     */
/*==============================================================*/
create table tb_private_letter
(
   id                   int(11) not null auto_increment comment '主键ID',
   sender_uid           int(11) not null comment '发信人',
   receiver_uid         int(11) not null comment '收信人',
   content              varchar(512) not null comment '内容',
   has_read             tinyint(6) default 0 comment '是否已读[0:未读; 1:已读]',
   has_reply            tinyint(6) default 0 comment '是否回复[0:未回复; 1:已回复]',
   sender_deleted       tinyint(6) default 0 comment '发送者删除[0:否; 1:是]',
   receiver_deleted     tinyint(6) default 0 comment '接收者删除[0:否; 1:是]',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_private_letter comment '私信';

/*==============================================================*/
/* Table: tb_travel_collect                                     */
/*==============================================================*/
create table tb_travel_collect
(
   id                   int(11) not null auto_increment comment '主键ID',
   uid                  int(11) not null comment '用户UID',
   tid                  int(11) not null comment '游记ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id),
   UNIQUE KEY uniq_uid_tid (uid,tid)
);

alter table tb_travel_collect comment '游记收藏表';

/*==============================================================*/
/* Table: tb_travel_content                                     */
/*==============================================================*/
create table tb_travel_content
(
   id                   int(11) not null auto_increment comment '主键ID',
   tid                  int(11) not null comment '游记ID',
   travel_date          datetime,
   content              MEDIUMTEXT not null comment '详细内容',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_content comment '游记详情表';

/*==============================================================*/
/* Table: tb_travel_info                                        */
/*==============================================================*/
create table tb_travel_info
(
   id                   int(11) not null auto_increment comment '主键ID',
   city                 varchar(256) not null comment '城市',
   uid                  int(11) not null comment '发布者ID',
   title                varchar(128) not null comment '标题',
   is_elite             tinyint(6) default 0 comment '是否精华[0:不是; 1:是]',
   is_top               tinyint(6) default 0 comment '是否置顶[0:否; 1:是]',
   status               tinyint(6) comment '状态[1:待审核; 2:审核通过; 3:审核不通过; -1:删除]',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_travel_info comment '游记信息表';

/*==============================================================*/
/* Table: tb_travel_info_stat                                   */
/*==============================================================*/
create table tb_travel_info_stat
(
   id                   int(11) not null auto_increment comment '主键ID',
   tid                  int(11) not null comment '游记ID',
   view_cnt             int(11) not null comment '浏览量',
   like_cnt             int(11) default 0 comment '喜欢数',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_info_stat comment '游记统计表';

/*==============================================================*/
/* Table: tb_travel_like                                        */
/*==============================================================*/
create table tb_travel_like
(
   id                   int(11) not null auto_increment comment '主键ID',
   tid                  int(11) not null comment '游记ID',
   uid                  int(11) not null comment '用户ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '时间戳',
   primary key (id)
);

alter table tb_travel_like comment '游记喜欢表';

/*==============================================================*/
/* Table: tb_travel_share                                       */
/*==============================================================*/
create table tb_travel_share
(
   id                   int(11) not null auto_increment,
   uid                  int(11) not null comment '分享者ID',
   tid                  int(11) not null comment '游记ID',
   share_type           tinyint(6) comment '分享渠道',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

alter table tb_travel_share comment '游记分享表';

/*==============================================================*/
/* Table: tb_travel_visit_log                                   */
/*==============================================================*/
create table tb_travel_visit_log
(
   id                   int(11) not null auto_increment comment '主键ID',
   tid                  int(11) not null comment '游记ID',
   uid                  int(11) not null comment '用户ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_travel_visit_log comment '游记浏览日志表';

/*==============================================================*/
/* Table: tb_user_follow                                        */
/*==============================================================*/
create table tb_user_follow
(
   id                   int(11) not null auto_increment comment '主键ID',
   fans_uid             int(11) not null comment '关注者ID',
   star_uid             int(11) not null comment '被关注者ID',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '时间戳',
   primary key (id),
   UNIQUE KEY uniq_uid_tid (fans_uid,star_uid)
);

alter table tb_user_follow comment '关注表';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
create table tb_user_info
(
   id                   int(11) not null auto_increment comment '主键ID',
   name                 varchar(128) default '' comment '登录用户名',
   email                varchar(128) comment '邮箱',
   user_pass            varchar(128) not null default '' comment '密码',
   history_passwd       varchar(128) comment '历史密码',
   avatar               varchar(128) comment '头像',
   nickname             varchar(128) comment '昵称',
   user_type            tinyint(6) not null default 0 comment '用户类型[0:普通用户; 1:规划师]',
   weibo_name           varchar(128) comment '微博账号',
   weibo_url            varchar(256) comment '微博链接',
   wechat_name          varchar(128) comment '微信账号',
   user_intro           varchar(1024),
   present_address      varchar(512) comment '现住址',
   previous_address     varchar(512) comment '曾经住址',
   status               tinyint(6) comment '状态',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_user_info comment '用户信息表';

drop table if exists tb_user_planner;

/*==============================================================*/
/* Table: tb_user_planner                                       */
/*==============================================================*/
create table tb_user_planner
(
   id                   int(11) not null auto_increment comment '主键ID',
   uid                  int(11) not null comment '用户ID',
   target_city          varchar(256) comment '可规划地区',
   fees                 varchar(64) default '' comment '收费标准',
   price                numeric(10,2) comment '规划价格',
   charge_mode          tinyint(6) comment '收费模式[1:按天; 2:按周; 3:按次;]',
   status               tinyint(6) comment '状态',
   refuse_reason        varchar(256) default '' comment '审核拒绝原因',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_user_planner comment '规划师';




INSERT INTO tb_dict_city (CODE,NAME) VALUES (1001,'纽约NYC及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1002,'洛杉矶LA及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1003,'旧金山San Francisco及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1004,'美国国家公园National Parks');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1005,'西雅图Seattle及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1006,'阿拉斯加Alaska');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1007,'游轮旅行Cruise');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1008,'奥兰多Orlando及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1009,'大佛罗里达Florida');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1010,'风情小镇Small Towns');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1011,'加州湾区Bay Area及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1012,'芝加哥Chicago及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1013,'其他Other');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1014,'波士顿Boston及周边');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1015,'自驾游－加州一号公路Pacific Coast Highway');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1016,'波多黎各Puerto Rico');
INSERT INTO tb_dict_city (CODE,NAME) VALUES (1017,'圣地亚哥San Diego及周边');

create unique index uniq_userinfo_email on tb_user_info (email); 

drop table if exists tb_index_travel;

/*==============================================================*/
/* Table: tb_index_travel                                       */
/*==============================================================*/
create table tb_index_travel
(
   id                   int(11) not null auto_increment,
   tid                  int(11) comment '游记ID',
   is_top               tinyint(6) default 0 comment '是否置顶[0:是; 1:否]',
   sort                 tinyint(6) default 0 comment '排序,值越大越靠前',
   src_type             char(1) comment '图片显示类型[l:大; s:小; n:窄; f:扁]',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table tb_index_travel comment '首页游记';





INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '审核管理', '/audit/.*?', 0, 0, '2013-12-21 23:18:10.408  ', '127.0.0.1', 0, '2013-12-21 23:18:10.409  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '管理员信息', '/admin/administrator/.*?', 0, 0, '2013-12-21 23:18:12.236  ', '127.0.0.1', 0, '2013-12-21 23:18:12.236  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '角色权限管理', '/admin/role/.*?', 0, 0, '2013-12-21 23:18:12.548  ', '127.0.0.1', 0, '2013-12-21 23:18:12.548  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '用户管理', '/admin/user/.*?', 0, 0, '2013-12-21 23:18:12.778  ', '127.0.0.1', 0, '2013-12-21 23:18:12.778  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '游记管理', '/audit/travel/.*?', 0, 0, '2013-12-21 23:18:13.419  ', '127.0.0.1', 0, '2013-12-21 23:18:13.419  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '私信管理', '/msg/.*?', 0, 0, '2013-12-21 23:18:13.419  ', '127.0.0.1', 0, '2013-12-21 23:18:13.419  ', '127.0.0.1');
INSERT INTO tb_admin_power_item(p_id,LEVEL,NAME,url,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES (0, 1, '规划师管理', '/admin/report/.*?', 0, 0, '2013-12-21 23:18:13.677  ', '127.0.0.1', 0, '2013-12-21 23:18:13.677  ', '127.0.0.1');

INSERT INTO tb_admin_role(NAME,STATUS,create_uid,create_time,create_ip,update_uid,update_time,update_ip) VALUES ('超级管理员', 1, 0, '2013-12-21 23:23:39.55   ', '127.0.0.1', 0, '2013-12-21 23:23:39.55   ', '127.0.0.1');


INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 1, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 2, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 3, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 4, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 5, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 6, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');
INSERT INTO tb_admin_role_power(role_id,power_id,create_uid,create_time,create_ip) VALUES (1, 7, 0, '2013-12-21 23:37:43.77   ', '127.0.0.1');



INSERT INTO tb_admin_user (NAME, PASSWORD, role_id, last_login_time, login_num, STATUS, create_uid, create_time, create_ip, update_uid, update_time, update_ip) VALUES ('root', '87e569f2f77d410d9beffeaa1573d859', 1, '2014-05-23 00:11:56.707  ', 132, 1, 0, '2013-12-21 22:03:12.305  ', '127.0.0.1', 0, '2014-05-23 00:15:14.562  ', '119.9.74.231');
