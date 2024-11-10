SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `PRODUCT_ATTRIBUTE_VALUE`;
TRUNCATE TABLE `ATTRIBUTE_VALUE`;
TRUNCATE TABLE `CATEGORY_ATTRIBUTE_TYPE`;
TRUNCATE TABLE `ATTRIBUTE_TYPE_LOCALE`;
TRUNCATE TABLE `ATTRIBUTE_TYPE`;
TRUNCATE TABLE `RMA_ORDER_LINE`;
TRUNCATE TABLE `RMA`;
TRUNCATE TABLE `TICKET_MESSAGE`;
TRUNCATE TABLE `TICKET_ORDER_LINE`;
TRUNCATE TABLE `TICKET`;
TRUNCATE TABLE `ORDER_LINE`;
TRUNCATE TABLE `CUSTOMER_ORDER`;
TRUNCATE TABLE `PRODUCT_LOCALE`;
TRUNCATE TABLE `PRODUCT`;
TRUNCATE TABLE `ADDRESS`;
TRUNCATE TABLE `CUSTOMER`;
TRUNCATE TABLE `EMPLOYEE_DEPARTMENT`;
TRUNCATE TABLE `EMPLOYEE`;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `EMPLOYEE` (`SUPERVISOR_ID`, `FIRST_NAME`, `LAST_NAME1`, `LAST_NAME2`, `DOCUMENT_TYPE_ID`, `DOCUMENT_NUMBER`, `PHONE`, `EMAIL`, `USERNAME`, `PASSWORD`, `CREATION_DATE`, `IBAN`, `BIC`)
VALUES
	(NULL, 'Alexandre', 'Perebaskine', NULL, 'PPT', '98765432C', 234567890, 'pereb27@gmail.com', 'aperebaskine', 'Y82KlkZFeEPL+eYX97iNgRq8iM2foVsblfynkK9Cd5C0wXOCuNf8ST18WnAG72mT', str_to_date('2022-11-16', '%Y-%m-%d'), 'ES6621000418402345678902', 'BANK789'), -- abc123.
	(NULL, 'Emily', 'Brown', NULL, 'NIE', '54321678D', 345678901, 'emily@example.com', 'ebrown', 'MWU7Q+RTk2Vdlo65QoAr8KSxreQWgtDx3sn8mMwz+DXOApoEHstgv9LSQgi+HxTo', str_to_date('2022-11-16', '%Y-%m-%d'), 'ES6621000418403456789013', 'BANKABC'), -- passwordabc
	(NULL, 'William', 'Garcia', NULL, 'PPT', 'EF123456', 456789012, 'william@example.com', 'wgarcia', 'ijprv3itRFygsLL8syLxA2yxIl2TfP7WT5I7VNImUTpzps6/c8VxSufOhHLs3lIz', str_to_date('2022-11-16', '%Y-%m-%d'), 'ES6621000418404567890124', 'BANKDEF'), -- passworddef
	(NULL, 'Sophia', 'Martinez', 'Rodriguez', 'NIF', '87654321G', 567890123, 'sophia@example.com', 'smartinez', 'LV4bprl0hYjuggOAIivTeks4fmFd2k9q/X/pxxMOtAgQfZDJvZ7n/c/O0jirUsH1', str_to_date('2022-11-16', '%Y-%m-%d'), 'ES6621000418405678901235', 'BANKGHI'), -- passwordghi
	(NULL, 'James', 'Lopez', NULL, 'NIE', '12345678H', 678901234, 'james@example.com', 'jlopez', 'mRrxNJu+t6g+z3Ifg0zGCQaelVte5FPWtHQSMu0gZqAXFBInZWpB0TL1XLp146E6', str_to_date('2023-01-16', '%Y-%m-%d'), 'ES6621000418406789012346', 'BANKJKL'), -- passwordjkl
	(NULL, 'Olivia', 'Gonzalez', 'Garcia', 'PPT', 'GH654321', 789012345, 'olivia@example.com', 'ogonzalez', 'qdRt6cveYJUI1WuToOd6Tq4XQcwKgdjLx3FCSY4xvjqQm0jw3iN7SqJwcrDuEr59', str_to_date('2023-01-16', '%Y-%m-%d'), 'ES6621000418407890123457', 'BANKMNO'), -- passwordmno
	(1, 'Rick', 'Astley', NULL, 'FOR', 'X1234567V', 666666666, 'never@gonnagiveyou.up', 'rastley', '3OdSndWVT6E8AwiYNljnGX8Pne9jXTUfWQNutJqLy5cUS/AzpjRlehdHd7Up2lco', str_to_date('2023-03-16', '%Y-%m-%d'), 'ES11111111111111', 'BANKPQR'); -- passwordpqr

