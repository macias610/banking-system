INSERT IGNORE INTO Banks (id, name, swift) VALUES (1, 'Chestnut bank', '20879576');
INSERT IGNORE INTO Users (id, employee_number, first_name, surname, bank_id) VALUES (1, UUID(), 'Nikola', 'Kowalska', 1);
INSERT IGNORE INTO Users (id, employee_number, first_name, surname, bank_id) VALUES (2, UUID(), 'Użytkownik', 'Systemowy', 1);