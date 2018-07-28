CREATE TABLE `ef_order_info` (
  `order_id` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `product_id` varchar(32) DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `price` decimal(13,2) DEFAULT NULL COMMENT '单价',
  `product_num` int(11) DEFAULT NULL COMMENT '产品数量',
  `total_amount` decimal(13,2) DEFAULT NULL COMMENT '总金额',
  `discount_amount` decimal(13,2) DEFAULT NULL COMMENT '优惠金额',
  `pay_amount` decimal(13,2) DEFAULT NULL COMMENT '实付金额',
  `order_status` varchar(2) DEFAULT NULL COMMENT '订单状态（00：初始，01：已支付；02：已完成；03：取消）',
  `remark` varchar(2000) DEFAULT NULL COMMENT '订单备注',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;