INSERT INTO `EMPLOYEE_DEPARTMENT` (`EMPLOYEE_ID`, `DEPARTMENT_ID`, `START_DATE`, `END_DATE`)
VALUES
    (1, 'EXC', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (2, 'MKT', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (3, 'HRS', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (4, 'SAL', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (5, 'FIN', str_to_date('2023-01-16', '%Y-%m-%d'), NULL),
    (6, 'OPS', str_to_date('2023-01-16', '%Y-%m-%d'), str_to_date('2024-01-15', '%Y-%m-%d')),
    (6, 'SUP', str_to_date('2024-01-16', '%Y-%m-%d'), NULL),
    (7, 'SUP', str_to_date('2023-03-16', '%Y-%m-%d'), str_to_date('2023-12-16', '%Y-%m-%d')),
    (7, 'FIN', str_to_date('2023-12-17', '%Y-%m-%d'), NULL);

INSERT INTO `CUSTOMER` (`FIRST_NAME`, `LAST_NAME1`, `LAST_NAME2`, `DOCUMENT_TYPE_ID`, `DOCUMENT_NUMBER`, `PHONE`, `EMAIL`, `PASSWORD`, `CREATION_DATE`)
VALUES
	('Ethan', 'Davis', NULL, 'NIE', 'FG876543', 890123456, 'ethan@example.com', '2Qs/8kHxDLdb8FJBJzSpxJb+U2F7+kJ7LT7AQnTAKDZlR50ekYjwEv5fekvumtIh', str_to_date('2022-11-16', '%Y-%m-%d')),
	('Ava', 'Rodriguez', 'López', 'PPT', 'IJ123456', 901234567, 'ava@example.com', '6272a94b52b95b50a5304af63d146fe8c7151e094927437e0cf2919f212888ff', str_to_date('2022-11-16', '%Y-%m-%d')), -- passabc
	('Daniel', 'Wilson', NULL, 'NIF', 'KL654321', 123456789, 'daniel@example.com', '7913d6c6ca2d3c8913d830ca150460974d1aace643ae24bcfdf1e661862d2b9b', str_to_date('2022-11-16', '%Y-%m-%d')), -- passdef
	('Mia', 'Miller', NULL, 'NIE', 'MN234567', 234567890, 'mia@example.com', '15b412db9e0bb9edce6096d33ebcc956f6d86d4e993edecfadab267daf5ab666', str_to_date('2023-01-16', '%Y-%m-%d')), -- passghi
	('Alexander', 'Taylor', NULL, 'PPT', 'OP123456', 345678901, 'alexander@example.com', 'fe2fd51ff6cb2db6fe8dc0f77e318e646017824f0893e6f83e55cd4d6db65bfa', str_to_date('2023-01-16', '%Y-%m-%d')), -- passjkl
	('Chloe', 'Anderson', NULL, 'NIF', 'QR765432', 456789012, 'chloe@example.com', '8d643b0613bd258518528c24e0a07d51ee89c26e1d57775df782b2e3107e470c', str_to_date('2023-04-16', '%Y-%m-%d')), -- passmno
	('John', 'Cena', NULL, 'FOR', 'ST555555', 455555555, 'cant@see.me', 'dff1ad49ede4b12cd8bc9952c37b12a7557e5c33096a9e7e5b83db49a317da93', str_to_date('2023-04-16', '%Y-%m-%d')); -- passpqr
    
INSERT INTO `ADDRESS` (`CUSTOMER_ID`, `CITY_ID`, `STREET_NAME`, `STREET_NUMBER`, `FLOOR`, `DOOR`, `ZIP_CODE`, `IS_DEFAULT`, `IS_BILLING`, `CREATION_DATE`)
VALUES
	(1, 1, 'Calle Gran Vía', 1, 3, 'Apt 5', 28013, true, false, str_to_date('2022-11-16', '%Y-%m-%d')),
	(2, 6, 'Avinguda Diagonal', 123, NULL, 'Apt 10', 08019, true, true, str_to_date('2022-11-16', '%Y-%m-%d')),
	(3, 11, 'Carrer de la Pau', 45, 5, 'Apt 15', 46002, true, true, str_to_date('2022-11-16', '%Y-%m-%d')),
	(4, 16, 'Calle Sierpes', 8, NULL, 'Apt 20', 41004, true, true, str_to_date('2023-01-16', '%Y-%m-%d')),
	(5, 5, 'Carrer de les Flors', 27, 7, 'Apt 25', 08001, true, true, str_to_date('2023-01-16', '%Y-%m-%d')),
	(6, 6, 'Passeig de Gràcia', 33, NULL, 'Apt 30', 08007, true, true, str_to_date('2023-04-16', '%Y-%m-%d')),
	(7, 8, 'Calle León y Castillo', 271, 9, 'Apt 35', 35005, true, true, str_to_date('2023-04-16', '%Y-%m-%d')),
    (1, 1, 'Calle Alcalá', 100, 4, 'Apt 6', 28009, false, true, str_to_date('2023-07-16', '%Y-%m-%d')),
	(1, 1, 'Calle del Arenal', 67, 6, 'Apt 7', 28013, false, false, str_to_date('2023-08-16', '%Y-%m-%d')),
	(2, 6, 'Rambla de Catalunya', 89, 2, 'Apt 12', 08008, false, false, str_to_date('2023-09-16', '%Y-%m-%d'));

    
INSERT INTO `ADDRESS` (`EMPLOYEE_ID`, `CITY_ID`, `STREET_NAME`, `STREET_NUMBER`, `FLOOR`, `DOOR`, `ZIP_CODE`, `IS_DEFAULT`, `IS_BILLING`, `CREATION_DATE`)
VALUES
    (1, 2, 'Calle Mayor', 10, 3, 'Apt 5', 28013, false, false, str_to_date('2022-11-16', '%Y-%m-%d')),
    (2, 7, 'Rambla de Catalunya', 45, NULL, 'Apt 10', 08007, false, false, str_to_date('2022-11-16', '%Y-%m-%d')),
    (3, 12, 'Avenida del Puerto', 20, 5, 'Apt 15', 46021, false, false, str_to_date('2022-11-16', '%Y-%m-%d')),
    (4, 17, 'Calle Sierpes', 15, NULL, 'Apt 20', 41004, false, false, str_to_date('2023-01-16', '%Y-%m-%d')),
    (5, 3, 'Calle del Mar', 5, 7, 'Apt 25', 08001, false, false, str_to_date('2023-01-16', '%Y-%m-%d')),
    (6, 18, 'Paseo de las Delicias', 40, NULL, 'Apt 30', 28045, false, false, str_to_date('2023-01-16', '%Y-%m-%d')),
    (7, 9, 'Calle Mayor', 100, 9, 'Apt 35', 28013, false, false, str_to_date('2023-03-16', '%Y-%m-%d'));

INSERT INTO `PRODUCT` (`ID`, `CATEGORY_ID`, `LAUNCH_DATE`, `STOCK`, `PURCHASE_PRICE`, `SALE_PRICE`, `REPLACEMENT_ID`)
VALUES
    -- AMD processors
    (1, 1, str_to_date('2023-05-16', '%Y-%m-%d'), 100, 480.00, 800.00, NULL),
    (2, 1, str_to_date('2022-11-16', '%Y-%m-%d'), 40, 420.00, 700.00, 1),
    (3, 1, str_to_date('2023-05-16', '%Y-%m-%d'), 35, 360.00, 600.00, NULL),
    (4, 1, str_to_date('2022-11-16', '%Y-%m-%d'), 30, 300.00, 500.00, 3),
    (5, 1, str_to_date('2023-05-16', '%Y-%m-%d'), 25, 240.00, 400.00, NULL),
    (6, 1, str_to_date('2022-11-16', '%Y-%m-%d'), 20, 180.00, 300.00, 5),
    -- Intel processors
    (7, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 22, 300.00, 500.00, NULL),
    (8, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 18, 270.00, 450.00, 7),
    (9, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 240.00, 400.00, 8),
    (10, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 480.00, 800.00, NULL),
    (11, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 420.00, 700.00, 10),
    (12, 1, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 360.00, 600.00, 11),
    -- Motherboards for Gigabyte brand
    (13, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 12, 168.00, 280.00, NULL),
    (14, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 10, 150.00, 250.00, 13),
    (15, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 8, 192.00, 320.00, NULL),
    (16, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 18, 180.00, 300.00, NULL),
    (17, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 210.00, 350.00, NULL),
    -- Motherboards for ASUS brand
    (18, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 228.00, 380.00, NULL),
    (19, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 162.00, 270.00, NULL),
    (20, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 22, 240.00, 400.00, NULL),
    (21, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 16, 216.00, 360.00, 20),
    (22, 2, str_to_date('2023-11-16', '%Y-%m-%d'), 10, 252.00, 420.00, NULL),
    -- RAM for Corsair brand
    (23, 3, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 90.00, 150.00, NULL),
    (24, 3, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 150.00, 250.00, NULL),
    (25, 3, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 240.00, 400.00, NULL),
    (26, 3, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 360.00, 600.00, NULL),
    -- SSDs for Samsung brand
    (27, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 150.00, 250.00, NULL),
    (28, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 270.00, 450.00, NULL),
    (29, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 10, 480.00, 800.00, NULL),
    (30, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 72.00, 120.00, 27),
    (31, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 18, 120.00, 200.00, 28),
    (32, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 12, 228.00, 380.00, 29),
    (33, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 60.00, 100.00, NULL),
    (34, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 108.00, 180.00, NULL),
    (35, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 10, 210.00, 350.00, NULL),
    -- SSDs for Western Digital brand
    (36, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 54.00, 90.00, NULL),
    (37, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 22, 96.00, 160.00, NULL),
    (38, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 180.00, 300.00, NULL),
    (39, 9, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 84.00, 140.00, NULL),
    -- HDDs for Western Digital brand
    (40, 10, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 48.00, 80.00, NULL),
    (41, 10, str_to_date('2023-11-16', '%Y-%m-%d'), 18, 66.00, 110.00, NULL),
    (42, 10, str_to_date('2023-11-16', '%Y-%m-%d'), 12, 84.00, 140.00, NULL),
    -- NVIDIA graphics cards
    (43, 11, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 720.00, 1200.00, NULL),
    (44, 11, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 360.00, 600.00, 43),
    (45, 11, str_to_date('2023-11-16', '%Y-%m-%d'), 40, 1080.00, 1800.00, NULL),
    -- AMD graphics cards
    (46, 12, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 420.00, 700.00, NULL),
    (47, 12, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 300.00, 500.00, 46),
    (48, 12, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 540.00, 900.00, NULL),
    -- Cases
    (49, 7, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 100.00, 150.00, NULL),
    (50, 7, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 70.00, 120.00, NULL),
    (51, 7, str_to_date('2023-11-16', '%Y-%m-%d'), 20, 50.00, 100.00, NULL),
    (52, 7, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 90.00, 150.00, NULL),
    -- Power Supplies
    (53, 6, str_to_date('2023-11-16', '%Y-%m-%d'), 30, 90.00, 150.00, NULL),
    (54, 6, str_to_date('2023-11-16', '%Y-%m-%d'), 25, 150.00, 250.00, NULL),
    (55, 6, str_to_date('2023-11-16', '%Y-%m-%d'), 15, 250.00, 400.00, NULL);

INSERT INTO `PRODUCT_LOCALE` (`PRODUCT_ID`, `LOCALE_ID`, `NAME`, `DESCRIPTION`)
VALUES
    -- AMD processors
    (1, 'en-GB', 'AMD Ryzen 9 7950X3D 4.2 GHz/5.7 GHz', 'Description of AMD Ryzen 9 7950X3D 4.2 GHz/5.7 GHz'),
    (2, 'en-GB', 'AMD Ryzen 9 5950X 3.4 GHz', 'Description of AMD Ryzen 9 5950X 3.4 GHz'),
    (3, 'en-GB', 'AMD Ryzen 7 7800X3D 4.2 GHz/5 GHz', 'Description of AMD Ryzen 7 7800X3D 4.2 GHz/5 GHz'),
    (4, 'en-GB', 'AMD Ryzen 7 5800X3D 3.4GHz', 'Description of AMD Ryzen 7 5800X3D 3.4GHz'),
    (5, 'en-GB', 'AMD Ryzen 5 7600X 4.7 GHz', 'Description of AMD Ryzen 5 7600X 4.7 GHz'),
    (6, 'en-GB', 'AMD Ryzen 5 5600X 3.7 GHz', 'Description of AMD Ryzen 5 5600X 3.7GHz'),
    -- Intel processors
    (7, 'en-GB', 'Intel Core i7-14700K 3.4/5.6GHz', 'Description of Intel Core i7-14700K 3.4/5.6GHz'),
    (8, 'en-GB', 'Intel Core i7-13700K 3.4 GHz', 'Description of Intel Core i7-13700K 3.4 GHz'),
    (9, 'en-GB', 'Intel Core i7-12700K 3.6 GHz', 'Description of Intel Core i7-12700K 3.6 GHz'),
    (10, 'en-GB', 'Intel Core i9-14900K 3.2/6GHz', 'Description of Intel Core i9-14900K 3.2/6GHz'),
    (11, 'en-GB', 'Intel Core i9-13900K 3 GHz', 'Description of Intel Core i9-13900K 3 GHz'),
    (12, 'en-GB', 'Intel Core i9-12900K 3.2 GHz', 'Description of Intel Core i9-12900K 3.2 GHz'),
    -- Motherboards for Gigabyte brand
    (13, 'en-GB', 'Gigabyte B650 Aorus Elite AX', 'Description of Gigabyte B650 Aorus Elite AX'),
    (14, 'en-GB', 'Gigabyte B550 Aorus Elite AX', 'Description of Gigabyte B550 Aorus Elite AX'),
    (15, 'en-GB', 'Gigabyte Z790 Aorus Elite AX', 'Description of Gigabyte Z790 Aorus Elite AX'),
    (16, 'en-GB', 'Gigabyte Z690 Gaming X DDR4', 'Description of Gigabyte Z690 Gaming X DDR4'),
    (17, 'en-GB', 'Gigabyte Z590 Aorus Master', 'Description of Gigabyte Z590 Aorus Master'),
    -- Motherboards for ASUS brand
    (18, 'en-GB', 'ASUS ROG STRIX X670E-F GAMING WIFI', 'Description of ASUS ROG STRIX X670E-F GAMING WIFI'),
    (19, 'en-GB', 'ASUS ROG Strix B550-F GAMING', 'Description of ASUS ROG Strix B550-F GAMING'),
    (20, 'en-GB', 'Asus ROG STRIX Z690-E GAMING WIFI', 'Description of Asus ROG STRIX Z690-E GAMING WIFI'),
    (21, 'en-GB', 'Asus ROG STRIX Z590-E GAMING WIFI', 'Description of Asus ROG STRIX Z590-F GAMING WIFI'),
    (22, 'en-GB', 'ASUS ROG Maximus Z790 Hero', 'Description of ASUS ROG Maximus Z790 Hero'),
    -- RAM for Corsair brand
    (23, 'en-GB', 'Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16', 'Description of Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16'),
    (24, 'en-GB', 'Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16', 'Description of Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16'),
    (25, 'en-GB', 'Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Negra', 'Description of Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Negra'),
    (26, 'en-GB', 'Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32', 'Description of Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32'),
    -- SSDs for Samsung brand
    (27, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'Description of Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1TB'),
    (28, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'Description of Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2TB'),
    (29, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'Description of Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 4TB'),
    (30, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'Description of Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500GB'),
    (31, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'Description of Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1TB'),
    (32, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'Description of Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2TB'),
    (33, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 500GB', 'Description of Samsung 970 EVO Plus SSD NVMe M.2 500GB'),
    (34, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 1TB', 'Description of Samsung 970 EVO Plus SSD NVMe M.2 1TB'),
    (35, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 2TB', 'Description of Samsung 970 EVO Plus SSD NVMe M.2 2TB'),
    -- SSDs for Western Digital brand
    (36, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 500GB', 'Description of WD Red SA500 NAS SSD SATA M.2 2280 500GB'),
    (37, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 1TB', 'Description of WD Red SA500 NAS SSD SATA M.2 2280 1TB'),
    (38, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 2TB', 'Description of WD Red SA500 NAS SSD SATA M.2 2280 2TB'),
    (39, 'en-GB', 'Western Digital Blue 2.5" SSD 1TB SATA 3', 'Description of Western Digital Blue 2.5" SSD 1TB SATA 3'),
    -- HDDs for Western Digital brand
    (40, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 2TB', 'Description of Western Digital Red Plus 3.5" SATA3 2TB'),
    (41, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 3TB', 'Description of Western Digital Red Plus 3.5" SATA3 3TB'),
    (42, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 4TB', 'Description of Western Digital Red Plus 3.5" SATA3 4TB'),
    -- NVIDIA graphics cards
    (43, 'en-GB', 'EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X', 'Description of EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X'),
    (44, 'en-GB', 'EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X', 'Description of EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X'),
    (45, 'en-GB', 'ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3', 'Description of ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3'),
    -- AMD graphics cards
    (46, 'en-GB', 'Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6', 'Description of Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6'),
    (47, 'en-GB', 'Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6', 'Description of Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6'),
    (48, 'en-GB', 'ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6', 'Description of ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6'),
    -- Cases
    (49, 'en-GB', 'Fractal Design Node 202 Mini ITX Desktop Case', 'Description of Fractal Design Node 202 Mini ITX Desktop Case'),
    (50, 'en-GB', 'NZXT H510 Compact ATX Mid-Tower Case', 'Description of NZXT H510 Compact ATX Mid-Tower Case'),
    (51, 'en-GB', 'Cooler Master MasterBox Q300L mATX Tower Case', 'Description of Cooler Master MasterBox Q300L mATX Tower Case'),
    (52, 'en-GB', 'Fractal Design Meshify C ATX Mid Tower Case', 'Description of Fractal Design Meshify C ATX Mid Tower Case'),
    -- Power Supplies
    (53, 'en-GB', 'EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Fully Modular', 'Description of EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Fully Modular'),
    (54, 'en-GB', 'Corsair RM850x, 850W, 80 Plus Gold, Fully Modular', 'Description of Corsair RM850x, 850W, 80 Plus Gold, Fully Modular'),
    (55, 'en-GB', 'Corsair HX1200i, 1200W, 80 Plus Platinum, Fully Modular', 'Description of Corsair HX1200i, 1200W, 80 Plus Platinum, Fully Modular');

INSERT INTO `CUSTOMER_ORDER` (`ORDER_STATE_ID`, `CUSTOMER_ID`, `ORDER_DATE`, `TRACKING_NUMBER`, `INVOICE_TOTAL`, `BILLING_ADDRESS_ID`, `SHIPPING_ADDRESS_ID`)
VALUES
	('PND', 1, str_to_date('2023-11-01', '%Y-%m-%d'), 'TRK123', 3650.00, 1, 1),
	('PRS', 2, str_to_date('2023-11-02', '%Y-%m-%d'), 'TRK456', 350.00, 2, 2),
	('SPD', 3, str_to_date('2023-11-03', '%Y-%m-%d'), 'TRK789', 150.00, 3, 3),
	('SPD', 4, str_to_date('2023-11-03', '%Y-%m-%d'), 'TRK012', 150.00, 4, 4),
	('DEL', 5, str_to_date('2023-08-02', '%Y-%m-%d'), 'TRK987', 93000.00, 5, 5),
	('DEL', 6, str_to_date('2023-07-24', '%Y-%m-%d'), 'TRK654', 66000.00, 6, 6),
	('DEL', 7, str_to_date('2023-07-25', '%Y-%m-%d'), 'TRK312', 27500.00, 7, 7),
	('DEL', 1, str_to_date('2023-08-19', '%Y-%m-%d'), 'TRK667', 93000.00, 5, 5),
	('PND', 1, str_to_date('2023-12-01', '%Y-%m-%d'), 'TRK111', 1000.00, 1, 1),
	('PND', 2, str_to_date('2023-12-02', '%Y-%m-%d'), 'TRK222', 500.00, 2, 2),
	('PND', 3, str_to_date('2023-12-03', '%Y-%m-%d'), 'TRK333', 750.00, 3, 3),
	('PND', 4, str_to_date('2023-12-04', '%Y-%m-%d'), 'TRK444', 1200.00, 4, 4),
	('PND', 5, str_to_date('2023-12-05', '%Y-%m-%d'), 'TRK555', 900.00, 5, 5),
	('PND', 6, str_to_date('2023-12-06', '%Y-%m-%d'), 'TRK666', 800.00, 6, 6),
	('PND', 7, str_to_date('2023-12-07', '%Y-%m-%d'), 'TRK777', 1500.00, 7, 7),
	('PND', 1, str_to_date('2023-12-08', '%Y-%m-%d'), 'TRK888', 1100.00, 1, 1),
	('PND', 2, str_to_date('2023-12-09', '%Y-%m-%d'), 'TRK999', 950.00, 2, 2),
	('PND', 3, str_to_date('2023-12-10', '%Y-%m-%d'), 'TRK000', 600.00, 3, 3),
	('PND', 4, str_to_date('2023-12-11', '%Y-%m-%d'), 'TRK123', 1300.00, 4, 4),
	('PND', 5, str_to_date('2023-12-12', '%Y-%m-%d'), 'TRK456', 1800.00, 5, 5),
	('PND', 6, str_to_date('2023-12-13', '%Y-%m-%d'), 'TRK789', 950.00, 6, 6),
	('PND', 7, str_to_date('2023-12-14', '%Y-%m-%d'), 'TRK012', 700.00, 7, 7),
	('PND', 1, str_to_date('2023-12-15', '%Y-%m-%d'), 'TRK321', 1200.00, 1, 1);

    
INSERT INTO `ORDER_LINE` (`CUSTOMER_ORDER_ID`, `PRODUCT_ID`, `QUANTITY`, `PURCHASE_PRICE`, `SALE_PRICE`)
VALUES
    -- Order 1
    (1, 1, 2, 450.00, 750.00),
    (1, 13, 1, 125.00, 250.00),
    (1, 43, 1, 720.00, 1200.00),
    (1, 32, 2, 210.00, 350.00),

    -- Order 2
    (2, 4, 2, 240.00, 400.00),
    (2, 14, 1, 125.00, 250.00),
    (2, 47, 1, 270.00, 450.00),
    (2, 32, 2, 228.00, 380.00),

    -- Order 3
    (3, 7, 1, 300.00, 500.00),
    (3, 15, 2, 192.00, 320.00),

    -- Order 4
    (4, 48, 1, 540.00, 900.00),
    (4, 26, 2, 270.00, 450.00),

    -- Order 5
    (5, 29, 100, 450.00, 750.00),
    (5, 42, 150, 72.00, 120.00),

    -- Order 6
    (6, 43, 40, 990.00, 1650.00),

    -- Order 7
    (7, 44, 50, 330.00, 550.00),

    -- Order 8
    (8, 48, 100, 540.00, 900.00),

    -- Order 9
    (9, 1, 2, 300.00, 500.00),
    (9, 13, 1, 125.00, 250.00),
    (9, 43, 1, 720.00, 1200.00),

    -- Order 10
    (10, 4, 2, 240.00, 400.00),
    (10, 14, 1, 125.00, 250.00),

    -- Order 11
    (11, 7, 1, 300.00, 500.00),
    (11, 15, 2, 192.00, 320.00),

    -- Order 12
    (12, 48, 1, 540.00, 900.00),
    (12, 26, 2, 270.00, 450.00),

    -- Order 13
    (13, 29, 1, 450.00, 750.00),

    -- Order 14
    (14, 43, 2, 495.00, 825.00),

    -- Order 15
    (15, 44, 3, 330.00, 550.00),
    (15, 48, 2, 540.00, 900.00),

    -- Order 16
    (16, 12, 1, 180.00, 300.00),
    (16, 18, 1, 90.00, 150.00),

    -- Order 17
    (17, 23, 2, 80.00, 200.00),
    (17, 31, 1, 108.00, 180.00),

    -- Order 18
    (18, 8, 1, 240.00, 400.00),
    (18, 42, 1, 420.00, 700.00),

    -- Order 19
    (19, 3, 1, 150.00, 250.00),
    (19, 16, 2, 180.00, 300.00),

    -- Order 20
    (20, 21, 1, 108.00, 180.00),

    -- Order 21
    (21, 6, 2, 210.00, 350.00),
    (21, 27, 1, 72.00, 120.00),

    -- Order 22
    (22, 33, 1, 360.00, 600.00),
    (22, 46, 2, 120.00, 200.00),

    -- Order 23
    (23, 13, 1, 125.00, 250.00),
    (23, 36, 2, 240.00, 400.00),
    (23, 47, 1, 480.00, 800.00);


INSERT INTO `TICKET` (`CUSTOMER_ID`, `EMPLOYEE_ID`, `CREATION_DATE`, `TICKET_STATE_ID`, `TICKET_TYPE_ID`, `PRODUCT_ID`, `TITLE`, `DESCRIPTION`)
VALUES
    (5, NULL, str_to_date('2022-08-03 12:00:00', '%Y-%m-%d %H:%i:%s'), 'CLO', 'RMA', NULL, 'Product Issue', 'Product XYZ123 not working properly, requesting return for warranty repair.'),
    (6, NULL, str_to_date('2023-08-05 12:00:00', '%Y-%m-%d %H:%i:%s'), 'CLO', 'RMA', NULL, 'Incorrect Item Received', 'Received wrong item in order, need replacement.'),
    (7, NULL, str_to_date('2023-08-10 12:00:00', '%Y-%m-%d %H:%i:%s'), 'CLO', 'RMA', NULL, 'Product Malfunction', 'Experiencing issues with received product.'),
    (1, NULL, str_to_date('2023-08-23 12:00:00', '%Y-%m-%d %H:%i:%s'), 'CLO', 'RMA', NULL, 'Product Description Mismatch', 'Product received does not match the description.'),
    (4, NULL, str_to_date('2023-11-06 12:00:00', '%Y-%m-%d %H:%i:%s'), 'OPN', 'PRO', 10, 'Product Inquiry', 'Interested in product number 10, need specifications.'),
    (7, NULL, str_to_date('2024-01-08 12:00:00', '%Y-%m-%d %H:%i:%s'), 'CLO', 'PRO', 25, 'Product Inquiry', 'Question about product 25.');
    
INSERT INTO `TICKET_ORDER_LINE` (`TICKET_ID`, `ORDER_LINE_ID`, `QUANTITY`)
VALUES
    (1, 13, 1),
    (1, 14, 10),
    (2, 15, 1),
    (3, 16, 5),
    (4, 17, 2);
    
INSERT INTO `TICKET_MESSAGE` (`TICKET_ID`, `CUSTOMER_ID`, `EMPLOYEE_ID`, `DATE`, `TEXT`)
VALUES
	-- Ticket 1
    (1, 5, NULL, str_to_date('2022-08-03 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hello, I purchased this product and it is not working properly. I would like to request a return for warranty repair.'),
    (1, NULL, 3, str_to_date('2022-08-04 14:00:00', '%Y-%m-%d %H:%i:%s'), 'Hello, I understand your concern. Could you please provide the serial number of the product?'),
    (1, 5, NULL, str_to_date('2022-08-05 16:00:00', '%Y-%m-%d %H:%i:%s'), 'Sure, the serial number is XYZ123.'),
    (1, NULL, 4, str_to_date('2022-08-06 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for providing the serial number. We will initiate the RMA process for your product.'),
    (1, 5, NULL, str_to_date('2022-08-06 20:00:00', '%Y-%m-%d %H:%i:%s'), 'Great, thank you for your assistance.'),
    (1, NULL, 3, str_to_date('2022-08-06 22:00:00', '%Y-%m-%d %H:%i:%s'), 'You are welcome. Please let us know if you need any further assistance.'),

	-- Ticket 2
    (2, 6, NULL, str_to_date('2023-08-05 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hi, I received the wrong item in my order. I need to return it for a replacement.'),
    (2, NULL, 4, str_to_date('2023-08-05 14:00:00', '%Y-%m-%d %H:%i:%s'), 'I apologize for the inconvenience. Could you please provide the order number?'),
    (2, 6, NULL, str_to_date('2023-08-05 16:00:00', '%Y-%m-%d %H:%i:%s'), 'Certainly, the order number is ORD456.'),
    (2, NULL, 3, str_to_date('2023-08-06 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for providing the order number. We will arrange the replacement for you.'),
    (2, 6, NULL, str_to_date('2023-08-06 20:00:00', '%Y-%m-%d %H:%i:%s'), 'Appreciate your help.'),
    (2, NULL, 4, str_to_date('2023-08-06 22:00:00', '%Y-%m-%d %H:%i:%s'), 'You are welcome. We aim to provide the best service.'),
	
    -- Ticket 3
    (3, 7, NULL, str_to_date('2023-08-10 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hello, I am experiencing issues with the product I received.'),
    (3, NULL, 3, str_to_date('2023-08-11 14:00:00', '%Y-%m-%d %H:%i:%s'), 'I am sorry to hear that. Could you please describe the problem in detail?'),
    (3, 7, NULL, str_to_date('2023-08-12 16:00:00', '%Y-%m-%d %H:%i:%s'), 'The product seems to be malfunctioning.'),
    (3, NULL, 4, str_to_date('2023-08-13 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for reporting the issue. We will arrange a return for inspection.'),
    (3, 7, NULL, str_to_date('2023-08-14 20:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for your prompt response.'),
    (3, NULL, 3, str_to_date('2023-08-15 22:00:00', '%Y-%m-%d %H:%i:%s'), 'We appreciate your patience.'),

	-- Ticket 4
    (4, 1, NULL, str_to_date('2023-08-23 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hi, I need to return the product I received as it does not meet my expectations.'),
    (4, NULL, 4, str_to_date('2023-08-23 14:00:00', '%Y-%m-%d %H:%i:%s'), 'I apologize for any inconvenience caused. Could you please provide the reason for the return?'),
    (4, 1, NULL, str_to_date('2023-08-24 16:00:00', '%Y-%m-%d %H:%i:%s'), 'The product does not match the description.'),
    (4, NULL, 3, str_to_date('2023-08-26 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for providing the information. We will process the return accordingly.'),
    (4, 1, NULL, str_to_date('2023-08-27 20:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for your assistance.'),
    (4, NULL, 4, str_to_date('2023-08-27 22:00:00', '%Y-%m-%d %H:%i:%s'), 'You are welcome. We are here to help.'),
    
    -- Ticket 5
	(5, 4, NULL, str_to_date('2023-11-06 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hello, I am interested in product number 10. Can you provide more information about its specifications?'),
    (5, NULL, 3, str_to_date('2023-11-06 14:00:00', '%Y-%m-%d %H:%i:%s'), 'Of course! Product 10 has the following specifications...'),
    (5, 4, NULL, str_to_date('2023-11-06 16:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for the information. How about product 25?'),
    (5, NULL, 4, str_to_date('2023-11-06 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Product 25 is a newer model with enhanced features.'),
    
	-- Ticket 6
    (6, 7, NULL, str_to_date('2024-01-08 12:00:00', '%Y-%m-%d %H:%i:%s'), 'Hello, I have a question about product 35. Could you provide some details?'),
    (6, NULL, 3, str_to_date('2024-01-08 14:00:00', '%Y-%m-%d %H:%i:%s'), 'Certainly! Product 35 is known for its durability and high performance.'),
    (6, 7, NULL, str_to_date('2024-01-08 16:00:00', '%Y-%m-%d %H:%i:%s'), 'Thank you for the information. What about product 48?'),
    (6, NULL, 4, str_to_date('2024-01-08 18:00:00', '%Y-%m-%d %H:%i:%s'), 'Product 48 is our flagship model with cutting-edge technology.');

INSERT INTO `RMA` (`CUSTOMER_ID`, `RMA_STATE_ID`, `CREATION_DATE`, `TRACKING_NUMBER`)
VALUES
	(5, 'APP', str_to_date('2022-08-06 18:30:00', '%Y-%m-%d %H:%i:%s'), 'RMA12345'),
	(6, 'APP', str_to_date('2023-08-06 18:30:00', '%Y-%m-%d %H:%i:%s'), 'RMA54321'),
	(7, 'APP', str_to_date('2023-08-13 18:30:00', '%Y-%m-%d %H:%i:%s'), 'RMA54321'),
	(1, 'APP', str_to_date('2023-08-26 18:30:00', '%Y-%m-%d %H:%i:%s'), 'RMA99999');

INSERT INTO `RMA_ORDER_LINE` (`RMA_ID`, `ORDER_LINE_ID`, `QUANTITY`)
VALUES
    (1, 13, 1),
    (1, 14, 10),
	(2, 15, 1),
    (3, 16, 5),
	(4, 17, 2);

INSERT INTO `ATTRIBUTE_TYPE` (`ATTRIBUTE_DATA_TYPE_ID`)
VALUES
    -- Brand
    ('VAR'),

    -- Processor attributes
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('VAR'),
    ('INT'),
    ('BOO'),
    ('VAR'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('VAR'),
    
    -- Motherboard attributes
    ('VAR'),
    ('VAR'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    
    -- RAM attributes
    ('INT'),
    ('INT'),
    ('INT'),
    ('DEC'),
    ('BOO'),
    ('INT'),
    
    -- Storage device attributes
    ('VAR'),
    ('INT'),
    ('VAR'),
    ('INT'),
    ('INT'),
    
    -- SSD specific attributes
    ('VAR'),
    ('INT'),
    
    -- HDD specific attributes
    ('INT'),
    
    -- GPU specific attributes
    ('INT'),
    ('INT'),
    ('INT'),
    
    -- Power Supply specific attributes
    ('INT'),
    ('VAR'),
    ('BOO'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),

    -- Case specific attributes
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT'),
    ('INT');

INSERT INTO `ATTRIBUTE_TYPE_LOCALE` (`ATTRIBUTE_TYPE_ID`, `LOCALE_ID`, `NAME`)
VALUES
    -- Brand
    (1, 'en-GB', 'Brand'),

    -- Processor attributes
    (2, 'en-GB', 'Base Frequency (MHz)'),
    (3, 'en-GB', 'Boost Frequency (MHz)'),
    (4, 'en-GB', 'Number of Cores'),
    (5, 'en-GB', 'Number of Threads'),
    (6, 'en-GB', 'L1 Cache Memory (KB)'),
    (7, 'en-GB', 'L2 Cache Memory (KB)'),
    (8, 'en-GB', 'L3 Cache Memory (MB)'),
    (9, 'en-GB', 'TDP (Thermal Design Power, W)'),
    (10, 'en-GB', 'Socket'),
    (11, 'en-GB', 'Manufacturing Process (nm)'),
    (12, 'en-GB', 'Integrated Graphics'),
    (13, 'en-GB', 'Integrated Graphics Processor'),
    (14, 'en-GB', 'Integrated Graphics Base Frequency (MHz)'),
    (15, 'en-GB', 'Integrated Graphics Max Frequency (MHz)'),
    (16, 'en-GB', 'Integrated Graphics Execution Units'),
    (17, 'en-GB', 'Integrated Graphics Cores'),
    (18, 'en-GB', 'Memory Type'),
    
    -- Motherboard attributes
    (19, 'en-GB', 'Form Factor'),
    (20, 'en-GB', 'Chipset'),
    (21, 'en-GB', 'Memory Slots'),
    (22, 'en-GB', 'Max Memory (GB)'),
    (23, 'en-GB', 'PCIe x16 Slots'),
    (24, 'en-GB', 'PCIe x1 Slots'),
    (25, 'en-GB', 'SATA III Ports'),
    (26, 'en-GB', 'M.2 Slots'),
    
    -- RAM attributes
    (27, 'en-GB', 'RAM Capacity (GB)'),
    (28, 'en-GB', 'Frequency (MHz)'),
    (29, 'en-GB', 'CAS Latency'),
    (30, 'en-GB', 'Voltage (V)'),
    (31, 'en-GB', 'Heat Spreader'),
    (32, 'en-GB', 'Number of RAM Modules'),
    
    -- Storage device attributes
    (33, 'en-GB', 'Storage Form Factor'),
    (34, 'en-GB', 'Storage Capacity (GB)'),
    (35, 'en-GB', 'Interface'),
    (36, 'en-GB', 'Read Speed (MB/s)'),
    (37, 'en-GB', 'Write Speed (MB/s)'),
    
    -- SSD specific attributes
    (38, 'en-GB', 'NAND Type'),
    (39, 'en-GB', 'Endurance (TBW)'),
    
    -- HDD specific attributes
    (40, 'en-GB', 'Rotational Speed (RPM)'),
    
    -- GPU specific attributes
    (41, 'en-GB', 'CUDA Cores'),
    (42, 'en-GB', 'Memory Size (GB)'),
    (43, 'en-GB', 'Memory Bandwidth (GB/s)'),
    
    -- Power Supply specific attributes
    (44, 'en-GB', 'Max Power Consumption (W)'),
    (45, 'en-GB', 'Efficiency Rating'),
    (46, 'en-GB', 'Modularity'),
    (47, 'en-GB', 'PCIe Connectors'),
    (48, 'en-GB', 'SATA Connectors'),
    (49, 'en-GB', 'Molex Connectors'),
    (50, 'en-GB', 'EPS Connectors'),

    -- Case specific attributes
    (51, 'en-GB', 'Maximum GPU Length (mm)'),
    (52, 'en-GB', 'Maximum CPU Cooler Height (mm)'),
    (53, 'en-GB', 'Maximum PSU Length (mm)'),
    (54, 'en-GB', 'External 5.25" Drive Bays'),
    (55, 'en-GB', 'Internal 3.5" Drive Bays'),
    (56, 'en-GB', 'Internal 2.5" Drive Bays');
    
INSERT INTO `CATEGORY_ATTRIBUTE_TYPE` (`CATEGORY_ID`, `ATTRIBUTE_TYPE_ID`)
VALUES
	-- Inserting the "Brand" attribute type to every category
	(1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    
	-- Attributes associated with the Processor category
	(1, 2),   -- Base Frequency (MHz)
	(1, 3),   -- Boost Frequency (MHz)
	(1, 4),   -- Number of Cores
	(1, 5),   -- Number of Threads
	(1, 6),   -- L1 Cache Memory (KB)
	(1, 7),   -- L2 Cache Memory (MB)
	(1, 8),   -- L3 Cache Memory (MB)
	(1, 9),   -- TDP (Thermal Design Power, W)
	(1, 10),  -- Socket
	(1, 11),  -- Manufacturing Process (nm)
	(1, 12),  -- Integrated Graphics
	(1, 13),  -- Integrated Graphics Processor
	(1, 14),  -- Integrated Graphics Base Frequency (MHz)
	(1, 15),  -- Integrated Graphics Max Frequency (MHz)
	(1, 16),  -- Integrated Graphics Execution Units
	(1, 17),  -- Integrated Graphics Cores
    (1, 18),  -- Memory Type
    
    -- Attributes associated with the Motherboard category
	(2, 10), -- Socket
    (2, 18), -- Memory Type
	(2, 19), -- Form Factor
    (2, 20), -- Chipset
    (2, 21), -- Memory Slots
    (2, 22), -- Max Memory (GB)
    (2, 23), -- PCIe x16 Slots
    (2, 24), -- PCIe x1 Slots
    (2, 25), -- SATA III Ports
    (2, 26), -- M.2 Slots
    
	-- Attributes associated with the RAM category
	(3, 18), -- Memory Type
	(3, 27), -- RAM Capacity (GB)
	(3, 28), -- Frequency (MHz)
	(3, 29), -- CAS Latency
	(3, 30), -- Voltage (V)
	(3, 31), -- Heat Spreader
	(3, 32), -- Number of RAM Modules

	-- Attributes associated with storage devices
	(4, 33), -- Storage Form Factor
	(4, 34), -- Storage Capacity (GB)
	(4, 35), -- Interface
    (4, 36), -- Read Speed (MB/s)
	(4, 37), -- Write Speed (MB/s)

	-- Attributes associated with SSDs
	(9, 38), -- NAND Type
	(9, 39), -- Endurance (TBW)

	-- Attributes associated with HDDs
	(10, 40), -- Rotational Speed (RPM)

	-- Attributes associated with Graphics Cards
	(5, 2),  -- Base Frequency (MHz)
	(5, 3),  -- Boost Frequency (MHz)
	(5, 9),  -- TDP (Thermal Design Power, W)
	(5, 11), -- Manufacturing Process (nm)
	(5, 18), -- Memory Type
	(5, 35), -- Interface
	(5, 41), -- CUDA Cores
	(5, 42), -- Memory Size (GB)
	(5, 43), -- Memory Bandwidth (GB/s)

	-- Attributes associated with Power supplies
	(6, 44), -- Max Power Consumption (W)
	(6, 45), -- Efficiency Rating
	(6, 46), -- Modularity
	(6, 47), -- PCIe Connectors
	(6, 48), -- SATA Connectors
	(6, 49), -- Molex Connectors
	(6, 50), -- EPS Connectors

	-- Attributes associated with Cases
	(7, 51), -- Maximum GPU Length
	(7, 52), -- Maximum CPU Cooler Height
	(7, 53), -- Maximum PSU Length
	(7, 54), -- External 5.25" Drive Bays
	(7, 55), -- Internal 3.5" Drive Bays
	(7, 56); -- Internal 2.5" Drive Bays
    
INSERT INTO `ATTRIBUTE_VALUE` (`ATTRIBUTE_TYPE_ID`, `VALUE_VARCHAR`, `VALUE_BIGINT`, `VALUE_DECIMAL`, `VALUE_BOOLEAN`)
VALUES

	-- Brand
    (1, 'Intel', NULL, NULL, NULL),
    (1, 'AMD', NULL, NULL, NULL),
    (1, 'ASUS', NULL, NULL, NULL),
    (1, 'GIGABYTE', NULL, NULL, NULL),
    (1, 'Corsair', NULL, NULL, NULL),
    (1, 'Samsung', NULL, NULL, NULL),
    (1, 'Western Digital', NULL, NULL, NULL),
    (1, 'NVIDIA', NULL, NULL, NULL),
    (1, 'EVGA', NULL, NULL, NULL),
    (1, 'Cooler Master', NULL, NULL, NULL),
    
	-- Base Frequency (MHz)
	(2, NULL, 4200, NULL, NULL),
	(2, NULL, 3400, NULL, NULL),
	(2, NULL, 4700, NULL, NULL),
	(2, NULL, 3700, NULL, NULL),
	(2, NULL, 3600, NULL, NULL),
	(2, NULL, 3200, NULL, NULL),
	(2, NULL, 3000, NULL, NULL),
	(2, NULL, 4400, NULL, NULL),
	(2, NULL, 2800, NULL, NULL),
	(2, NULL, 2600, NULL, NULL),
	(2, NULL, 4300, NULL, NULL),

	-- Boost Frequency (MHz)
	(3, NULL, 5700, NULL, NULL),
	(3, NULL, 4900, NULL, NULL),
	(3, NULL, 5000, NULL, NULL),
	(3, NULL, 4500, NULL, NULL),
	(3, NULL, 5300, NULL, NULL),
	(3, NULL, 4600, NULL, NULL),
	(3, NULL, 5600, NULL, NULL),
	(3, NULL, 5400, NULL, NULL),
	(3, NULL, 6000, NULL, NULL),
	(3, NULL, 5800, NULL, NULL),
	(3, NULL, 5200, NULL, NULL),
	(3, NULL, 6500, NULL, NULL),
	(3, NULL, 6200, NULL, NULL),
	(3, NULL, 4000, NULL, NULL),
	(3, NULL, 4100, NULL, NULL),
	(3, NULL, 3800, NULL, NULL),

	-- Number of Cores
	(4, NULL, 2, NULL, NULL),
	(4, NULL, 4, NULL, NULL),
	(4, NULL, 6, NULL, NULL),
	(4, NULL, 8, NULL, NULL),
	(4, NULL, 12, NULL, NULL),
	(4, NULL, 16, NULL, NULL),
	(4, NULL, 20, NULL, NULL),
	(4, NULL, 24, NULL, NULL),


	-- Number of Threads
	(5, NULL, 2, NULL, NULL),
	(5, NULL, 4, NULL, NULL),
	(5, NULL, 8, NULL, NULL),
	(5, NULL, 12, NULL, NULL),
	(5, NULL, 16, NULL, NULL),
	(5, NULL, 20, NULL, NULL),
	(5, NULL, 24, NULL, NULL),
	(5, NULL, 28, NULL, NULL),
	(5, NULL, 32, NULL, NULL),


	-- L2 Cache Memory (MB)
	(7, NULL, 32, NULL, NULL),
	(7, NULL, 28, NULL, NULL),
	(7, NULL, 24, NULL, NULL),
	(7, NULL, 16, NULL, NULL),
	(7, NULL, 14, NULL, NULL),
	(7, NULL, 12, NULL, NULL),
	(7, NULL, 8, NULL, NULL),
	(7, NULL, 6, NULL, NULL),
	(7, NULL, 4, NULL, NULL),
	(7, NULL, 3, NULL, NULL),

	-- L3 Cache Memory (MB)
	(8, NULL, 128, NULL, NULL),
	(8, NULL, 96, NULL, NULL),
	(8, NULL, 32, NULL, NULL),
	(8, NULL, 36, NULL, NULL),
	(8, NULL, 33, NULL, NULL),
	(8, NULL, 30, NULL, NULL),
	(8, NULL, 25, NULL, NULL),

	-- TDP (Thermal Design Power, W)
	(9, NULL, 65, NULL, NULL),
	(9, NULL, 105, NULL, NULL),
	(9, NULL, 120, NULL, NULL),
	(9, NULL, 190, NULL, NULL),
	(9, NULL, 241, NULL, NULL),
	(9, NULL, 253, NULL, NULL),

	-- Socket
	(10, 'AM4', NULL, NULL, NULL),
	(10, 'AM5', NULL, NULL, NULL),
	(10, 'LGA 1200', NULL, NULL, NULL),
	(10, 'LGA 1700', NULL, NULL, NULL),

	-- Manufacturing Process (nm)
	(11, NULL, 10, NULL, NULL),
	(11, NULL, 7, NULL, NULL),
	(11, NULL, 5, NULL, NULL),

	-- Integrated Graphics
	(12, NULL, NULL, NULL, false),
	(12, NULL, NULL, NULL, true),

	-- Integrated Graphics Processor
	(13, 'Intel UHD Graphics 770', NULL, NULL, NULL),
	(13, 'AMD Radeon Graphics', NULL, NULL, NULL),

	-- Integrated Graphics Base Frequency (MHz)
	(14, NULL, 300, NULL, NULL),

	-- Integrated Graphics Max Frequency (MHz)
	(15, NULL, 1500, NULL, NULL),
	(15, NULL, 1550, NULL, NULL),
	(15, NULL, 1600, NULL, NULL),
	(15, NULL, 1650, NULL, NULL),
	(15, NULL, 2200, NULL, NULL),

	-- Integrated Graphics Execution Units
	(16, NULL, 32, NULL, NULL),
	  
	-- Integrated Graphics Cores
	(17, NULL, 2, NULL, NULL),
    
    -- Memory Type
    (18, 'DDR4', NULL, NULL, NULL),
    (18, 'DDR5', NULL, NULL, NULL),
    (18, 'GDDR6', NULL, NULL, NULL),
    (18, 'GDDR6X', NULL, NULL, NULL),
    
    -- Form Factor
    (19, 'ATX', NULL, NULL, NULL),
    (19, 'Micro ATX', NULL, NULL, NULL),
    (19, 'Mini ITX', NULL, NULL, NULL),

    -- Chipset
	(20, 'Intel Z590', NULL, NULL, NULL),
	(20, 'Intel Z690', NULL, NULL, NULL),
	(20, 'Intel Z790', NULL, NULL, NULL),
	(20, 'AMD B550', NULL, NULL, NULL),
	(20, 'AMD X570', NULL, NULL, NULL),
	(20, 'AMD B650', NULL, NULL, NULL),
	(20, 'AMD X670', NULL, NULL, NULL),
	(20, 'AMD X670E', NULL, NULL, NULL),

    -- Memory Slots
    (21, NULL, 4, NULL, NULL),
    (21, NULL, 2, NULL, NULL),
    (21, NULL, 8, NULL, NULL),

    -- Max Memory (GB)
    (22, NULL, 128, NULL, NULL),
    (22, NULL, 64, NULL, NULL),
    (22, NULL, 32, NULL, NULL),

    -- PCIe x16 Slots
    (23, NULL, 1, NULL, NULL),
    (23, NULL, 2, NULL, NULL),
    (23, NULL, 3, NULL, NULL),

    -- PCIe x1 Slots
    (24, NULL, 1, NULL, NULL),
    (24, NULL, 2, NULL, NULL),
    (24, NULL, 3, NULL, NULL),
	(24, NULL, 4, NULL, NULL),

    -- SATA III Ports
    (25, NULL, 4, NULL, NULL),
    (25, NULL, 6, NULL, NULL),
    (25, NULL, 8, NULL, NULL),

    -- M.2 Slots
	(26, NULL, 1, NULL, NULL),
    (26, NULL, 2, NULL, NULL),
    (26, NULL, 3, NULL, NULL),

    -- Capacity (GB)
	(27, NULL, 8, NULL, NULL),
	(27, NULL, 16, NULL, NULL),
	(27, NULL, 32, NULL, NULL),
	(27, NULL, 64, NULL, NULL),

    -- Frequency (MHz) 
    (28, NULL, 2400, NULL, NULL),
	(28, NULL, 3200, NULL, NULL),
	(28, NULL, 3600, NULL, NULL),
	(28, NULL, 4000, NULL, NULL),
	(28, NULL, 5600, NULL, NULL),
	(28, NULL, 6000, NULL, NULL),
	(28, NULL, 6400, NULL, NULL),

    -- CAS Latency 
    (29, NULL, 16, NULL, NULL),
	(29, NULL, 18, NULL, NULL),
	(29, NULL, 22, NULL, NULL),

    -- Voltage (V)
    (30, NULL, NULL, 1.1, NULL),
    (30, NULL, NULL, 1.2, NULL),
    (30, NULL, NULL, 1.35, NULL),
    (30, NULL, NULL, 1.5, NULL),

    -- Heat Spreader
    (31, NULL, NULL, NULL, true),
	(31, NULL, NULL, NULL, false),

    -- Number of RAM Modules
    (32, NULL, 1, NULL, NULL),
    (32, NULL, 2, NULL, NULL),
    (32, NULL, 4, NULL, NULL),
    
    -- Storage Form Factor
	(33, '2.5"', NULL, NULL, NULL),
	(33, '3.5"', NULL, NULL, NULL),
	(33, 'M.2', NULL, NULL, NULL),
	(33, 'PCIe Card', NULL, NULL, NULL),
    
    -- Storage Capacity (GB)
	(34, NULL, 120, NULL, NULL),
	(34, NULL, 250, NULL, NULL),
	(34, NULL, 500, NULL, NULL),
	(34, NULL, 1000, NULL, NULL),
	(34, NULL, 2000, NULL, NULL),
	(34, NULL, 3000, NULL, NULL),
	(34, NULL, 4000, NULL, NULL),
	(34, NULL, 5000, NULL, NULL),
	(34, NULL, 6000, NULL, NULL),

    -- Interface
    (35, 'SATA III', NULL, NULL, NULL),
    (35, 'NVMe', NULL, NULL, NULL),
	(35, 'PCIe 4.0 x16', NULL, NULL, NULL),
    (35, 'PCIe 3.0 x16', NULL, NULL, NULL),
	(35, 'PCIe 4.0 x1', NULL, NULL, NULL),
    (35, 'PCIe 3.0 x1', NULL, NULL, NULL),
    (35, 'USB', NULL, NULL, NULL),

	-- NAND Type
	(38, 'QLC', NULL, NULL, NULL),
	(38, 'TLC', NULL, NULL, NULL),
	(38, 'MLC', NULL, NULL, NULL),
	(38, 'SLC', NULL, NULL, NULL),
    
    -- Endurance (TBW)
	(39, NULL, 120, NULL, NULL),
	(39, NULL, 250, NULL, NULL),
	(39, NULL, 300, NULL, NULL),
	(39, NULL, 500, NULL, NULL),
	(39, NULL, 600, NULL, NULL),
	(39, NULL, 750, NULL, NULL),
	(39, NULL, 1000, NULL, NULL),
	(39, NULL, 1200, NULL, NULL),
	(39, NULL, 1500, NULL, NULL),
	(39, NULL, 2000, NULL, NULL),
	(39, NULL, 2500, NULL, NULL),
	(39, NULL, 3000, NULL, NULL),

	-- Rotational Speed (RPM)
	(40, NULL, 5400, NULL, NULL),
	(40, NULL, 7200, NULL, NULL),
	(40, NULL, 10000, NULL, NULL),
	(40, NULL, 15000, NULL, NULL),
    
	-- CUDA Cores
	(41, NULL, 16384, NULL, NULL),
	(41, NULL, 10752, NULL, NULL),
	(41, NULL, 8704, NULL, NULL),
	(41, NULL, 6144, NULL, NULL),
	(41, NULL, 5888, NULL, NULL),
	(41, NULL, 4864, NULL, NULL),
	(41, NULL, 4352, NULL, NULL),
	(41, NULL, 3840, NULL, NULL),
	(41, NULL, 3584, NULL, NULL),
	(41, NULL, 3072, NULL, NULL),
	(41, NULL, 2944, NULL, NULL),
	(41, NULL, 2304, NULL, NULL),
	(41, NULL, 1536, NULL, NULL),

	-- Memory Size (GB)
	(42, NULL, 24, NULL, NULL),
	(42, NULL, 16, NULL, NULL),
	(42, NULL, 12, NULL, NULL),
	(42, NULL, 10, NULL, NULL),
	(42, NULL, 8, NULL, NULL),
	(42, NULL, 6, NULL, NULL),
	(42, NULL, 4, NULL, NULL),

	-- Memory Bandwidth (GB/s)
	(43, NULL, 936, NULL, NULL),
	(43, NULL, 760, NULL, NULL),
	(43, NULL, 512, NULL, NULL),
	(43, NULL, 448, NULL, NULL),
	(43, NULL, 384, NULL, NULL),
	(43, NULL, 336, NULL, NULL),
	(43, NULL, 256, NULL, NULL),
	(43, NULL, 192, NULL, NULL),

	-- TDP (Thermal Design Power, W)
	(9, NULL, 450, NULL, NULL),
	(9, NULL, 350, NULL, NULL),
	(9, NULL, 220, NULL, NULL),
	(9, NULL, 130, NULL, NULL),

	-- Efficiency Rating
	(45, '80 Plus Bronze', NULL, NULL, NULL),
	(45, '80 Plus Gold', NULL, NULL, NULL),
	(45, '80 Plus Platinum', NULL, NULL, NULL),
	(45, '80 Plus Titanium', NULL, NULL, NULL),

	-- Modularity
	(46, NULL, NULL, NULL, false),
	(46, NULL, NULL, NULL, true),

	-- PCIe Connectors
	(47, NULL, 6, NULL, NULL),
	(47, NULL, 8, NULL, NULL),
	(47, NULL, 12, NULL, NULL),

	-- Maximum GPU Length
	(51, NULL, 330, NULL, NULL),
	(51, NULL, 290, NULL, NULL),
	(51, NULL, 380, NULL, NULL),

	-- Maximum CPU Cooler Height
	(52, NULL, 160, NULL, NULL),
	(52, NULL, 180, NULL, NULL),
	(52, NULL, 200, NULL, NULL),

	-- Maximum PSU Length
	(53, NULL, 180, NULL, NULL),
	(53, NULL, 220, NULL, NULL),
	(53, NULL, 260, NULL, NULL),

	-- Internal 5.25" Drive Bays
	(54, NULL, 0, NULL, NULL),
	(54, NULL, 2, NULL, NULL),
	(54, NULL, 3, NULL, NULL),

	-- Internal 3.5" Drive Bays
	(55, NULL, 2, NULL, NULL),
	(55, NULL, 4, NULL, NULL),
	(55, NULL, 6, NULL, NULL),

	-- Internal 2.5" Drive Bays
	(56, NULL, 2, NULL, NULL),
	(56, NULL, 4, NULL, NULL),
	(56, NULL, 6, NULL, NULL),
    
	-- Base Frequency (MHz) for Graphics Cards
	(2, NULL, 2250, NULL, NULL),
	(2, NULL, 2105, NULL, NULL),
	(2, NULL, 1815, NULL, NULL),
	(2, NULL, 1605, NULL, NULL),
	(2, NULL, 1470, NULL, NULL),
	(2, NULL, 1440, NULL, NULL),
	(2, NULL, 1410, NULL, NULL),
	(2, NULL, 1400, NULL, NULL),
	(2, NULL, 1350, NULL, NULL),
	(2, NULL, 1515, NULL, NULL),

	-- Boost Frequency (MHz) for Graphics Cards
	(3, NULL, 2425, NULL, NULL),
	(3, NULL, 2360, NULL, NULL),
	(3, NULL, 2250, NULL, NULL),
	(3, NULL, 2170, NULL, NULL),
	(3, NULL, 1770, NULL, NULL),
	(3, NULL, 1740, NULL, NULL),
	(3, NULL, 1725, NULL, NULL),
	(3, NULL, 1710, NULL, NULL),
	(3, NULL, 1700, NULL, NULL),
	(3, NULL, 1620, NULL, NULL),
    
    -- Read Speed (MB/s) for Storage Devices
    (36, NULL, 150, NULL, NULL), 
    (36, NULL, 500, NULL, NULL), 
    (36, NULL, 2000, NULL, NULL), 
    (36, NULL, 2500, NULL, NULL), 
    (36, NULL, 5000, NULL, NULL),
    (36, NULL, 6000, NULL, NULL), 

	-- Write Speed (MB/s) for Storage Devices
    (37, NULL, 100, NULL, NULL), 
    (37, NULL, 450, NULL, NULL), 
    (37, NULL, 1200, NULL, NULL), 
    (37, NULL, 1500, NULL, NULL), 
    (37, NULL, 3500, NULL, NULL),
    (37, NULL, 4000, NULL, NULL),

	-- Max Power Consumption (W) for Power Supplies
    (44, NULL, 550, NULL, NULL),
    (44, NULL, 750, NULL, NULL), 
    (44, NULL, 850, NULL, NULL),
    (44, NULL, 1200, NULL, NULL),
    
    -- Brands for Cases
    (1, 'Fractal Design', NULL, NULL, NULL),
    (1, 'NZXT', NULL, NULL, NULL);
    
INSERT INTO `PRODUCT_ATTRIBUTE_VALUE` (`PRODUCT_ID`, `ATTRIBUTE_VALUE_ID`)
VALUES
    -- AMD Ryzen 9 7950X3D 4.2 GHz/5.7 GHz (Product ID: 1)
    (1, 2), -- Brand
    (1, 11), -- Base Frequency (MHz)
    (1, 22), -- Boost Frequency (MHz)
    (1, 43), -- Number of Cores
    (1, 54), -- Number of Threads
    (1, 58), -- L2 Cache Memory (KB)
    (1, 65), -- L3 Cache Memory (MB)
    (1, 74), -- TDP (Thermal Design Power, W)
    (1, 79), -- Socket
    (1, 84), -- Manufacturing Process (nm)
    (1, 86), -- Integrated Graphics
    (1, 88), -- Integrated Graphics Processor
    (1, 94), -- Integrated Graphics Max Frequency (MHz)
    (1, 96),
    
    -- AMD Ryzen 9 5950X 3.4 GHz (Product ID: 2)
    (2, 2), -- Brand
    (2, 12), -- Base Frequency (MHz)
    (2, 23), -- Boost Frequency (MHz)
    (2, 43), -- Number of Cores
    (2, 54), -- Number of Threads
    (2, 61), -- L2 Cache Memory (KB)
    (2, 66), -- L3 Cache Memory (MB)
    (2, 73), -- TDP (Thermal Design Power, W)
    (2, 78), -- Socket
    (2, 83), -- Manufacturing Process (nm)
    (2, 85), -- Integrated Graphics

	-- AMD Ryzen 7 7800X3D 4.2 GHz/5 GHz (Product ID: 3)
    (3, 2), -- Brand
    (3, 11), -- Base Frequency (MHz)
    (3, 24), -- Boost Frequency (MHz)
    (3, 41), -- Number of Cores
    (3, 50), -- Number of Threads
    (3, 61), -- L2 Cache Memory (KB)
    (3, 66), -- L3 Cache Memory (MB)
    (3, 74), -- TDP (Thermal Design Power, W)
    (3, 79), -- Socket
    (3, 84), -- Manufacturing Process (nm)
    (3, 86), -- Integrated Graphics
    (3, 88), -- Integrated Graphics Processor
    (3, 94), -- Integrated Graphics Max Frequency (MHz)
    (3, 96), -- Integrated Graphics Cores

    -- AMD Ryzen 7 5800X3D 3.4GHz (Product ID: 4)
    (4, 2), -- Brand
    (4, 12), -- Base Frequency (MHz)
    (4, 25), -- Boost Frequency (MHz)
    (4, 41), -- Number of Cores
    (4, 50), -- Number of Threads
    (4, 63), -- L2 Cache Memory (KB)
    (4, 66), -- L3 Cache Memory (MB)
    (4, 73), -- TDP (Thermal Design Power, W)
    (4, 78), -- Socket
    (4, 83), -- Manufacturing Process (nm)
    (4, 85), -- Integrated Graphics

    -- AMD Ryzen 5 7600X 4.7 GHz (Product ID: 5)
    (5, 2), -- Brand
    (5, 13), -- Base Frequency (MHz)
    (5, 26), -- Boost Frequency (MHz)
    (5, 41), -- Number of Cores
    (5, 49), -- Number of Threads
    (5, 62), -- L2 Cache Memory (KB)
    (5, 67), -- L3 Cache Memory (MB)
    (5, 72), -- TDP (Thermal Design Power, W)
    (5, 79), -- Socket
    (5, 84), -- Manufacturing Process (nm)
    (5, 86), -- Integrated Graphics
    (5, 88), -- Integrated Graphics Processor
    (5, 94), -- Integrated Graphics Max Frequency (MHz)
    (5, 96), -- Integrated Graphics Cores

    -- AMD Ryzen 5 5600X 3.7 GHz (Product ID: 6)
    (6, 2), -- Brand
    (6, 14), -- Base Frequency (MHz)
    (6, 27), -- Boost Frequency (MHz)
    (6, 40), -- Number of Cores
    (6, 49), -- Number of Threads
    (6, 64), -- L2 Cache Memory (KB)
    (6, 67), -- L3 Cache Memory (MB)
    (6, 72), -- TDP (Thermal Design Power, W)
    (6, 78), -- Socket
    (6, 83), -- Manufacturing Process (nm)
    (6, 85), -- Integrated Graphics

    -- Intel Core i7-14700K 3.4/5.6GHz (Product ID: 7)
    (7, 1), -- Brand
    (7, 12), -- Base Frequency (MHz)
    (7, 28), -- Boost Frequency (MHz)
    (7, 44), -- Number of Cores
    (7, 52), -- Number of Threads
    (7, 56), -- L2 Cache Memory (KB)
    (7, 69), -- L3 Cache Memory (MB)
    (7, 77), -- TDP (Thermal Design Power, W)
    (7, 81), -- Socket
    (7, 82), -- Manufacturing Process (nm)
    (7, 86), -- Integrated Graphics
    (7, 87), -- Integrated Graphics Processor
    (7, 89), -- Integrated Graphics Base Frequency (MHz)
    (7, 92), -- Integrated Graphics Max Frequency (MHz)
    (7, 95), -- Integrated Graphics Execution Units

    -- Intel Core i7-13700K 3.4 GHz (Product ID: 8)
    (8, 1), -- Brand
    (8, 12), -- Base Frequency (MHz)
    (8, 29), -- Boost Frequency (MHz)
    (8, 43), -- Number of Cores
    (8, 51), -- Number of Threads
    (8, 57), -- L2 Cache Memory (KB)
    (8, 70), -- L3 Cache Memory (MB)
    (8, 77), -- TDP (Thermal Design Power, W)
    (8, 81), -- Socket
    (8, 82), -- Manufacturing Process (nm)
    (8, 86), -- Integrated Graphics
    (8, 87), -- Integrated Graphics Processor
    (8, 89), -- Integrated Graphics Base Frequency (MHz)
    (8, 92), -- Integrated Graphics Max Frequency (MHz)
    (8, 95), -- Integrated Graphics Execution Units

    -- Intel Core i7-12700K 3.6 GHz (Product ID: 9)
    (9, 1), -- Brand
    (9, 15), -- Base Frequency (MHz)
    (9, 24), -- Boost Frequency (MHz)
    (9, 42), -- Number of Cores
    (9, 50), -- Number of Threads
    (9, 60), -- L2 Cache Memory (KB)
    (9, 71), -- L3 Cache Memory (MB)
    (9, 75), -- TDP (Thermal Design Power, W)
    (9, 80), -- Socket
    (9, 82), -- Manufacturing Process (nm)
    (9, 86), -- Integrated Graphics
    (9, 87), -- Integrated Graphics Processor
    (9, 89), -- Integrated Graphics Base Frequency (MHz)
    (9, 90), -- Integrated Graphics Max Frequency (MHz)
    (9, 95), -- Integrated Graphics Execution Units

    -- Intel Core i9-14900K 3.2/6GHz (Product ID: 10)
    (10, 1), -- Brand
    (10, 16), -- Base Frequency (MHz)
    (10, 30), -- Boost Frequency (MHz)
    (10, 45), -- Number of Cores
    (10, 54), -- Number of Threads
    (10, 55), -- L2 Cache Memory (KB)
    (10, 68), -- L3 Cache Memory (MB)
    (10, 77), -- TDP (Thermal Design Power, W)
    (10, 81), -- Socket
    (10, 82), -- Manufacturing Process (nm)
    (10, 86), -- Integrated Graphics
    (10, 87), -- Integrated Graphics Processor
    (10, 89), -- Integrated Graphics Base Frequency (MHz)
    (10, 93), -- Integrated Graphics Max Frequency (MHz)
    (10, 95), -- Integrated Graphics Execution Units

    -- Intel Core i9-13900K 3 GHz (Product ID: 11)
    (11, 1), -- Brand
    (11, 17), -- Base Frequency (MHz)
    (11, 31), -- Boost Frequency (MHz)
    (11, 43), -- Number of Cores
    (11, 54), -- Number of Threads
    (11, 55), -- L2 Cache Memory (KB)
    (11, 68), -- L3 Cache Memory (MB)
    (11, 77), -- TDP (Thermal Design Power, W)
    (11, 81), -- Socket
    (11, 82), -- Manufacturing Process (nm)
    (11, 86), -- Integrated Graphics
    (11, 87), -- Integrated Graphics Processor
    (11, 89), -- Integrated Graphics Base Frequency (MHz)
    (11, 93), -- Integrated Graphics Max Frequency (MHz)
    (11, 95), -- Integrated Graphics Execution Units

	-- Intel Core i9-12900K 3.2 GHz (Product ID: 11)
    (12, 1), -- Brand
    (12, 16), -- Base Frequency (MHz)
    (12, 32), -- Boost Frequency (MHz)
    (12, 43), -- Number of Cores
    (12, 52), -- Number of Threads
    (12, 59), -- L2 Cache Memory (KB)
    (12, 70), -- L3 Cache Memory (MB)
    (12, 76), -- TDP (Thermal Design Power, W)
    (12, 81), -- Socket
    (12, 82), -- Manufacturing Process (nm)
    (12, 86), -- Integrated Graphics
    (12, 87), -- Integrated Graphics Processor
    (12, 89), -- Integrated Graphics Base Frequency (MHz)
    (12, 91), -- Integrated Graphics Max Frequency (MHz)
    (12, 95), -- Integrated Graphics Execution Units
    
	-- Gigabyte B650 Aorus Elite AX
	(13, 4),  -- Brand: Gigabyte
	(13, 109), -- Chipset: AMD B650
	(13, 79), -- Socket: AM5
	(13, 98), -- Memory Type: DDR5
	(13, 101), -- Form Factor: ATX
	(13, 112), -- Memory Slots: 4
	(13, 115), -- Max Memory (GB): 128
	(13, 118), -- PCIe x16 Slots: 1
	(13, 121), -- PCIe x1 Slots: 1
	(13, 126), -- SATA III Ports: 6
	(13, 129), -- M.2 Slots: 2

	-- Gigabyte B550 Aorus Elite AX
	(14, 4), -- Brand: Gigabyte
	(14, 107), -- Chipset: AMD B550
	(14, 78), -- Socket: AM4
	(14, 97), -- Memory Type: DDR4
	(14, 101), -- Form Factor: ATX
	(14, 112), -- Memory Slots: 4
	(14, 117), -- Max Memory (GB): 32
	(14, 119), -- PCIe x16 Slots: 2
	(14, 121), -- PCIe x1 Slots: 1
	(14, 126), -- SATA III Ports: 6
	(14, 129), -- M.2 Slots: 2

	-- Gigabyte Z790 Aorus Elite AX
	(15, 4), -- Brand: Gigabyte
	(15, 106), -- Chipset: Intel Z790
	(15, 81), -- Socket: LGA 1700
	(15, 98), -- Memory Type: DDR5
	(15, 101), -- Form Factor: ATX
	(15, 112), -- Memory Slots: 4
	(15, 115), -- Max Memory (GB): 128
	(15, 120), -- PCIe x16 Slots: 3
	(15, 122), -- PCIe x1 Slots: 2
	(15, 127), -- SATA III Ports: 8
	(15, 130), -- M.2 Slots: 3

	-- Gigabyte Z690 Gaming X DDR4
	(16, 4), -- Brand: Gigabyte
	(16, 105), -- Chipset: Intel Z690
	(16, 80), -- Socket: LGA 1200
	(16, 98), -- Memory Type: DDR5
	(16, 101), -- Form Factor: ATX
	(16, 112), -- Memory Slots: 4
	(16, 116), -- Max Memory (GB): 164
	(16, 119), -- PCIe x16 Slots: 2
	(16, 122), -- PCIe x1 Slots: 2
	(16, 126), -- SATA III Ports: 6
	(16, 130), -- M.2 Slots: 3

	-- Gigabyte Z590 Aorus Master
	(17, 4), -- Brand: Gigabyte
	(17, 104), -- Chipset: Intel Z590
	(17, 80), -- Socket: LGA 1200
	(17, 97), -- Memory Type: DDR4
	(17, 101), -- Form Factor: ATX
	(17, 112), -- Memory Slots: 4
	(17, 115), -- Max Memory (GB): 128
	(17, 120), -- PCIe x16 Slots: 3
	(17, 123), -- PCIe x1 Slots: 3
	(17, 126), -- SATA III Ports: 6
	(17, 130), -- M.2 Slots: 3

	-- ASUS ROG STRIX X670E-F GAMING WIFI
	(18, 3), -- Brand: ASUS
	(18, 111), -- Chipset: AMD X670E
	(18, 79), -- Socket: AM5
	(18, 98), -- Memory Type: DDR5
	(18, 101), -- Form Factor: ATX
	(18, 112), -- Memory Slots: 4
	(18, 115), -- Max Memory (GB): 128
	(18, 118), -- PCIe x16 Slots: 1
	(18, 121), -- PCIe x1 Slots: 1
	(18, 125), -- SATA III Ports: 4
	(18, 128), -- M.2 Slots: 1

	-- ASUS ROG Strix B550-F GAMING
	(19, 3), -- Brand: ASUS
	(19, 107), -- Chipset: AMD B550
	(19, 78), -- Socket: AM4
	(19, 97), -- Memory Type: DDR4
	(19, 101), -- Form Factor: ATX
	(19, 112), -- Memory Slots: 4
	(19, 115), -- Max Memory (GB): 128
	(19, 119), -- PCIe x16 Slots: 2
	(19, 121), -- PCIe x1 Slots: 1
	(19, 126), -- SATA III Ports: 6
	(19, 129), -- M.2 Slots: 2

	-- Asus ROG STRIX Z690-E GAMING WIFI
	(20, 3), -- Brand: ASUS
	(20, 105), -- Chipset: Intel Z690
	(20, 80), -- Socket: LGA 1200
	(20, 98), -- Memory Type: DDR5
	(20, 101), -- Form Factor: ATX
	(20, 112), -- Memory Slots: 4
	(20, 117), -- Max Memory (GB): 32
	(20, 120), -- PCIe x16 Slots: 3
	(20, 123), -- PCIe x1 Slots: 3
	(20, 126), -- SATA III Ports: 6
	(20, 130), -- M.2 Slots: 3

	-- Asus ROG STRIX Z590-E GAMING WIFI
	(21, 3), -- Brand: ASUS
	(21, 104), -- Chipset: Intel Z590
	(21, 80), -- Socket: LGA 1200
	(21, 97), -- Memory Type: DDR4
	(21, 101), -- Form Factor: ATX
	(21, 112), -- Memory Slots: 4
	(21, 115), -- Max Memory (GB): 128
	(21, 120), -- PCIe x16 Slots: 3
	(21, 123), -- PCIe x1 Slots: 3
	(21, 126), -- SATA III Ports: 6
	(21, 130), -- M.2 Slots: 3

	-- ASUS ROG Maximus Z790 Hero
	(22, 3), -- Brand: ASUS
	(22, 106), -- Chipset: Intel Z790
	(22, 81), -- Socket: LGA 1700
	(22, 98), -- Memory Type: DDR5
	(22, 101), -- Form Factor: ATX
	(22, 112), -- Memory Slots: 4
	(22, 115), -- Max Memory (GB): 128
	(22, 120), -- PCIe x16 Slots: 3
	(22, 123), -- PCIe x1 Slots: 3
	(22, 126), -- SATA III Ports: 6
	(22, 130), -- M.2 Slots: 3

	-- Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16
	(23, 5),  -- Brand: Corsair
	(23, 97), -- Memory Type: DDR4
	(23, 132), -- RAM Capacity (GB): 16
	(23, 136), -- Frequency (MHz): 3200
	(23, 142), -- CAS Latency: 16
	(23, 147), -- Voltage (V): 1.35
	(23, 150), -- Heat Spreader: True
	(23, 152), -- Number of RAM Modules: 2

	-- Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16
	(24, 5), -- Brand: Corsair
	(24, 97), -- Memory Type: DDR4
	(24, 133), -- RAM Capacity (GB): 32
	(24, 136), -- Frequency (MHz): 3200
	(24, 142), -- CAS Latency: 16
	(24, 147), -- Voltage (V): 1.35
	(24, 150), -- Heat Spreader: True
	(24, 152), -- Number of RAM Modules: 2

	-- Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Negra
	(25, 5), -- Brand: Corsair
	(25, 98), -- Memory Type: DDR5
	(25, 133), -- RAM Capacity (GB): 32
	(25, 140), -- Frequency (MHz): 6000
	(25, 144), -- CAS Latency: 22
	(25, 145), -- Voltage (V): 1.1
	(25, 150), -- Heat Spreader: True
	(25, 152), -- Number of RAM Modules: 2

	-- Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32
	(26, 5), -- Brand: Corsair
	(26, 98), -- Memory Type: DDR5
	(26, 134), -- RAM Capacity (GB): 64
	(26, 141), -- Frequency (MHz): 6400
	(26, 143), -- CAS Latency: 18
	(26, 146), -- Voltage (V): 1.2
	(26, 150), -- Heat Spreader: True
	(26, 152), -- Number of RAM Modules: 2
    
	-- Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500GB
	(27, 6),  -- Brand: Samsung
	(27, 156), -- Storage Form Factor: M.2
	(27, 160), -- Storage Capacity (GB): 500
	(27, 168), -- Interface: NVMe
	(27, 175), -- NAND Type: TLC
	(27, 182), -- Endurance (TBW): 600
	(27, 278), -- Read Speed (MB/s): 6000
	(27, 284), -- Write Speed (MB/s): 4000

	-- Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1TB
	(28, 6),  -- Brand: Samsung
	(28, 156), -- Storage Form Factor: M.2
	(28, 161), -- Storage Capacity (GB): 1000
	(28, 168), -- Interface: NVMe
	(28, 175), -- NAND Type: TLC
	(28, 184), -- Endurance (TBW): 1000
	(28, 278), -- Read Speed (MB/s): 6000
	(28, 284), -- Write Speed (MB/s): 4000

	-- Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2TB
	(29, 6),  -- Brand: Samsung
	(29, 156), -- Storage Form Factor: M.2
	(29, 162), -- Storage Capacity (GB): 2000
	(29, 168), -- Interface: NVMe
	(29, 175), -- NAND Type: TLC
	(29, 186), -- Endurance (TBW): 1500
	(29, 278), -- Read Speed (MB/s): 6000
	(29, 284), -- Write Speed (MB/s): 4000

	-- Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500GB
	(30, 6),  -- Brand: Samsung
	(30, 156), -- Storage Form Factor: M.2
	(30, 160), -- Storage Capacity (GB): 500
	(30, 168), -- Interface: NVMe
	(30, 175), -- NAND Type: TLC
	(30, 182), -- Endurance (TBW): 600
	(30, 277), -- Read Speed (MB/s): 5000
	(30, 283), -- Write Speed (MB/s): 3500

	-- Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1TB
	(31, 6),  -- Brand: Samsung
	(31, 156), -- Storage Form Factor: M.2
	(31, 161), -- Storage Capacity (GB): 1000
	(31, 168), -- Interface: NVMe
	(31, 175), -- NAND Type: TLC
	(31, 184), -- Endurance (TBW): 1000
	(31, 277), -- Read Speed (MB/s): 5000
	(31, 283), -- Write Speed (MB/s): 3500

	-- Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2TB
	(32, 6),  -- Brand: Samsung
	(32, 156), -- Storage Form Factor: M.2
	(32, 162), -- Storage Capacity (GB): 2000
	(32, 168), -- Interface: NVMe
	(32, 175), -- NAND Type: TLC
	(32, 186), -- Endurance (TBW): 1500
	(32, 277), -- Read Speed (MB/s): 5000
	(32, 283), -- Write Speed (MB/s): 3500

	-- Samsung 970 EVO Plus SSD NVMe M.2 500GB
	(33, 6),  -- Brand: Samsung
	(33, 156), -- Storage Form Factor: M.2
	(33, 160), -- Storage Capacity (GB): 500
	(33, 168), -- Interface: NVMe
	(33, 175), -- NAND Type: TLC
	(33, 182), -- Endurance (TBW): 600
	(33, 276), -- Read Speed (MB/s): 2500
	(33, 282), -- Write Speed (MB/s): 1500

	-- Samsung 970 EVO Plus SSD NVMe M.2 1TB
	(34, 6),  -- Brand: Samsung
	(34, 156), -- Storage Form Factor: M.2
	(34, 161), -- Storage Capacity (GB): 1000
	(34, 168), -- Interface: NVMe
	(34, 175), -- NAND Type: TLC
	(34, 184), -- Endurance (TBW): 1000
	(34, 276), -- Read Speed (MB/s): 2500
	(34, 282), -- Write Speed (MB/s): 1500

	-- Samsung 970 EVO Plus SSD NVMe M.2 2TB
	(35, 6),  -- Brand: Samsung
	(35, 156), -- Storage Form Factor: M.2
	(35, 162), -- Storage Capacity (GB): 2000
	(35, 168), -- Interface: NVMe
	(35, 175), -- NAND Type: TLC
	(35, 186), -- Endurance (TBW): 1500
	(35, 276), -- Read Speed (MB/s): 2500
	(35, 282), -- Write Speed (MB/s): 1500

	-- WD Red SA500 NAS SSD SATA M.2 2280 500GB
	(36, 7),  -- Brand: Western Digital
	(36, 156), -- Storage Form Factor: M.2
	(36, 160), -- Storage Capacity (GB): 500
	(36, 167), -- Interface: SATA III
	(36, 174), -- NAND Type: QLC
	(36, 178), -- Endurance (TBW): 120
	(36, 274), -- Read Speed (MB/s): 500
	(36, 280), -- Write Speed (MB/s): 450

	-- WD Red SA500 NAS SSD SATA M.2 2280 1TB
	(37, 7),  -- Brand: Western Digital
	(37, 156), -- Storage Form Factor: M.2
	(37, 161), -- Storage Capacity (GB): 1000
	(37, 167), -- Interface: SATA III
	(37, 174), -- NAND Type: QLC
	(37, 179), -- Endurance (TBW): 250
	(37, 274), -- Read Speed (MB/s): 500
	(37, 280), -- Write Speed (MB/s): 450

	-- WD Red SA500 NAS SSD SATA M.2 2280 2TB
	(38, 7),  -- Brand: Western Digital
	(38, 156), -- Storage Form Factor: M.2
	(38, 162), -- Storage Capacity (GB): 2000
	(38, 167), -- Interface: SATA III
	(38, 174), -- NAND Type: QLC
	(38, 180), -- Endurance (TBW): 300
	(38, 274), -- Read Speed (MB/s): 500
	(38, 280), -- Write Speed (MB/s): 450

	-- Western Digital Blue 2.5" SSD 1TB SATA 3
	(39, 7),  -- Brand: Western Digital
	(39, 154), -- Storage Form Factor: 2.5"
	(39, 161), -- Storage Capacity (GB): 1000
	(39, 167), -- Interface: SATA III
	(39, 174), -- NAND Type: QLC
	(39, 184), -- Endurance (TBW): 1000
	(39, 274), -- Read Speed (MB/s): 500
	(39, 280), -- Write Speed (MB/s): 450

	-- Western Digital Red Plus 3.5" SATA3 2TB
	(40, 7),  -- Brand: Western Digital
	(40, 155), -- Storage Form Factor: 3.5"
	(40, 162), -- Storage Capacity (GB): 2000
	(40, 167), -- Interface: SATA III
	(40, 191), -- Rotational Speed (RPM): 7200
	(40, 273), -- Read Speed (MB/s): 150
	(40, 279), -- Write Speed (MB/s): 100

	-- Western Digital Red Plus 3.5" SATA3 3TB
	(41, 7),  -- Brand: Western Digital
	(41, 155), -- Storage Form Factor: 3.5"
	(41, 163), -- Storage Capacity (GB): 3000
	(41, 167), -- Interface: SATA III
	(41, 191), -- Rotational Speed (RPM): 7200
	(41, 273), -- Read Speed (MB/s): 150
	(41, 279), -- Write Speed (MB/s): 100

	-- Western Digital Red Plus 3.5" SATA3 4TB
	(42, 7),  -- Brand: Western Digital
	(42, 155), -- Storage Form Factor: 3.5"
	(42, 164), -- Storage Capacity (GB): 4000
	(42, 167), -- Interface: SATA III
	(42, 191), -- Rotational Speed (RPM): 7200
	(42, 273), -- Read Speed (MB/s): 150
	(42, 279), -- Write Speed (MB/s): 100
    
	-- EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X
    (43, 9), -- Brand: EVGA
	(43, 259), -- Base Frequency (MHz): 1410
	(43, 270), -- Boost Frequency (MHz): 1770
	(43, 223), -- TDP (W): 350
	(43, 83),  -- Manufacturing Process (nm): 7
	(43, 100), -- Memory Type: GDDR6X
	(43, 196), -- CUDA Cores: 8704
	(43, 210), -- Memory Size (GB): 10

	-- EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X
	(44, 9), -- Brand: EVGA
	(44, 260), -- Base Frequency (MHz): 1400
	(44, 272), -- Boost Frequency (MHz): 1620
	(44, 223), -- TDP (W): 350
	(44, 82),  -- Manufacturing Process (nm): 10
	(44, 100), -- Memory Type: GDDR6X
	(44, 205), -- CUDA Cores: 2944
	(44, 211), -- Memory Size (GB): 8

	-- ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3
    (45, 3), -- Brand: ASUS
	(45, 255), -- Base Frequency (MHz): 1815
	(45, 265), -- Boost Frequency (MHz): 2250
	(45, 222), -- TDP (W): 450
	(45, 84),  -- Manufacturing Process (nm): 5
	(45, 100), -- Memory Type: GDDR6X
	(45, 195), -- CUDA Cores: 10752
	(45, 208), -- Memory Size (GB): 16

	-- Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6
    (46, 4), -- Brand: GIGABYTE
	(46, 261), -- Base Frequency (MHz): 1350
	(46, 269), -- Boost Frequency (MHz): 1725
	(46, 224), -- TDP (W): 220
	(46, 84),  -- Manufacturing Process (nm): 5
	(46, 99),  -- Memory Type: GDDR6
	(46, 209), -- Memory Size (GB): 12

	-- Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6
    (47, 4), -- Brand: GIGABYTE
	(47, 259), -- Base Frequency (MHz): 1410
	(47, 271), -- Boost Frequency (MHz): 1700
	(47, 225), -- TDP (W): 130
	(47, 82),  -- Manufacturing Process (nm): 10
	(47, 99),  -- Memory Type: GDDR6
	(47, 209), -- Memory Size (GB): 12

	-- ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6
	(48, 3), -- Brand: ASUS
	(48, 255), -- Base Frequency (MHz): 1815
	(48, 264), -- Boost Frequency (MHz): 2360
	(48, 222), -- TDP (W): 450
	(48, 84),  -- Manufacturing Process (nm): 5
	(48, 99),  -- Memory Type: GDDR6
	(48, 207), -- Memory Size (GB): 24
    
    -- EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Fully Modular
    (53, 9), -- Brand: EVGA
    (53, 286),  -- Max Power Consumption (W): 550 W
    (53, 227),  -- Efficiency Rating: 80 Plus Gold
    (53, 231),  -- Modularity: True

	-- Corsair RM850x, 850W, 80 Plus Gold, Fully Modular
    (54, 5), -- Brand: Corsair
    (54, 287),  -- Max Power Consumption (W): 850 W
    (54, 227),  -- Efficiency Rating: 80 Plus Gold
    (54, 231),  -- Modularity: True

	-- Corsair HX1200i, 1200W, 80 Plus Platinum, Fully Modular
    (55, 5), -- Brand: Corsair
    (55, 288),  -- Max Power Consumption (W): 1200 W
    (55, 228),  -- Efficiency Rating: 80 Plus Platinum
    (55, 231),  -- Modularity: True
    
    -- Fractal Design Node 202 Mini ITX Desktop Case
    (49, 289), -- Brand: Fractal Design
    (49, 103), -- Form Factor: Mini ITX
    (49, 237), -- Maximum GPU Length: 380 mm
    (49, 240), -- Maximum CPU Cooler Height: 200 mm
    (49, 243), -- Maximum PSU Length: 260 mm
    (49, 244), -- Internal 5.25" Drive Bays: 0
    (49, 247), -- Internal 3.5" Drive Bays: 2
    (49, 250), -- Internal 2.5" Drive Bays: 2

	-- NZXT H510 Compact ATX Mid-Tower Case
    (50, 290), -- Brand: NZXT
    (50, 101), -- Form Factor: ATX
    (50, 235), -- Maximum GPU Length: 330 mm
    (50, 239), -- Maximum CPU Cooler Height: 180 mm
    (50, 242), -- Maximum PSU Length: 220 mm
    (50, 244), -- Internal 5.25" Drive Bays: 0
    (50, 247), -- Internal 3.5" Drive Bays: 2
    (50, 250), -- Internal 2.5" Drive Bays: 2

	-- Cooler Master MasterBox Q300L mATX Tower Case
    (51, 10), -- Brand: Cooler Master
    (51, 102), -- Form Factor: Micro ATX
    (51, 236), -- Maximum GPU Length: 290 mm
    (51, 238), -- Maximum CPU Cooler Height: 160 mm
    (51, 241), -- Maximum PSU Length: 180 mm
    (51, 244), -- Internal 5.25" Drive Bays: 0
    (51, 248), -- Internal 3.5" Drive Bays: 4
    (51, 251), -- Internal 2.5" Drive Bays: 4

	-- Fractal Design Meshify C ATX Mid Tower Case
    (52, 289), -- Brand: Fractal Design
    (52, 101), -- Form Factor: ATX
    (52, 237), -- Maximum GPU Length: 380 mm
    (52, 240), -- Maximum CPU Cooler Height: 200 mm
    (52, 242), -- Maximum PSU Length: 220 mm
    (52, 244), -- Internal 5.25" Drive Bays: 0
    (52, 247), -- Internal 3.5" Drive Bays: 2
    (52, 250); -- Internal 2.5" Drive Bays: 2
