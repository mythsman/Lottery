DROP TABLE  IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `period` varchar(20) NOT NULL,
  `result` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `period_index` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;