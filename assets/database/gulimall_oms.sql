/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.56.10
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : 192.168.56.10:3306
 Source Schema         : gulimall_oms

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 11/11/2021 20:13:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1, 3, '202111071718565311457276371603574786', NULL, '2021-11-07 09:18:57', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:18:57');
INSERT INTO `oms_order` VALUES (2, 3, '202111071723095921457277433005756417', NULL, '2021-11-07 09:23:10', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:23:10');
INSERT INTO `oms_order` VALUES (3, 3, '202111071734046401457280180493070338', NULL, '2021-11-07 09:34:05', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-07 09:34:05');
INSERT INTO `oms_order` VALUES (4, 3, '202111081640424341457629137341517826', NULL, '2021-11-08 08:40:43', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:40:43');
INSERT INTO `oms_order` VALUES (5, 3, '202111081649394841457631389875703810', NULL, '2021-11-08 08:49:40', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:49:40');
INSERT INTO `oms_order` VALUES (6, 3, '202111081655017521457632741561470978', NULL, '2021-11-08 08:55:02', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 08:55:02');
INSERT INTO `oms_order` VALUES (7, 3, '202111081702353331457634644013879298', NULL, '2021-11-08 09:02:35', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 09:02:35');
INSERT INTO `oms_order` VALUES (8, 3, '202111081747101951457645863219232770', NULL, '2021-11-08 09:47:11', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 09:47:11');
INSERT INTO `oms_order` VALUES (9, 3, '202111081819115271457653921852948482', NULL, '2021-11-08 10:19:12', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 10:19:12');
INSERT INTO `oms_order` VALUES (10, 3, '202111081917559831457668704501301250', NULL, '2021-11-08 11:17:56', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 11:17:56');
INSERT INTO `oms_order` VALUES (11, 3, '202111081930418701457671916868390913', NULL, '2021-11-08 11:30:42', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-08 11:30:42');
INSERT INTO `oms_order` VALUES (12, 3, '202111090917014001457879868489457665', NULL, '2021-11-09 01:17:02', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:17:02');
INSERT INTO `oms_order` VALUES (13, 3, '202111090938364701457885300406652929', NULL, '2021-11-09 01:38:37', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:38:37');
INSERT INTO `oms_order` VALUES (14, 3, '202111090942369581457886309069656065', NULL, '2021-11-09 01:42:37', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:42:37');
INSERT INTO `oms_order` VALUES (15, 3, '202111090944368551457886811954122753', NULL, '2021-11-09 01:44:37', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 01:44:37');
INSERT INTO `oms_order` VALUES (16, 3, '202111091053311131457904152314130433', NULL, '2021-11-09 02:53:31', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 02:53:31');
INSERT INTO `oms_order` VALUES (17, 3, '202111091552313301457979399029719041', NULL, '2021-11-09 07:52:32', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:52:32');
INSERT INTO `oms_order` VALUES (18, 3, '202111091553359751457979670157918209', NULL, '2021-11-09 07:53:36', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:53:36');
INSERT INTO `oms_order` VALUES (19, 3, '202111091555034751457980037159518210', NULL, '2021-11-09 07:55:04', 'rnzhiw', 200768.0000, 200803.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 200768, 200768, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:55:04');
INSERT INTO `oms_order` VALUES (20, 3, '202111091558431861457980958690050049', NULL, '2021-11-09 07:58:43', 'rnzhiw', 200768.0000, 200803.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 200768, 200768, NULL, NULL, NULL, NULL, NULL, 'rnzhiw', '13588812345', NULL, '浙江省', NULL, NULL, '浙江省杭州市', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 07:58:43');
INSERT INTO `oms_order` VALUES (21, 3, '202111091602359231457981934880694273', NULL, '2021-11-09 08:02:36', 'rnzhiw', 200768.0000, 200803.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 200768, 200768, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 08:02:36');
INSERT INTO `oms_order` VALUES (22, 3, '202111091619498841457986271614869505', NULL, '2021-11-09 08:19:50', 'rnzhiw', 200768.0000, 200803.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 200768, 200768, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-11-09 08:19:50');
INSERT INTO `oms_order` VALUES (23, 3, '202111091644414671457992527763218434', NULL, '2021-11-09 08:44:42', 'rnzhiw', 194469.0000, 194504.0000, 35.0000, 0.0000, 0.0000, 0.0000, NULL, 1, NULL, 1, NULL, NULL, 7, 194469, 194469, NULL, NULL, NULL, NULL, NULL, '阮治玮', '13588891714', NULL, '浙江省', NULL, NULL, '浙江省台州市椒江区', NULL, 0, 0, NULL, '2021-11-09 08:45:00', NULL, NULL, NULL, '2021-11-09 08:45:00');
INSERT INTO `oms_order` VALUES (24, 3, '202111101844263741458385051329781762', NULL, '2021-11-10 10:44:27', NULL, NULL, 10.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (25, 3, '202111101847358281458385845965901826', NULL, '2021-11-10 10:47:36', NULL, NULL, 10.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (26, 3, '202111101830443211458381603406401538', NULL, '2021-11-10 10:30:45', NULL, NULL, 10.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单项信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1, NULL, '202111071718565311457276371603574786', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (2, NULL, '202111071718565311457276371603574786', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (3, NULL, '202111071723095921457277433005756417', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (4, NULL, '202111071723095921457277433005756417', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (5, NULL, '202111071734046401457280180493070338', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (6, NULL, '202111071734046401457280180493070338', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (7, NULL, '202111081640424341457629137341517826', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (8, NULL, '202111081640424341457629137341517826', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (9, NULL, '202111081649394841457631389875703810', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (10, NULL, '202111081649394841457631389875703810', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (11, NULL, '202111081655017521457632741561470978', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (12, NULL, '202111081655017521457632741561470978', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (13, NULL, '202111081702353331457634644013879298', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (14, NULL, '202111081702353331457634644013879298', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (15, NULL, '202111081747101951457645863219232770', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (16, NULL, '202111081747101951457645863219232770', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (17, NULL, '202111081819115271457653921852948482', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (18, NULL, '202111081819115271457653921852948482', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (19, NULL, '202111081917559831457668704501301250', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (20, NULL, '202111081917559831457668704501301250', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (21, NULL, '202111081930418701457671916868390913', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (22, NULL, '202111081930418701457671916868390913', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (23, NULL, '202111090917014001457879868489457665', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (24, NULL, '202111090917014001457879868489457665', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (25, NULL, '202111090938364701457885300406652929', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (26, NULL, '202111090938364701457885300406652929', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (27, NULL, '202111090942369581457886309069656065', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (28, NULL, '202111090942369581457886309069656065', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (29, NULL, '202111090944368551457886811954122753', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (30, NULL, '202111090944368551457886811954122753', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (31, NULL, '202111091053311131457904152314130433', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (32, NULL, '202111091053311131457904152314130433', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (33, NULL, '202111091552313301457979399029719041', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (34, NULL, '202111091552313301457979399029719041', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (35, NULL, '202111091553359751457979670157918209', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (36, NULL, '202111091553359751457979670157918209', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (37, NULL, '202111091555034751457980037159518210', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (38, NULL, '202111091555034751457980037159518210', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 31, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 195269.0000, 195269, 195269);
INSERT INTO `oms_order_item` VALUES (39, NULL, '202111091558431861457980958690050049', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (40, NULL, '202111091558431861457980958690050049', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 31, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 195269.0000, 195269, 195269);
INSERT INTO `oms_order_item` VALUES (41, NULL, '202111091602359231457981934880694273', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (42, NULL, '202111091602359231457981934880694273', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 31, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 195269.0000, 195269, 195269);
INSERT INTO `oms_order_item` VALUES (43, NULL, '202111091619498841457986271614869505', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (44, NULL, '202111091619498841457986271614869505', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 31, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 195269.0000, 195269, 195269);
INSERT INTO `oms_order_item` VALUES (45, NULL, '202111091644414671457992527763218434', 18, 'huawei', NULL, NULL, 225, 27, 'huawei 黑色 4GB', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', 5499.0000, 1, '颜色：黑色;内存：4GB', 0.0000, 0.0000, 0.0000, 5499.0000, 5499, 5499);
INSERT INTO `oms_order_item` VALUES (46, NULL, '202111091644414671457992527763218434', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, 3, '华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄4G全网通手机', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', 6299.0000, 30, '颜色：亮黑色;版本：8GB+256GB', 0.0000, 0.0000, 0.0000, 188970.0000, 188970, 188970);
INSERT INTO `oms_order_item` VALUES (47, NULL, '202111101844263741458385051329781762', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 10.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (48, NULL, '202111101847358281458385845965901826', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 10.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (49, NULL, '202111101830443211458381603406401538', 11, '华为 HUAWEI Mate 30 Pro', NULL, NULL, 225, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 10.0000, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_operate_history
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单退货申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_return_apply
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退货原因' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_return_reason
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `orderSn`(`order_sn`) USING BTREE,
  UNIQUE INDEX `alipayTradeNo`(`alipay_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------
INSERT INTO `oms_payment_info` VALUES (1, '202111091644414671457992527763218434', NULL, '2021110922001473790502379543', 194504.0000, '颜色：黑色;内存：4GB', 'TRADE_SUCCESS', '2021-11-09 08:45:00', NULL, NULL, '2021-11-09 08:44:59');

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退款信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_refund_info
-- ----------------------------

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

SET FOREIGN_KEY_CHECKS = 1;
