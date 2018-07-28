CREATE TABLE `ef_user_info` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `login_password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `cert_type` varchar(2) DEFAULT NULL COMMENT '证件类型',
  `cert_code` varchar(255) DEFAULT NULL COMMENT '证件号码',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `mifi_serial` varchar(255) DEFAULT NULL COMMENT 'mifi序列号',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `country` varchar(255) DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;