DROP TABLE `ms_image_store` ;

CREATE TABLE `ms_image_store` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `token` varchar(128) NOT NULL COMMENT '文件凭证',
  
  `file` varchar(200)  COMMENT '文件名称',

  `alt` varchar(500)  COMMENT '备注',

  `src` varchar(500)  COMMENT '链接',
  
  `type` char(1) DEFAULT '0' COMMENT '类型 头像,商品,认证',
  
  `state` char(10) DEFAULT '0' COMMENT '状态',
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


DROP TABLE `ms_home_bannaner` ;

CREATE TABLE `ms_home_bannaner` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `token` varchar(128) NOT NULL COMMENT '文件凭证',
  
  `sequence` int  COMMENT '排序',
  
  `file` varchar(200)  COMMENT '文件名称',

  `alt` varchar(500)  COMMENT '备注',

  `link` varchar(500)  COMMENT '链接',
  
  `type` char(1) DEFAULT '0' COMMENT '类别0 导航 1 bananer',
  
  `pubtime` datetime  COMMENT '发布时间',
  
  `state` char(1) DEFAULT '0' COMMENT '状态 0 正常 1废弃',
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS ms_admin;
CREATE TABLE ms_admin (
  id 			int auto_increment NOT NULL  COMMENT '用户编号',
  username 		varchar(30) NOT NULL  COMMENT '用户名',
  password 		varchar(50) NOT NULL  COMMENT '密码',
  name 			varchar(50) NOT NULL  COMMENT '用户名称',
  department	varchar(50) COMMENT '部门',
  phone 		varchar(20)   COMMENT '联系方式',
  address 		varchar(200)  COMMENT '联系地址',
  email 		varchar(50)   COMMENT '邮件',
  level			char(1)  default '1' COMMENT '0:admin,1:管理员',
  time	 		datetime   COMMENT '注册时间',
  state 		char(1) 	default '0' COMMENT '是否可用 0:可用 1：不可用',
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into ms_admin(username,password,name,email,level,state) value('admin','21232f297a57a5a743894a0e4a801fc3','系统管理员','admin@admin.com','0','0');


DROP TABLE ms_activity ;
CREATE TABLE ms_activity (
id  			bigint(14) NOT NULL AUTO_INCREMENT COMMENT '编号',
name  			varchar(200)  COMMENT '活动名称',
introduction	text 		  COMMENT '活动简洁' ,
image  			varchar(300)  COMMENT '活动海报',
link  			varchar(300)  COMMENT '活动链接',
starttime  		varchar(20)   COMMENT '活动开始时间',
endtime 		varchar(20)   COMMENT '活动结束时间',
money  			double(11,2) NULL DEFAULT 0.00 COMMENT '活动奖励',
number  		int(11) NULL DEFAULT 0 COMMENT '限定人数',
amount  		int(11) NULL DEFAULT 0 COMMENT '已完成人数',
time   			datetime   COMMENT '发布时间',
state  			char(1) DEFAULT '0'  COMMENT '是否可用 0:可用 1：不可用',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1;


DROP TABLE IF EXISTS `ms_product_type`;
CREATE TABLE `ms_product_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL  COMMENT '类型名称',
  `pid` int DEFAULT 0 COMMENT '父类型 0：顶级',
  `logo` varchar(500)  COMMENT '类型logo',
  `state` char(1) DEFAULT '0' COMMENT '产品类型状态 0：可用，1不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品类型';

insert into ms_product_type(name,logo) values('乐手','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('乐谱','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('乐器','https://git.oschina.net/logo.gif');
insert into ms_product_type(name,logo) values('调音师','https://git.oschina.net/logo.gif');

DROP TABLE IF EXISTS `ms_product`;
CREATE TABLE `ms_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT 'default' COMMENT '产品名称',
 -- `limit_time` datetime NOT NULL DEFAULT '2016-01-01 00:00:00' COMMENT '产品使用期限',
  `state` char(1) NOT NULL DEFAULT '0' COMMENT '使用状态，0表示启用；1表示禁用',
  `cost` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格数量',
  `img` varchar(255) NOT NULL DEFAULT '00' COMMENT '图片地址',
  `category` int(11) NOT NULL DEFAULT '0' COMMENT '产品所属类别',
  `owner`    int(11) NOT NULL  COMMENT '发布人',
  `introduce` text COMMENT '简介',
  `desc` text COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='产品表';



DROP TABLE IF EXISTS `ms_field_order`;
CREATE TABLE `ms_field_order` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`title`  varchar(256) NOT NULL ,
`description`  varchar(1024) ,
`address_id`  int(11) ,
`account_id`  int(11) ,
`needcount`  int(11) ,
`starttime`  	datetime   COMMENT '订单开始时间',
`endtime`  	datetime   COMMENT '订单结束时间',
`deadline`  	datetime   COMMENT '报名截止时间',
`created`  datetime ,
`state`  varchar(16) ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8 COMMENT='商户发布驻场订单表';


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
)ENGINE=InnoDB AUTO_INCREMENT=2016000001 DEFAULT CHARSET=utf8 COMMENT='商户发布订单表';

DROP TABLE IF EXISTS `ms_publish_time`;
CREATE TABLE `ms_publish_time` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '订单编号',
  `price` 		double(11,2) NOT NULL COMMENT '价格',
  `starttime`  	datetime   COMMENT '订单开始时间',
  `endtime`  	datetime   COMMENT '订单结束时间',
  `deadline`  	datetime   COMMENT '报名截止时间',
  `need` 		int DEFAULT 0  COMMENT '需要人数',
  `recruited`  	int DEFAULT 0  COMMENT '抢单人数',
  `remark` 		text COMMENT '备注',
  `state` 		varchar(16)  COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单时间表';


DROP TABLE IF EXISTS `ms_publish_grap`;
CREATE TABLE `ms_publish_grap` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '订单编号',
  `timeid` 		int 		 COMMENT '订单时间段编号',
  `userid` 		int NOT NULL COMMENT '用户编号',
  `type`  		varchar(15)  COMMENT '订单类型 TIME FIELD',
  `time`  		datetime   	 COMMENT '抢订单时间',
  `state`  		varchar(15)  COMMENT '是否抢成功 否: WAIT,是:SUCCESS,商家不感兴趣:UNINTERESTED',
  `remark` 		text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抢订单表';



DROP TABLE IF EXISTS `ms_publish_common`;
CREATE TABLE `ms_publish_common` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int  COMMENT '订单编号',
  `timeid` 		int  COMMENT '订单时间段编号',
  `puserid` 	int NOT NULL COMMENT '评论人编号',
  `peeuser` 	int NOT NULL COMMENT '被评人编号',
  `time`  		datetime   COMMENT '评论时间',
  `score` 		double default 0 COMMENT  '评分',
  `remark` 		text COMMENT '评论内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论列表';


DROP TABLE IF EXISTS `ms_order_style`;
CREATE TABLE `ms_order_style` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `name` 		 varchar(128)  COMMENT '风格名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风格表';


DROP TABLE IF EXISTS `ms_publish_style`;
CREATE TABLE `ms_publish_style` (
  `id` 			int(11) NOT NULL AUTO_INCREMENT,
  `orderid` 	int NOT NULL COMMENT '订单编号',
  `type` 		varchar(200) NOT NULL COMMENT '风格名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单风格表';



