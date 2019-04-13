INSERT IGNORE INTO banks (id, name, swift) VALUES (1, 'Chestnut bank', '20879576');
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (1, UUID(), 'Nikola', 'Kowalska', 1);
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (2, UUID(), 'Użytkownik', 'Systemowy', 1);

--Dummy data for deposit module
INSERT INTO `account_info` (`id`,`available_amount`,`locked_amount`) VALUES (1,1000,200);
INSERT INTO `accounts` (`id`,`iban`,`is_active`,`is_blocked`,`number_banking_account`,`number_client_account`,`type`,`client_id`,`currency_id`,`info_id`) VALUES (1,NULL,CONV('1', 2, 10) + 0,CONV('0', 2, 10) + 0,NULL,NULL,NULL,1,1,1);
INSERT INTO `capitalization` (`capitalization_id`,`days_period`,`type`) VALUES (1,30,'MONTHLY');
INSERT INTO `client_statuses`(`id`, `name`) VALUES(1,NULL);
INSERT INTO `client_types`(`id`,`value`) VALUES(1,'TYPOWY');
INSERT INTO `clients`(`id`,`bank_id`,`created_at`,`deleted_at`,`is_active`,`uuid`,`client_info_id`,`client_status_id`,`client_type_id`,`created_by`,`credit_category_id`,`deleted_by`,`introductor_id`) VALUES (1,	1,'2019-04-13 11:37:36','2019-04-13 11:37:36',CONV('1', 2, 10) + 0,NULL,1,1,1,NULL,NULL,NULL,NULL);
INSERT INTO `clients_info` (`id`, `birthday`, `country`, `first_name`, `lang`, `nationality`, `pesel`, `surname`) VALUES (1,'2019-04-13','PL','Janysz','1',NULL,NULL,NULL);
INSERT INTO `currencies` (`id`, `name`) VALUES(1,	'DOLAR');
INSERT INTO `deposit`(`deposit_id`,`end_date`,`is_active`,`start_date`,`account_id`,`deposit_type_id`,`amount`) VALUES (1,'2019-04-05',CONV('1', 2, 10) + 0,'2019-04-05',1,1,4000);
INSERT INTO `deposit_type`(`deposit_type_id`,`max_amount`,`days_period`,`interest_rate`,`min_amount`,`name`,`capitalization_id`) VALUES (1,5000,120,3,1000,'5000+',1);
INSERT INTO `hibernate_sequence` (`next_val`) VALUES (1),(1),(1),(1),(1),(1),(1),(1),(1),(1);
INSERT INTO `operation`(`operation_id`,`date`,`type`,`deposit_id`) VALUES (1,'2019-04-05','OPENING',1);