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
   context              varchar(512) not null comment '内容',
   has_read             tinyint(6) default 0 comment '是否已读[0:未读; 1:已读]',
   has_reply            tinyint(6) default 0 comment '是否回复[0:未回复; 1:已回复]',
   is_deleted           tinyint(6) default 0 comment '是否删除[0:否; 1:是]',
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
   primary key (id)
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
   content              text not null comment '详细内容',
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
   city                 int(11) not null comment '城市',
   planner_uid          int(11) not null comment '策划师ID',
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
   primary key (id)
);

alter table tb_user_follow comment '关注表';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
create table tb_user_info
(
   id                   int(11) not null auto_increment comment '主键ID',
   name                 varchar(128) not null default '' comment '登录用户名',
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

/*==============================================================*/
/* Table: tb_user_planner                                       */
/*==============================================================*/
create table tb_user_planner
(
   id                   int(11) not null auto_increment comment '主键ID',
   uid                  int(11) not null comment '用户ID',
   target_city          varchar(256) comment '可策划地区',
   price                numeric(10,2) comment '策划价格',
   charge_mode          tinyint(6) comment '收费模式[1:按天; 2:按周; 3:按次;]',
   status               tinyint(6) comment '状态',
   create_uid           int(11),
   create_time          datetime,
   create_ip            varchar(64),
   update_uid           int(11),
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            varchar(64),
   primary key (id)
);

alter table tb_user_planner comment '策划师';




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