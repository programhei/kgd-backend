
/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50744
Source Host           : localhost:3306
Source Database       : kgd

Target Server Type    : MYSQL
Target Server Version : 50744
File Encoding         : 65001

Date: 2025-12-30 14:16:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for intelligent_decision
-- ----------------------------
DROP TABLE IF EXISTS `intelligent_decision`;
CREATE TABLE `intelligent_decision` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_type` varchar(60) DEFAULT NULL COMMENT '数据类型',
  `data_content` varchar(60) DEFAULT NULL COMMENT '数据',
  `value_type` varchar(10) DEFAULT NULL COMMENT '值类型',
  `interface_meaning` varchar(100) DEFAULT NULL COMMENT '接口含义',
  `importance_level` varchar(10) DEFAULT NULL COMMENT '重要等级',
  `remark` text COMMENT '备注',
  `create_by` varchar(10) DEFAULT NULL COMMENT '记录创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '记录创建时间',
  `update_by` varchar(10) DEFAULT NULL COMMENT '记录更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '记录更新时间',
  `data_kind` varchar(60) DEFAULT NULL COMMENT '数据种类',
  `serial_number` varchar(10) DEFAULT NULL COMMENT '序号',
  `is_missing` varchar(10) DEFAULT NULL COMMENT '接口中是否缺失',
  `is_format_correct` varchar(10) DEFAULT NULL COMMENT '格式是否正确',
  `is_input` varchar(10) DEFAULT NULL COMMENT '是否是输入接口',
  `dimension` varchar(60) DEFAULT NULL COMMENT '维度',
  `interface_type` varchar(60) DEFAULT NULL COMMENT '接口类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5449 DEFAULT CHARSET=utf8mb4 COMMENT='支持智能决策模型生成与评估';

-- ----------------------------
-- Records of intelligent_decision
-- ----------------------------
INSERT INTO `intelligent_decision` VALUES ('5379', '运动学模型', '本机经度', 'double', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '1. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5380', '运动学模型', '本机纬度', 'double', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '2. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5381', '运动学模型', '本机高度', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '3. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5382', '运动学模型', '本机航向角', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '4. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5383', '运动学模型', '本机俯仰角', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '5. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5384', '运动学模型', '本机滚转角', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '6. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5385', '运动学模型', '本机速度X轴', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '7. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5386', '运动学模型', '本机速度Y轴', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '8. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5387', '运动学模型', '本机速度Z轴', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '9. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5388', '运动学模型', '迎角', 'double', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '10. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5389', '运动学模型', '侧滑角', 'double', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '11. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5390', '运动学模型', '法向过载', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '12. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5391', '气动参数', '体轴滚转角速度', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '13. ', '是', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5392', '气动参数', '体轴俯仰角速度', 'float', '', '1', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '14. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5393', '气动参数', '体轴偏航角速度', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '15. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5394', '气动参数', '本机马赫数', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '16. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5395', '发动机模型', '本机耗油率', 'int', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '17. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5396', '发动机模型', '燃油消耗', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '18. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5397', '发动机模型', '油门位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '19. ', '是', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5398', '气动参数', '左升降舵位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '20. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5399', '气动参数', '右升降舵位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '21. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5400', '气动参数', '左副翼位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '22. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5401', '气动参数', '右副翼位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '23. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5402', '气动参数', '方向舵位置', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '24. ', '否', '', null, '飞行仿真', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5403', '火控雷达模型', '雷达开关机状态', 'int', '0-关机，1-开机', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '25. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5404', '火控雷达模型', '雷达扫描方式', 'int', '0-NA，1-RWS，2-TWS，3-TAS', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '26. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5405', '火控雷达模型', '俯仰扫描范围', 'int', '10度，30度，60度，90度', '1', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '27. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5406', '火控雷达模型', '方位扫描范围', 'int', '10度，30度，60度，90度', '1', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '28. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5407', '火控雷达模型', '跟踪目标数量', 'int', '0~20', '1', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '29. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5408', '火控雷达模型', '目标批号', 'int', '', '1', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '30. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5409', '火控雷达模型', '雷达对该目标状态', 'int', '0-搜索，1-跟踪，2-记忆跟踪', '1', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '31. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5410', '火控雷达模型', '目标距离', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '32. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5411', '火控雷达模型', '目标方位角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '33. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5412', '火控雷达模型', '目标俯仰角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '34. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5413', '火控雷达模型', '目标径向速度', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '35. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5414', '火控雷达模型', '目标速度X轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '36. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5415', '火控雷达模型', '目标速度Y轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '37. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5416', '火控雷达模型', '目标速度Z轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '38. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5417', '火控雷达模型', '目标RCS', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '39. ', '', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5418', '导弹模型', '本机剩余弹量', 'int', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '40. ', '是', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5420', '导弹模型', '导弹经度', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '42. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5421', '导弹模型', '导弹纬度', 'double', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '43. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5422', '导弹模型', '导弹高度', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '44. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5423', '导弹模型', '导弹航向角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '45. ', '是', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5424', '导弹模型', '导弹俯仰角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '46. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5425', '导弹模型', '导弹滚转角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '47. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5426', '导弹模型', '导弹速度X轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '48. ', '是', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5427', '导弹模型', '导弹速度Y轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '49. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5428', '导弹模型', '导弹速度Z轴', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '50. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5429', '导弹模型', '加力状态', 'int', '0-未加力，1-加力', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '51. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5430', '导弹模型', '导弹导引头截获状态', 'int', '0-未截获，1-截获', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:15:23', '支持智能决策模型生成与评估', '52. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5431', '航炮模型', '射程', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:27:38', '支持智能决策模型生成与评估', '53. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5432', '航炮模型', '发射前置角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:27:38', '支持智能决策模型生成与评估', '54. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5433', '航炮模型', '射速', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:27:38', '支持智能决策模型生成与评估', '55. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5434', '航炮模型', '剩余弹药', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:27:38', '支持智能决策模型生成与评估', '56. ', '否', '', null, '武器系统', '状态空间接口');
INSERT INTO `intelligent_decision` VALUES ('5435', '飞行控制接口信息', '机动方式', 'int', '0-平飞，1-平飞加减速，2-水平转弯，3-俯冲，4-最速爬升，5-中断', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '57. ', '', '', null, '', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5436', '飞行控制接口信息', '期望高度', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '58. ', '', '', null, '', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5437', '飞行控制接口信息', '期望马赫数', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '59. ', '', '', null, '', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5438', '飞行控制接口信息', '期望过载', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '60. ', '', '', null, '', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5439', '飞行控制接口信息', '期望航向', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '61. ', '', '', null, '', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5440', '雷达控制接口', '雷达开关机', 'int', '0-关机，1-开机', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '62. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5441', '雷达控制接口', '雷达扫描方式', 'int', '0-NA，1-RWS，2-TWS，3-TAS', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '63. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5442', '雷达控制接口', '俯仰扫描范围', 'int', '10度，30度，60度，90度', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '64. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5443', '雷达控制接口', '方位扫描范围', 'int', '10度，30度，60度，90度', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '65. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5444', '雷达控制接口', '扫描中心俯仰角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '66. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5445', '雷达控制接口', '扫描中心方位角', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', null, null, '支持智能决策模型生成与评估', '67. ', '', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5446', '武器控制模型', '武器发射', 'int', '0-关机，1-开机', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:18:32', '支持智能决策模型生成与评估', '68. ', '否', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5447', '武器控制模型', '航炮发射', 'int', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-29 16:18:32', '支持智能决策模型生成与评估', '69. ', '否', '', null, '武器系统', '动作空间接口');
INSERT INTO `intelligent_decision` VALUES ('5448', '气动参数', '本机剩余弹量', 'float', '', '2', '', 'admin', '2025-11-30 05:35:14', 'admin', '2025-12-30 14:09:48', '支持智能决策模型生成与评估', '70. ', '是', '', '', '飞行仿真', '状态空间接口');