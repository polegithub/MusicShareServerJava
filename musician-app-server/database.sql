DROP TABLE `ms_image_store` ;

CREATE TABLE `ms_image_store` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `token` varchar(128) NOT NULL COMMENT '�ļ�ƾ֤',
  
  `file` varchar(200)  COMMENT '�ļ�����',

  `alt` varchar(500)  COMMENT '��ע',

  `src` varchar(500)  COMMENT '����',
  
  `type` char(1) DEFAULT '0' COMMENT '���� ͷ��,��Ʒ,��֤',
  
  `state` char(10) DEFAULT '0' COMMENT '״̬',
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


DROP TABLE `ms_home_bannaner` ;

CREATE TABLE `ms_home_bannaner` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `token` varchar(128) NOT NULL COMMENT '�ļ�ƾ֤',
  
  `sequence` int  COMMENT '����',
  
  `file` varchar(200)  COMMENT '�ļ�����',

  `alt` varchar(500)  COMMENT '��ע',

  `link` varchar(500)  COMMENT '����',
  
  `type` char(1) DEFAULT '0' COMMENT '���0 ���� 1 bananer',
  
  `pubtime` datetime  COMMENT '����ʱ��',
  
  `state` char(1) DEFAULT '0' COMMENT '״̬ 0 ���� 1����',
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS ms_admin;
CREATE TABLE ms_admin (
  id 			int auto_increment NOT NULL  COMMENT '�û����',
  username 		varchar(30) NOT NULL  COMMENT '�û���',
  password 		varchar(50) NOT NULL  COMMENT '����',
  name 			varchar(50) NOT NULL  COMMENT '�û�����',
  department	varchar(50) COMMENT '����',
  phone 		varchar(20)   COMMENT '��ϵ��ʽ',
  address 		varchar(200)  COMMENT '��ϵ��ַ',
  email 		varchar(50)   COMMENT '�ʼ�',
  level			char(1)  default '1' COMMENT '0:admin,1:����Ա',
  time	 		datetime   COMMENT 'ע��ʱ��',
  state 		char(1) 	default '0' COMMENT '�Ƿ���� 0:���� 1��������',
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into ms_admin(username,password,name,email,level,state) value('admin','21232f297a57a5a743894a0e4a801fc3','ϵͳ����Ա','admin@admin.com','0','0');


DROP TABLE ms_activity ;
CREATE TABLE ms_activity (
id  			bigint(14) NOT NULL AUTO_INCREMENT COMMENT '���',
name  			varchar(200)  COMMENT '�����',
introduction	text 		  COMMENT '����' ,
image  			varchar(300)  COMMENT '�����',
link  			varchar(300)  COMMENT '�����',
starttime  		varchar(20)   COMMENT '���ʼʱ��',
endtime 		varchar(20)   COMMENT '�����ʱ��',
money  			double(11,2) NULL DEFAULT 0.00 COMMENT '�����',
number  		int(11) NULL DEFAULT 0 COMMENT '�޶�����',
amount  		int(11) NULL DEFAULT 0 COMMENT '���������',
time   			datetime   COMMENT '����ʱ��',
state  			char(1) DEFAULT '0'  COMMENT '�Ƿ���� 0:���� 1��������',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1;


DROP TABLE IF EXISTS `ms_product_type`;
CREATE TABLE `ms_product_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '���',
  `name` varchar(50) NOT NULL  COMMENT '��������',
  `pid` int DEFAULT 0 COMMENT '������ 0������',
  `logo` varchar(500)  COMMENT '����logo',
  `state` char(1) DEFAULT '0' COMMENT '��Ʒ����״̬ 0�����ã�1������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ʒ����';

insert into ms_product_type(name,logo) values('����','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('����','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('����','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('����ʦ','https://git.oschina.net/logo.gif');

DROP TABLE IF EXISTS `ms_product`;
CREATE TABLE `ms_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'default' COMMENT '��Ʒ����',
 -- `limit_time` datetime NOT NULL DEFAULT '2016-01-01 00:00:00' COMMENT '��Ʒʹ������',
  `state` char(1) NOT NULL DEFAULT '0' COMMENT 'ʹ��״̬��0��ʾ���ã�1��ʾ����',
  `cost` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '��Ʒ�۸�����',
  `img` varchar(255) NOT NULL DEFAULT '00' COMMENT 'ͼƬ��ַ',
  `category` int(11) NOT NULL DEFAULT '0' COMMENT '��Ʒ�������',
  `owner`    int(11) NOT NULL  COMMENT '������',
  `introduce` text COMMENT '���',
  `desc` text COMMENT '˵��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='��Ʒ��';



DROP TABLE IF EXISTS `ms_field_order`;
CREATE TABLE `ms_field_order` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`title`  varchar(256) NOT NULL ,
`description`  varchar(1024) ,
`address_id`  int(11) ,
`account_id`  int(11) ,
`needcount`  int(11) ,
`starttime`  	datetime   COMMENT '������ʼʱ��',
`endtime`  	datetime   COMMENT '��������ʱ��',
`deadline`  	datetime   COMMENT '������ֹʱ��',
`created`  datetime ,
`state`  varchar(16) ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8 COMMENT='�̻�����פ��������';


DROP TABLE IF EXISTS `ms_merchant_publish`;
CREATE TABLE `ms_merchant_publish` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`title`  varchar(256) NOT NULL ,
`description`  varchar(1024) ,
`address_id`  int(11) ,
`account_id`  int(11) ,
`created`  datetime ,
`state`  varchar(16) ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=2016000001 DEFAULT CHARSET=utf8 COMMENT='�̻�����������';

DROP TABLE IF EXISTS `ms_publish_time`;
CREATE TABLE `ms_publish_time` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '�������',
  `price` 		double(11,2) NOT NULL COMMENT '�۸�',
  `starttime`  	datetime   COMMENT '������ʼʱ��',
  `endtime`  	datetime   COMMENT '��������ʱ��',
  `deadline`  	datetime   COMMENT '������ֹʱ��',
  `need` 		int DEFAULT 0  COMMENT '��Ҫ����',
  `recruited`  	int DEFAULT 0  COMMENT '��������',
  `remark` 		text COMMENT '��ע',
  `state` 		varchar(16)  COMMENT '����״̬',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����ʱ���';


DROP TABLE IF EXISTS `ms_publish_grap`;
CREATE TABLE `ms_publish_grap` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '�������',
  `timeid` 		int 		 COMMENT '����ʱ��α��',
  `userid` 		int NOT NULL COMMENT '�û����',
  `type`  		varchar(15)  COMMENT '�������� TIME FIELD',
  `time`  		datetime   	 COMMENT '������ʱ��',
  `state`  		varchar(15)  COMMENT '�Ƿ����ɹ� ��: WAIT,��:SUCCESS,�̼Ҳ�����Ȥ:UNINTERESTED',
  `remark` 		text COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��������';



DROP TABLE IF EXISTS `ms_publish_common`;
CREATE TABLE `ms_publish_common` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int  COMMENT '�������',
  `timeid` 		int  COMMENT '����ʱ��α��',
  `puserid` 	int NOT NULL COMMENT '�����˱��',
  `peeuser` 	int NOT NULL COMMENT '�����˱��',
  `time`  		datetime   COMMENT '����ʱ��',
  `score` 		double default 0 COMMENT  '����',
  `remark` 		text COMMENT '��������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����б�';


DROP TABLE IF EXISTS `ms_order_style`;
CREATE TABLE `ms_order_style` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `name` 		 varchar(128)  COMMENT '�������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����';


DROP TABLE IF EXISTS `ms_publish_style`;
CREATE TABLE `ms_publish_style` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '�������',
  `type` 		varchar(200) NOT NULL COMMENT '�������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��������';



