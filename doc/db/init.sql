DROP TABLE IF EXISTS tb_dict_city;

DROP TABLE IF EXISTS tb_private_letter;

DROP TABLE IF EXISTS tb_travel_content;

DROP TABLE IF EXISTS tb_travel_info;

DROP TABLE IF EXISTS tb_travel_info_collect;

DROP TABLE IF EXISTS tb_travel_info_stat;

DROP TABLE IF EXISTS tb_travel_visit_log;

DROP TABLE IF EXISTS tb_user_follow;

DROP TABLE IF EXISTS tb_user_info;

DROP TABLE IF EXISTS tb_user_planner;

/*==============================================================*/
/* Table: tb_dict_city                                          */
/*==============================================================*/
CREATE TABLE tb_dict_city
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   CODE                 INT(11) NOT NULL COMMENT '编码',
   NAME                 VARCHAR(128) NOT NULL COMMENT '城市',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_dict_city COMMENT '城市字典表';

/*==============================================================*/
/* Table: tb_private_letter                                     */
/*==============================================================*/
CREATE TABLE tb_private_letter
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   sender_uid           INT(11) NOT NULL COMMENT '发信人',
   receiver_uid         INT(11) NOT NULL COMMENT '收信人',
   context              VARCHAR(512) NOT NULL COMMENT '内容',
   has_read             TINYINT(6) DEFAULT 0 COMMENT '是否已读[0:未读; 1:已读]',
   has_reply            TINYINT(6) DEFAULT 0 COMMENT '是否回复[0:未回复; 1:已回复]',
   is_deleted           TINYINT(6) DEFAULT 0 COMMENT '是否删除[0:否; 1:是]',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_private_letter COMMENT '私信';

/*==============================================================*/
/* Table: tb_travel_content                                     */
/*==============================================================*/
CREATE TABLE tb_travel_content
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   tid                  INT(11) NOT NULL COMMENT '游记ID',
   content              TEXT NOT NULL COMMENT '详细内容',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_content COMMENT '游记详情表';

/*==============================================================*/
/* Table: tb_travel_info                                        */
/*==============================================================*/
CREATE TABLE tb_travel_info
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   city                 INT(11) NOT NULL COMMENT '城市',
   planner_uid          INT(11) NOT NULL COMMENT '策划师ID',
   title                VARCHAR(128) NOT NULL COMMENT '标题',
   is_elite             TINYINT(6) DEFAULT 0 COMMENT '是否精华[0:不是; 1:是]',
   is_top               TINYINT(6) DEFAULT 0 COMMENT '是否置顶[0:否; 1:是]',
   STATUS               TINYINT(6) COMMENT '状态[1:待审核; 2:审核通过; 3:审核不通过; -1:删除]',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info COMMENT '游记信息表';

/*==============================================================*/
/* Table: tb_travel_info_collect                                */
/*==============================================================*/
CREATE TABLE tb_travel_info_collect
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   uid                  INT(11) NOT NULL COMMENT '用户UID',
   tid                  INT(11) NOT NULL COMMENT '游记ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info_collect COMMENT '游记收藏表';

/*==============================================================*/
/* Table: tb_travel_info_stat                                   */
/*==============================================================*/
CREATE TABLE tb_travel_info_stat
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   tid                  INT(11) NOT NULL COMMENT '游记ID',
   view_cnt             INT(11) NOT NULL COMMENT '浏览量',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info_stat COMMENT '游记统计表';

/*==============================================================*/
/* Table: tb_travel_visit_log                                   */
/*==============================================================*/
CREATE TABLE tb_travel_visit_log
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   tid                  INT(11) NOT NULL COMMENT '游记ID',
   uid                  INT(11) NOT NULL COMMENT '用户ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_visit_log COMMENT '游记浏览日志表';

/*==============================================================*/
/* Table: tb_user_follow                                        */
/*==============================================================*/
CREATE TABLE tb_user_follow
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   fans_uid             INT(11) NOT NULL COMMENT '关注者ID',
   star_uid             INT(11) NOT NULL COMMENT '被关注者ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_follow COMMENT '关注表';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
CREATE TABLE tb_user_info
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   NAME                 VARCHAR(128) NOT NULL DEFAULT '' COMMENT '登录用户名',
   email                VARCHAR(128) COMMENT '邮箱',
   PASSWORD             VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码',
   history_passwd       VARCHAR(128) COMMENT '历史密码',
   avatar               VARCHAR(128) COMMENT '头像',
   nickname             VARCHAR(128) COMMENT '昵称',
   user_type            TINYINT(6) NOT NULL DEFAULT 0 COMMENT '用户类型[0:普通用户; 1:规划师]',
   weibo_name           VARCHAR(128) COMMENT '微博账号',
   weibo_url            VARCHAR(256) COMMENT '微博链接',
   wechat_name          VARCHAR(128) COMMENT '微信账号',
   STATUS               TINYINT(6) COMMENT '状态',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_info COMMENT '用户信息表';

/*==============================================================*/
/* Table: tb_user_planner                                       */
/*==============================================================*/
CREATE TABLE tb_user_planner
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   uid                  INT(11) NOT NULL COMMENT '用户ID',
   target_city          VARCHAR(256) COMMENT '可策划地区',
   price                NUMERIC(10,2) COMMENT '策划价格',
   charge_mode          TINYINT(6) COMMENT '收费模式[1:按天; 2:按周; 3:按次;]',
   user_intro           VARCHAR(1024) COMMENT '个人简介',
   STATUS               TINYINT(6) COMMENT '状态',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_planner COMMENT '策划师';



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