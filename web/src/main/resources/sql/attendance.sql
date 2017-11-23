/*
SQLyog 企业版 - MySQL GUI v8.14
MySQL - 5.7.12 : Database - saas_biz_attendance
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`saas_biz_attendance` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `saas_biz_attendance2.0`;

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
  `attendance_card_status` char(32) DEFAULT '2' COMMENT '0/正常，1/缺上班卡，2/缺下班卡',
  `attendance_longitude` float(10,6) DEFAULT NULL COMMENT '考勤打卡的经度',
  `attendance_dimension` float(10,6) DEFAULT NULL COMMENT '考勤打卡的维度',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_attendance_user` */

DROP TABLE IF EXISTS `t_attendance_user`;

CREATE TABLE `t_attendance_user` (
  `id` char(32) NOT NULL COMMENT '主键使用uuid',
  `attendance_username` char(32) NOT NULL COMMENT '账号',
  `attendance_password` char(32) NOT NULL COMMENT '密码',
  `attendance_group` char(32) NOT NULL COMMENT '管理所属组',
  `enterprise_id` char(32) DEFAULT NULL COMMENT '企业id-备用字段',
  `enterprise_name` char(32) DEFAULT NULL COMMENT '企业名称-备用字段',
  `user_type` char(32) DEFAULT NULL COMMENT '0/超级管理员,1/考勤组管理员',
  `actived` char(11) DEFAULT '0' COMMENT '0/正常,1/停用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_audit` */

DROP TABLE IF EXISTS `t_audit`;

CREATE TABLE `t_audit` (
  `id` char(32) NOT NULL COMMENT '审批表主键id',
  `username` char(32) DEFAULT NULL COMMENT '提交审批用户名',
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日报提交时间',
  `audit_content` varchar(255) NOT NULL COMMENT '申请的审批的内容',
  `audit_user_id` varchar(255) DEFAULT NULL COMMENT '审批人ID',
  `audit_user_name` varchar(255) DEFAULT NULL COMMENT '审批人名字',
  `audit_status` int(11) DEFAULT NULL COMMENT '审批意见状态,0/已阅,1/已处理,2/未处理',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `attendance_id` char(32) DEFAULT NULL COMMENT '审批的是补卡类型时,关联对应考勤',
  `audit_suggestion` int(11) DEFAULT NULL COMMENT '0/同意 , 1/拒绝',
  `business_type` int(11) DEFAULT NULL COMMENT '保留字段,暂定0为补卡新类型, 以后可增加请假等类型',
  `suggestion_remarksvarchar` varchar(255) DEFAULT NULL COMMENT '审批意见备注',
  `attendance_group` char(32) DEFAULT NULL COMMENT '提交审批所属组',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `attendance_id` (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_clazzes` */

DROP TABLE IF EXISTS `t_clazzes`;

CREATE TABLE `t_clazzes` (
  `id` char(32) NOT NULL COMMENT '班次表id',
  `nomal_address` varchar(50) NOT NULL COMMENT '设定正常打卡地点',
  `nomal_start_time` time NOT NULL COMMENT '正常上班时间',
  `nomal_end_time` time NOT NULL COMMENT '正常下班时间',
  `class_group_id` char(32) DEFAULT NULL COMMENT '用户所属组ID',
  `class_group` char(32) DEFAULT NULL COMMENT '用户所属组名字',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  `total` int(11) NOT NULL COMMENT '打卡应到人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_daily` */

DROP TABLE IF EXISTS `t_daily`;

CREATE TABLE `t_daily` (
  `id` char(32) NOT NULL COMMENT '日报表主键id',
  `username` char(32) NOT NULL COMMENT '日报提交用户名',
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
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `attendance_group` char(32) DEFAULT NULL COMMENT '提交审批所属组',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人ID',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新人ID',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_ID` (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_address` */

DROP TABLE IF EXISTS `t_group_address`;

CREATE TABLE `t_group_address` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `group_attendance_longitude` float(10,6) NOT NULL COMMENT '考勤组的经度',
  `group_attendance_dimension` float(10,6) NOT NULL COMMENT '考勤组的维度',
  `group_address` char(32) DEFAULT NULL COMMENT '考勤地址',
  `attendance_group_id` char(32) NOT NULL COMMENT '关联对应的考勤组',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_audit` */

DROP TABLE IF EXISTS `t_group_audit`;

CREATE TABLE `t_group_audit` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `audit_phone` char(32) DEFAULT NULL COMMENT '日报审核人手机号',
  `audit_name` char(32) DEFAULT NULL COMMENT '日报审核人名字',
  `enterprise_id` char(32) DEFAULT NULL COMMENT '企业id',
  `enterprise_name` char(32) DEFAULT NULL COMMENT '企业名称（保留字段）',
  `daily_rule_id` char(32) NOT NULL COMMENT '关联对应的日报表',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_daily` */

DROP TABLE IF EXISTS `t_group_daily`;

CREATE TABLE `t_group_daily` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `daily_name` char(32) DEFAULT NULL COMMENT '日报模板名字',
  `daily_address` varchar(255) DEFAULT NULL COMMENT '日报模板地址(html地址)',
  `daily_content` char(32) DEFAULT NULL COMMENT '日报内容(注：格式为json格式)',
  `daily_status` int(11) DEFAULT '1' COMMENT '日报状态(注0/启用 1/停用)',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_daily_rule` */

DROP TABLE IF EXISTS `t_group_daily_rule`;

CREATE TABLE `t_group_daily_rule` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `rule_daily_type` int(11) DEFAULT NULL COMMENT '0/日报 1/周报',
  `rule_daily_week` char(32) DEFAULT NULL COMMENT '(注：1-7表示提示日期)',
  `rule_template_id` char(32) DEFAULT NULL COMMENT '关联日报模板模块 ',
  `attendance_group_id` char(32) NOT NULL COMMENT '关联对应考勤组',
  `rule_daily_status` int(11) DEFAULT NULL COMMENT '0/立即启用 1/停用',
  `rule_daily_reserve` date DEFAULT NULL COMMENT '用户预订的生效时间（待定是年月日）',
  `rule_deadline` date DEFAULT NULL COMMENT '生效时间从-至 至的话默认2099年',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_personnel` */

DROP TABLE IF EXISTS `t_group_personnel`;

CREATE TABLE `t_group_personnel` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `personnel_phone` char(32) NOT NULL COMMENT '考勤人员手机号',
  `personnel_name` char(32) DEFAULT NULL COMMENT '考勤人员用户名',
  `enterprise_id` char(32) NOT NULL COMMENT '企业id',
  `enterprise_name` char(32) DEFAULT NULL COMMENT '企业名称（保留字段)',
  `attendance_group_id` char(32) NOT NULL COMMENT '关联对应的考勤组',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_group_rule` */

DROP TABLE IF EXISTS `t_group_rule`;

CREATE TABLE `t_group_rule` (
  `id` char(32) NOT NULL COMMENT 'UUID主键',
  `group_name` char(32) DEFAULT NULL COMMENT '考勤组名',
  `group_enterprise_id` char(32) NOT NULL COMMENT '企业id (保留字段)',
  `group_enterprise_name` char(32) DEFAULT NULL COMMENT '企业名称(保留字段)',
  `group_status` int(11) DEFAULT '1' COMMENT '0/启用 1/停用 默认1',
  `group_deadline` date DEFAULT NULL COMMENT '生效时间从-至 至的话默认2099年',
  `group_daily_id` char(32) DEFAULT NULL COMMENT '考勤组启用日报模板ID',
  `group_attendance_way` int(11) DEFAULT NULL COMMENT '考勤方式 0/自由 1/时长',
  `group_attendance_start` time NOT NULL COMMENT '组考勤上班时间',
  `group_attendance_end` time NOT NULL COMMENT '组考勤下班时间',
  `group_attendance_duration` float(10,6) NOT NULL COMMENT '考勤的时长（注：单位小时，如8.5小时)',
  `group_attendance_week` char(32) DEFAULT NULL COMMENT '组考勤的周',
  `group_attendance_longitude` float(10,6) NOT NULL COMMENT '组考勤的经度',
  `group_attendance_dimension` float(10,6) NOT NULL COMMENT '组考勤的维度',
  `group_address` char(32) DEFAULT NULL COMMENT '考勤地址',
  `group_attendance_scope` int(11) NOT NULL COMMENT '组考勤的范围（注：单位米 如1000米）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_statistics` */

DROP TABLE IF EXISTS `t_statistics`;

CREATE TABLE `t_statistics` (
  `id` char(32) NOT NULL COMMENT '统计表主键id',
  `late_time` int(11) DEFAULT NULL COMMENT '迟到次数',
  `early_time` int(11) DEFAULT NULL COMMENT '早退次数',
  `office_time` int(11) DEFAULT NULL COMMENT '上班时长',
  `daily_time` int(11) DEFAULT NULL COMMENT '补填日报次数',
  `remarks` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `create_by` char(32) DEFAULT NULL COMMENT '创建用户手机',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '更新用户手机',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` char(32) NOT NULL COMMENT '用户名',
  `attendance_group` char(32) DEFAULT NULL COMMENT '统计所属组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
