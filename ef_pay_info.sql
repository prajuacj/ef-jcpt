CREATE TABLE `ef_pay_info` (
  `flow_id` varchar(32) NOT NULL DEFAULT '' COMMENT '流水号',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `order_content` varchar(1000) DEFAULT NULL COMMENT '订单内容',
  `pay_amount` decimal(13,2) DEFAULT NULL,
  `pay_channel` varchar(1) DEFAULT NULL COMMENT '支付渠道（1：支付宝；2：微信）',
  `pay_status` varchar(2) DEFAULT NULL COMMENT '支付状态（00：初始；01：支付成功；02：支付失败）',
  `hand_fee` decimal(10,0) DEFAULT NULL COMMENT '手续费',
  `request_param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `response_content` varchar(2000) DEFAULT NULL,
  `pay_memo` varchar(1000) DEFAULT NULL COMMENT '支付备注',
  `fail_reason` varchar(1000) DEFAULT NULL COMMENT '失败原因',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;