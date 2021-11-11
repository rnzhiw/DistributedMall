/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.56.10
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : 192.168.56.10:3306
 Source Schema         : gulimall_wms

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 11/11/2021 20:13:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '采购单id',
  `assignee_id` bigint(20) NULL DEFAULT NULL COMMENT '采购人id',
  `assignee_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采购人名',
  `phone` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `priority` int(4) NULL DEFAULT NULL COMMENT '优先级',
  `status` int(4) NULL DEFAULT NULL COMMENT '状态',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '总金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '采购信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (1, 2, 'rnzhiw', '13588891714', 1, 2, NULL, NULL, NULL, '2021-09-21 07:11:21');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (1, 1, 1, 10, NULL, 1, 2);
INSERT INTO `wms_purchase_detail` VALUES (2, 1, 2, 12, NULL, 2, 2);

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '1号仓库', '北京xxx', '124');
INSERT INTO `wms_ware_info` VALUES (2, '2号仓库', '上海xxx', '125');
INSERT INTO `wms_ware_info` VALUES (3, '3号仓库', '浙江xxx', '126');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) NULL DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (1, NULL, '202111071718565311457276371603574786', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:18:57', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (2, NULL, '202111071723095921457277433005756417', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:23:10', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (3, NULL, '202111071734046401457280180493070338', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:34:05', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (4, NULL, '202111081640424341457629137341517826', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:40:43', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (5, NULL, '202111081649394841457631389875703810', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:49:40', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (6, NULL, '202111081655017521457632741561470978', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:55:02', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (7, NULL, '202111081702353331457634644013879298', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 09:02:36', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (8, NULL, '202111081747101951457645863219232770', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 09:47:11', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (9, NULL, '202111081819115271457653921852948482', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 10:19:12', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (10, NULL, '202111081917559831457668704501301250', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 11:17:56', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (11, NULL, '202111081930418701457671916868390913', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-08 11:30:42', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (12, NULL, '202111090917014001457879868489457665', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:17:02', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (13, NULL, '202111090938364701457885300406652929', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:38:37', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (14, NULL, '202111090942369581457886309069656065', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:42:37', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (15, NULL, '202111090944368551457886811954122753', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:44:37', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (16, NULL, '202111091053311131457904152314130433', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 02:53:32', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (17, NULL, '202111091552313301457979399029719041', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:52:32', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (18, NULL, '202111091553359751457979670157918209', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:53:36', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (19, NULL, '202111091555034751457980037159518210', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:55:04', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (20, NULL, '202111091558431861457980958690050049', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:58:43', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (21, NULL, '202111091602359231457981934880694273', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 08:02:36', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (22, NULL, '202111091619498841457986271614869505', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 08:19:50', NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (23, NULL, '202111091644414671457992527763218434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-11-09 08:44:42', NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `lock_status` int(1) NULL DEFAULT NULL COMMENT '1-锁定 2-解锁 3-扣减',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (9, 27, '', 1, 8, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (10, 3, '', 30, 8, 100, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (11, 27, '', 1, 9, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (12, 3, '', 30, 9, 100, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (13, 27, '', 1, 10, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (14, 3, '', 30, 10, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (15, 27, '', 1, 11, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (16, 3, '', 30, 11, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (17, 27, '', 1, 12, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (18, 3, '', 30, 12, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (19, 27, '', 1, 13, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (20, 3, '', 30, 13, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (21, 27, '', 1, 14, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (22, 3, '', 30, 14, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (23, 27, '', 1, 15, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (24, 3, '', 30, 15, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (25, 27, '', 1, 16, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (26, 3, '', 30, 16, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (27, 27, '', 1, 17, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (28, 3, '', 30, 17, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (29, 27, '', 1, 18, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (30, 3, '', 30, 18, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (31, 27, '', 1, 19, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (32, 3, '', 31, 19, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (33, 27, '', 1, 20, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (34, 3, '', 31, 20, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (35, 27, '', 1, 21, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (36, 3, '', 31, 21, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (37, 27, '', 1, 22, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (38, 3, '', 31, 22, 100, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (39, 27, '', 1, 23, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (40, 3, '', 30, 23, 100, 1);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) NULL DEFAULT NULL COMMENT '锁定库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (1, 27, 1, 10, 'huawei', 1);
INSERT INTO `wms_ware_sku` VALUES (2, 3, 100, 100, 'huawei', 30);

SET FOREIGN_KEY_CHECKS = 1;
