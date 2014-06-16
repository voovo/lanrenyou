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
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   CODE                 INT(11) NOT NULL COMMENT '����',
   NAME                 VARCHAR(128) NOT NULL COMMENT '����',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_dict_city COMMENT '�����ֵ��';

/*==============================================================*/
/* Table: tb_private_letter                                     */
/*==============================================================*/
CREATE TABLE tb_private_letter
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   sender_uid           INT(11) NOT NULL COMMENT '������',
   receiver_uid         INT(11) NOT NULL COMMENT '������',
   context              VARCHAR(512) NOT NULL COMMENT '����',
   has_read             TINYINT(6) DEFAULT 0 COMMENT '�Ƿ��Ѷ�[0:δ��; 1:�Ѷ�]',
   has_reply            TINYINT(6) DEFAULT 0 COMMENT '�Ƿ�ظ�[0:δ�ظ�; 1:�ѻظ�]',
   is_deleted           TINYINT(6) DEFAULT 0 COMMENT '�Ƿ�ɾ��[0:��; 1:��]',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_private_letter COMMENT '˽��';

/*==============================================================*/
/* Table: tb_travel_content                                     */
/*==============================================================*/
CREATE TABLE tb_travel_content
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   tid                  INT(11) NOT NULL COMMENT '�μ�ID',
   content              TEXT NOT NULL COMMENT '��ϸ����',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_content COMMENT '�μ������';

/*==============================================================*/
/* Table: tb_travel_info                                        */
/*==============================================================*/
CREATE TABLE tb_travel_info
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   city                 INT(11) NOT NULL COMMENT '����',
   planner_uid          INT(11) NOT NULL COMMENT '�߻�ʦID',
   title                VARCHAR(128) NOT NULL COMMENT '����',
   is_elite             TINYINT(6) DEFAULT 0 COMMENT '�Ƿ񾫻�[0:����; 1:��]',
   is_top               TINYINT(6) DEFAULT 0 COMMENT '�Ƿ��ö�[0:��; 1:��]',
   STATUS               TINYINT(6) COMMENT '״̬[1:�����; 2:���ͨ��; 3:��˲�ͨ��; -1:ɾ��]',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info COMMENT '�μ���Ϣ��';

/*==============================================================*/
/* Table: tb_travel_info_collect                                */
/*==============================================================*/
CREATE TABLE tb_travel_info_collect
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   uid                  INT(11) NOT NULL COMMENT '�û�UID',
   tid                  INT(11) NOT NULL COMMENT '�μ�ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info_collect COMMENT '�μ��ղر�';

/*==============================================================*/
/* Table: tb_travel_info_stat                                   */
/*==============================================================*/
CREATE TABLE tb_travel_info_stat
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   tid                  INT(11) NOT NULL COMMENT '�μ�ID',
   view_cnt             INT(11) NOT NULL COMMENT '�����',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_info_stat COMMENT '�μ�ͳ�Ʊ�';

/*==============================================================*/
/* Table: tb_travel_visit_log                                   */
/*==============================================================*/
CREATE TABLE tb_travel_visit_log
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   tid                  INT(11) NOT NULL COMMENT '�μ�ID',
   uid                  INT(11) NOT NULL COMMENT '�û�ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE tb_travel_visit_log COMMENT '�μ������־��';

/*==============================================================*/
/* Table: tb_user_follow                                        */
/*==============================================================*/
CREATE TABLE tb_user_follow
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   fans_uid             INT(11) NOT NULL COMMENT '��ע��ID',
   star_uid             INT(11) NOT NULL COMMENT '����ע��ID',
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ʱ���',
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_follow COMMENT '��ע��';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
CREATE TABLE tb_user_info
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   NAME                 VARCHAR(128) NOT NULL DEFAULT '' COMMENT '��¼�û���',
   email                VARCHAR(128) COMMENT '����',
   PASSWORD             VARCHAR(128) NOT NULL DEFAULT '' COMMENT '����',
   history_passwd       VARCHAR(128) COMMENT '��ʷ����',
   avatar               VARCHAR(128) COMMENT 'ͷ��',
   nickname             VARCHAR(128) COMMENT '�ǳ�',
   user_type            TINYINT(6) NOT NULL DEFAULT 0 COMMENT '�û�����[0:��ͨ�û�; 1:�滮ʦ]',
   weibo_name           VARCHAR(128) COMMENT '΢���˺�',
   weibo_url            VARCHAR(256) COMMENT '΢������',
   wechat_name          VARCHAR(128) COMMENT '΢���˺�',
   STATUS               TINYINT(6) COMMENT '״̬',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_info COMMENT '�û���Ϣ��';

/*==============================================================*/
/* Table: tb_user_planner                                       */
/*==============================================================*/
CREATE TABLE tb_user_planner
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
   uid                  INT(11) NOT NULL COMMENT '�û�ID',
   target_city          VARCHAR(256) COMMENT '�ɲ߻�����',
   price                NUMERIC(10,2) COMMENT '�߻��۸�',
   charge_mode          TINYINT(6) COMMENT '�շ�ģʽ[1:����; 2:����; 3:����;]',
   user_intro           VARCHAR(1024) COMMENT '���˼��',
   STATUS               TINYINT(6) COMMENT '״̬',
   create_uid           INT(11),
   create_time          DATETIME,
   create_ip            VARCHAR(64),
   update_uid           INT(11),
   update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   update_ip            VARCHAR(64),
   PRIMARY KEY (id)
);

ALTER TABLE tb_user_planner COMMENT '�߻�ʦ';



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