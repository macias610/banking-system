INSERT IGNORE INTO banks (id, name, swift) VALUES (1, 'Chestnut bank', '20879576');
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (1, UUID(), 'Nikola', 'Kowalska', 1);
INSERT IGNORE INTO users (id, employee_number, first_name, surname, bank_id) VALUES (2, UUID(), 'Użytkownik', 'Systemowy', 1);
INSERT IGNORE INTO client_statuses(id, name) VALUES (1, 'client active');
INSERT IGNORE INTO client_types(id, value) VALUES (1, 'individual client');
INSERT IGNORE INTO client_types(id, value) VALUES (1, 'commercial client');
