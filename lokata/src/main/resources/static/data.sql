-- Adminer 4.7.0 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

INSERT INTO `accounts` (`account_id`, `client_id`, `info_id`, `is_active`, `is_blocked`, `currency_id`) VALUES
(1,	1,	1,	1,	0,	1);

INSERT INTO `capitalization` (`capitalization_id`, `days_period`, `type`) VALUES
(1,	30,	'MONTHLY');

INSERT INTO `currencies` (`currency_id`, `name`) VALUES
(1,	'DOLAR');

INSERT INTO `deposit` (`deposit_id`, `end_date`, `is_active`, `start_date`, `account_id`, `deposit_type_id`) VALUES
(1,	'2019-04-05',	CONV('1', 2, 10) + 0,	'2019-04-05',	1,	1);

INSERT INTO `deposit_type` (`deposit_type_id`, `amount`, `days_period`, `interest_rate`, `name`, `capitalization_id`, `currency_id`, `min_amount`) VALUES
(1,	5000,	120,	3,	'5000+',	1,	1,    1000);

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1),
(1),
(1),
(1),
(1),
(1);

INSERT INTO `operation` (`operation_id`, `date`, `type`, `deposit_id`) VALUES
(1,	'2019-04-05',	'OPENING',	1);

-- 2019-04-05 08:10:02