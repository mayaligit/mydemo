/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.37 : Database - saas_biz_attendance
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`saas_biz_attendance` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `saas_biz_attendance`;

/*Table structure for table `t_attendance` */

DROP TABLE IF EXISTS `t_attendance`;

CREATE TABLE `t_attendance` (
  `id` char(32) NOT NULL COMMENT '主键使用uuid',
  `attendance_user` char(32) NOT NULL COMMENT '当前打卡用户',
  `start_time` datetime DEFAULT NULL COMMENT '上班打卡时间',
  `end_time` datetime DEFAULT NULL COMMENT '下班打卡时间',
  `attendance_status` char(11) DEFAULT '0' COMMENT '0/正常,1/异常 2/外勤',
  `attendance_desc` varchar(255) DEFAULT NULL COMMENT '考勤状态正常的话考勤描述为空，异常的话考勤状态需要显示是否已提审批，未审批显示异常',
  `attendance_month` varchar(32) DEFAULT NULL COMMENT '考勤月份',
  `start_location` char(32) DEFAULT NULL COMMENT '上班打卡地点',
  `end_location` char(32) DEFAULT NULL COMMENT '下班打卡地点',
  `start_signal` char(32) DEFAULT NULL COMMENT '上班打卡使用的信号源,如wifi名,移动4G等保留字段',
  `end_signal` char(32) DEFAULT NULL COMMENT '下班打卡使用的信号源,如wifi名,移动4G等保留字段',
  `daily_status` int(11) DEFAULT '0' COMMENT '日报状态,0/未完成,1/已完成',
  `attendance_group` char(32) DEFAULT NULL COMMENT '备用字段,用户所属考勤组',
  `end_time_status` char(32) DEFAULT NULL COMMENT '0/下班正常,1/下班早退',
  `start_time_status` char(32) DEFAULT NULL COMMENT '0/上班正常,1/上班迟到',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_attendance` */

insert  into `t_attendance`(`id`,`attendance_user`,`start_time`,`end_time`,`attendance_status`,`attendance_desc`,`attendance_month`,`start_location`,`end_location`,`start_signal`,`end_signal`,`daily_status`,`attendance_group`,`end_time_status`,`start_time_status`,`create_by`,`create_time`,`update_by`,`update_time`) values ('186ef640cc01000','a0001','2017-10-31 10:09:08','2017-10-31 15:33:31','1','地点异常','2017-10-31',NULL,'广东省广州市天河区新塘街道食全食美(凌塘新村店)',NULL,NULL,0,NULL,'1',NULL,'a0001','2017-10-31 15:33:33','a0001','2017-10-31 15:33:33'),('186f6d477c02000','小泽','2017-10-31 09:09:23','2017-10-31 20:09:39','1',NULL,'10',NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,'a0001','2017-10-31 16:06:07','a0001','2017-10-31 16:06:07');

/*Table structure for table `t_audit` */

DROP TABLE IF EXISTS `t_audit`;

CREATE TABLE `t_audit` (
  `id` char(32) NOT NULL COMMENT '审批表主键id',
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日报提交时间',
  `audit_content` varchar(255) NOT NULL COMMENT '申请的审批的内容',
  `audit_user_id` varchar(255) DEFAULT NULL COMMENT '审批人ID',
  `audit_user_name` varchar(255) DEFAULT NULL COMMENT '审批人名字',
  `audit_status` int(11) DEFAULT NULL COMMENT '审批意见状态,0/已阅,1/已处理,2/未处理',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `attendance_id` char(32) DEFAULT NULL COMMENT '审批的是补卡类型时,关联对应考勤',
  `audit_suggestion` int(11) DEFAULT NULL COMMENT '0/同意 , 1/拒绝',
  `business_type` int(11) DEFAULT NULL COMMENT '保留字段,暂定0为补卡新类型, 以后可增加请假等类型',
  `create_by` char(32) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `audit_detail` varchar(255) DEFAULT NULL COMMENT '审批详情',
  `username` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `attendance_id` (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_audit` */

/*Table structure for table `t_clazzes` */

DROP TABLE IF EXISTS `t_clazzes`;

CREATE TABLE `t_clazzes` (
  `id` char(32) NOT NULL COMMENT '班次表id',
  `nomal_address` varchar(50) NOT NULL COMMENT '设定正常打卡地点',
  `nomal_start_time` time NOT NULL COMMENT '正常上班时间',
  `nomal_end_time` time NOT NULL COMMENT '正常下班时间',
  `class_group_id` char(32) DEFAULT NULL COMMENT '用户所属组ID',
  `class_group` char(32) DEFAULT NULL COMMENT '用户所属组名字',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  `total` int(11) NOT NULL COMMENT '打卡应到人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_clazzes` */

/*Table structure for table `t_clazzes_bak` */

DROP TABLE IF EXISTS `t_clazzes_bak`;

CREATE TABLE `t_clazzes_bak` (
  `id` char(32) NOT NULL COMMENT '班次表id',
  `nomal_address` varchar(50) NOT NULL COMMENT '设定正常打卡地点',
  `nomal_start_time` time NOT NULL COMMENT '正常上班时间',
  `nomal_end_time` time NOT NULL COMMENT '正常下班时间',
  `class_group_id` char(32) DEFAULT NULL COMMENT '用户所属组ID',
  `class_group` char(32) DEFAULT NULL COMMENT '用户所属组名字',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_clazzes_bak` */

insert  into `t_clazzes_bak`(`id`,`nomal_address`,`nomal_start_time`,`nomal_end_time`,`class_group_id`,`class_group`,`create_by`,`create_time`,`update_by`,`update_time`,`username`) values ('c2310c7ec996409b8e91f12daa428a98','1000','08:30:00','18:00:00','111','0000','10086','2017-10-31 15:12:29','10086','2017-10-31 15:12:29','0000');

/*Table structure for table `t_daily` */

DROP TABLE IF EXISTS `t_daily`;

CREATE TABLE `t_daily` (
  `id` char(32) NOT NULL COMMENT '日报表主键id',
  `attendance_id` char(32) NOT NULL COMMENT '日报所属考勤的id',
  `daily_desc` varchar(255) DEFAULT NULL COMMENT '日报描述',
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日报提交时间',
  `daily_title` varchar(255) DEFAULT NULL COMMENT '日报标题',
  `finished_work` varchar(255) DEFAULT NULL COMMENT '已完成工作',
  `unfinished_work` varchar(255) DEFAULT NULL COMMENT '未完成工作',
  `submit_location` varchar(255) DEFAULT NULL COMMENT '提交日报时的地点',
  `examiner` char(32) DEFAULT NULL COMMENT '日报审批人',
  `examine_time` datetime DEFAULT NULL COMMENT '日报审批时间',
  `suggestion_status` int(11) DEFAULT NULL COMMENT '审批意见状态,0/已阅,1/已处理,2/未处理',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  KEY `FK_ID` (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_daily` */

insert  into `t_daily`(`id`,`attendance_id`,`daily_desc`,`submit_time`,`daily_title`,`finished_work`,`unfinished_work`,`submit_location`,`examiner`,`examine_time`,`suggestion_status`,`create_by`,`create_time`,`update_by`,`update_time`,`username`) values ('0c27e6d67416437495344dad281dbb65','934573d63a9348fba43c3dd6d0982cd4','日报描述','2017-09-09 00:00:00','日报:Mon Oct 30 16:26:24 CST 2017','已完成工作','未完成工作','日报提交地点','小苍',NULL,2,'a0001','2017-10-30 16:26:30','a0001','2017-10-30 16:26:30',''),('186f6d401802000','186f6d477c02000','日报描述','2017-09-09 00:00:00','日报:Tue Oct 31 16:05:58 CST 2017','已完成工作','未完成工作','日报提交地点','小苍',NULL,2,'a0001','2017-10-31 16:06:08','a0001','2017-10-31 16:06:08','6666');

/*Table structure for table `t_daily_record` */

DROP TABLE IF EXISTS `t_daily_record`;

CREATE TABLE `t_daily_record` (
  `attendance_id` char(32) NOT NULL COMMENT '主键使用uuid',
  `user_name` char(32) DEFAULT NULL COMMENT '用户名',
  `location` char(32) NOT NULL COMMENT '打卡地点',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_daily_record` */

/*Table structure for table `t_statistics` */

DROP TABLE IF EXISTS `t_statistics`;

CREATE TABLE `t_statistics` (
  `id` char(32) NOT NULL COMMENT '统计表主键id',
  `late_time` int(11) DEFAULT NULL COMMENT '迟到次数',
  `early_time` int(11) DEFAULT NULL COMMENT '早退次数',
  `office_time` int(11) DEFAULT NULL COMMENT '上班时长',
  `daily_time` int(11) DEFAULT NULL COMMENT '补填日报次数',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_statistics` */

insert  into `t_statistics`(`id`,`late_time`,`early_time`,`office_time`,`daily_time`,`create_by`,`create_time`,`update_by`,`update_time`,`username`) values ('123456',1,NULL,20,NULL,'小泽','2017-10-31 16:51:04',NULL,'2017-10-31 16:51:19',''),('2222',1,NULL,15,NULL,'小泽','2017-10-31 16:51:37',NULL,'2017-10-31 16:51:37',''),('ddd',1,NULL,13,NULL,'小苍','2017-10-01 17:06:09',NULL,'2017-10-31 17:06:19',''),('gfgfgg',1,NULL,40,NULL,'老王','2017-10-31 16:56:14',NULL,'2017-10-31 16:56:14',''),('ggg',NULL,NULL,22,NULL,'老王','2017-11-01 17:10:03',NULL,'2017-10-31 17:10:45',''),('sddfsdfd',NULL,NULL,10,NULL,'小苍','2017-10-01 16:50:41',NULL,'2017-10-31 16:50:48','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
