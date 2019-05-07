INSERT IGNORE INTO banks (id, country, name, swift) VALUES (1, 'PL', 'Chestnut bank', '24900005');
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (1, UUID(), 'Nikola', 'Kowalska', 1);
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (2, UUID(), 'Użytkownik', 'Systemowy', 1);
INSERT IGNORE INTO client_types(id, value) VALUES (1, 'individual client');
INSERT IGNORE INTO client_types(id, value) VALUES (1, 'commercial client');
--
-- Zrzut danych tabeli `clients`
--

INSERT IGNORE INTO `clients` (`id`, `created_at`, `deleted_at`, `is_active`, `uuid`, `bank_id`, `client_info_id`, `client_status`, `client_type_id`, `created_by`, `credit_category_id`, `deleted_by`, `introductor_id`) VALUES
(1, '2019-04-16 06:24:37', NULL, b'1', '0013571228849587694472263648613518347956', 1, 1, 'client active', 1, 1, NULL, NULL, NULL),
(2, '2019-04-16 06:25:22', NULL, b'1', '0280984408403301778819565455426516977997', 1, 2, 'client active', 1, 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Zrzut danych tabeli `clients_info`
--

INSERT IGNORE INTO `clients_info` (`id`, `birthday`, `country`, `first_name`, `lang`, `nationality`, `pesel`, `surname`) VALUES
(1, '1995-07-06', 'PL', 'Jakub', 'PL', 'PL', 95060748971, 'Jsonowy'),
(2, '1986-11-08', 'PL', 'Andrzej', 'PL', 'PL', 86100949513, 'Nowak');

-- --------------------------------------------------------

-- --------------------------------------------------------
--
-- Zrzut danych tabeli `contacts`
--

INSERT IGNORE INTO `contacts` (`id`, `created_at`, `type`, `value`, `client_id`, `created_by`) VALUES
(1, '2019-04-16 06:24:37', 'PHONE', '948362018', 1, 1),
(2, '2019-04-16 06:24:37', 'EMAIL', 'jsonowiec@gmail.com', 1, 1),
(3, '2019-04-16 06:25:22', 'PHONE', '123123123', 2, 1),
(4, '2019-04-16 06:25:22', 'EMAIL', 'warszawiak@gmail.com', 2, 1);

-- --------------------------------------------------------

--
-- Zrzut danych tabeli `documents`
--

INSERT IGNORE INTO `documents` (`id`, `type`, `value`, `client_id`) VALUES
(1, 'ID', 'HTD4521', 1),
(2, 'ID', 'AVG456A', 2);

-- --------------------------------------------------------

--
-- Zrzut danych tabeli `locations`
--

INSERT IGNORE INTO `locations` (`id`, `apartment_number`, `city`, `created_at`, `house_number`, `street`, `zip`, `client_id`, `created_by`) VALUES
(1, '9', 'Poznań', '2019-04-16 06:24:37', '2', 'Discordowa', '98-999', 1, 1),
(2, '12', 'Warszawa', '2019-04-16 06:25:22', '12', 'Uliczna', '98-123', 2, 1);

--
-- Zrzut danych providera
--
INSERT IGNORE INTO `clients` (`id`, `created_at`, `deleted_at`, `is_active`, `uuid`, `bank_id`, `client_info_id`, `client_status`, `client_type_id`, `created_by`, `credit_category_id`, `deleted_by`, `introductor_id`) VALUES
(3, '2019-04-16 06:24:37', NULL, b'1', '0013571228849587694472263648613518123456', 1, 3, 'client active', 1, 1, NULL, NULL, NULL);
INSERT IGNORE INTO `clients_info` (`id`, `birthday`, `country`, `first_name`, `lang`, `nationality`, `pesel`, `surname`) VALUES
(3, '1995-08-09', 'PL', 'Orange', 'PL', 'PL', 95080948971, 'Orange');
INSERT IGNORE INTO `contacts` (`id`, `created_at`, `type`, `value`, `client_id`, `created_by`) VALUES
(5, '2019-04-16 06:24:37', 'PHONE', '123456789', 1, 1);
INSERT IGNORE INTO `accounts` (`id`, `currency`, `iban`, `is_active`, `is_blocked`, `number_banking_account`, `number_client_account`, `type`, `client_id`, `info_id`) VALUES
(1, 'PLN', 'PL08249000058429035340935700', 1, 0, '08249000058429035340935700', '8429035340935700', 'provider', '3', '1');
INSERT IGNORE INTO `account_info` (`id`, `available_amount`, `locked_amount`) VALUES
(1, 0, 500);