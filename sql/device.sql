/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : planter

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-09-23 17:23:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_address` varchar(255) DEFAULT NULL COMMENT '设备地址',
  `state` int(1) DEFAULT NULL COMMENT '设备状态(0离线，1在线)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
