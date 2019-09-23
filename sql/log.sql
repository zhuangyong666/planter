/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : planter

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-09-23 17:00:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg` varchar(255) DEFAULT NULL COMMENT '消息',
  `msglen` int(11) DEFAULT NULL COMMENT '消息体长度',
  `device_address` varchar(255) DEFAULT NULL COMMENT '设备地址',
  `seeds_num` float(11,0) DEFAULT NULL COMMENT '播种数',
  `seeded_area` float(255,0) DEFAULT NULL COMMENT '播种面积（单位：平方米）',
  `position` varchar(255) DEFAULT NULL COMMENT '播种机坐标位置',
  `state` varchar(255) DEFAULT NULL COMMENT '播种机下种状态(0：正常下种，1：下种故障)',
  `fault` varchar(11) DEFAULT NULL COMMENT '播种机下种故障状态累计',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
