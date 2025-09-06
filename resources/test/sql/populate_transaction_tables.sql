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
TRUNCATE TABLE `EMPLOYEE_ROLE`;
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

INSERT INTO `EMPLOYEE_ROLE` (`EMPLOYEE_ID`, `ROLE_ID`, `START_DATE`, `END_DATE`)
VALUES
    (1, 'admin', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (2, 'marketing', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (3, 'hr', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (4, 'sales', str_to_date('2022-11-16', '%Y-%m-%d'), NULL),
    (5, 'finance', str_to_date('2023-01-16', '%Y-%m-%d'), NULL),
    (6, 'ops', str_to_date('2023-01-16', '%Y-%m-%d'), str_to_date('2024-01-15', '%Y-%m-%d')),
    (6, 'support', str_to_date('2024-01-16', '%Y-%m-%d'), NULL),
    (7, 'support', str_to_date('2023-03-16', '%Y-%m-%d'), str_to_date('2023-12-16', '%Y-%m-%d')),
    (7, 'finance', str_to_date('2023-12-17', '%Y-%m-%d'), NULL);

INSERT INTO `CUSTOMER` (`FIRST_NAME`, `LAST_NAME1`, `LAST_NAME2`, `DOCUMENT_TYPE_ID`, `DOCUMENT_NUMBER`, `PHONE`, `EMAIL`, `PASSWORD`, `CREATION_DATE`)
VALUES
	('Ethan', 'Davis', NULL, 'NIE', 'FG876543', 890123456, 'ethan@example.com', '2Qs/8kHxDLdb8FJBJzSpxJb+U2F7+kJ7LT7AQnTAKDZlR50ekYjwEv5fekvumtIh', str_to_date('2022-11-16', '%Y-%m-%d')),
	('Ava', 'Rodriguez', 'López', 'PPT', 'IJ123456', 901234567, 'ava@example.com', '6272a94b52b95b50a5304af63d146fe8c7151e094927437e0cf2919f212888ff', str_to_date('2022-11-16', '%Y-%m-%d')), -- passabc
	('Daniel', 'Wilson', NULL, 'NIF', 'KL654321', 123456789, 'daniel@example.com', '7913d6c6ca2d3c8913d830ca150460974d1aace643ae24bcfdf1e661862d2b9b', str_to_date('2022-11-16', '%Y-%m-%d')), -- passdef
	('Mia', 'Miller', NULL, 'NIE', 'MN234567', 234567890, 'mia@example.com', '15b412db9e0bb9edce6096d33ebcc956f6d86d4e993edecfadab267daf5ab666', str_to_date('2023-01-16', '%Y-%m-%d')), -- passghi
	('Alexander', 'Taylor', NULL, 'PPT', 'OP123456', 345678901, 'alexander@example.com', 'fe2fd51ff6cb2db6fe8dc0f77e318e646017824f0893e6f83e55cd4d6db65bfa', str_to_date('2023-01-16', '%Y-%m-%d')), -- passjkl
	('Chloe', 'Anderson', NULL, 'NIF', 'QR765432', 456789012, 'chloe@example.com', '8d643b0613bd258518528c24e0a07d51ee89c26e1d57775df782b2e3107e470c', str_to_date('2023-04-16', '%Y-%m-%d')), -- passmno
	('John', 'Cena', NULL, 'FOR', 'ST555555', 455555555, 'cant@see.me', 'dff1ad49ede4b12cd8bc9952c37b12a7557e5c33096a9e7e5b83db49a317da93', str_to_date('2023-04-16', '%Y-%m-%d')); -- passpqr
    
INSERT INTO `ADDRESS` (`NAME`, `CUSTOMER_ID`, `CITY_ID`, `STREET_NAME`, `STREET_NUMBER`, `FLOOR`, `DOOR`, `ZIP_CODE`, `IS_DEFAULT`, `IS_BILLING`, `CREATION_DATE`)
VALUES
	('My Address 1', 1, 1, 'Calle Gran Vía', 1, 3, 'Apt 5', 28013, true, false, str_to_date('2022-11-16', '%Y-%m-%d')),
	('My Address 2', 2, 6, 'Avinguda Diagonal', 123, NULL, 'Apt 10', 08019, true, true, str_to_date('2022-11-16', '%Y-%m-%d')),
	('My Address 3', 3, 11, 'Carrer de la Pau', 45, 5, 'Apt 15', 46002, true, true, str_to_date('2022-11-16', '%Y-%m-%d')),
	('My Address 4', 4, 16, 'Calle Sierpes', 8, NULL, 'Apt 20', 41004, true, true, str_to_date('2023-01-16', '%Y-%m-%d')),
	('My Address 5', 5, 5, 'Carrer de les Flors', 27, 7, 'Apt 25', 08001, true, true, str_to_date('2023-01-16', '%Y-%m-%d')),
	('My Address 6', 6, 6, 'Passeig de Gràcia', 33, NULL, 'Apt 30', 08007, true, true, str_to_date('2023-04-16', '%Y-%m-%d')),
	('My Address 7', 7, 8, 'Calle León y Castillo', 271, 9, 'Apt 35', 35005, true, true, str_to_date('2023-04-16', '%Y-%m-%d')),
    ('My Address 8', 1, 1, 'Calle Alcalá', 100, 4, 'Apt 6', 28009, false, true, str_to_date('2023-07-16', '%Y-%m-%d')),
	('My Address 9', 1, 1, 'Calle del Arenal', 67, 6, 'Apt 7', 28013, false, false, str_to_date('2023-08-16', '%Y-%m-%d')),
	('My Address 10', 2, 6, 'Rambla de Catalunya', 89, 2, 'Apt 12', 08008, false, false, str_to_date('2023-09-16', '%Y-%m-%d'));

    
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
    (1, 'en-GB', 'AMD Ryzen 9 7950X3D 4.2 GHz/5.7 GHz', 'The AMD Ryzen 9 7950X3D is a high-performance processor with 16 cores and 32 threads. With a base clock of 4.2 GHz and a boost clock of 5.7 GHz, it is designed for demanding applications like gaming, content creation, and multitasking. Built on AMD\'s Zen 4 architecture, it delivers exceptional processing power and energy efficiency, making it a top choice for enthusiasts and professionals alike.'),
    (2, 'en-GB', 'AMD Ryzen 9 5950X 3.4 GHz', 'The AMD Ryzen 9 5950X offers 16 cores and 32 threads, providing incredible multitasking and parallel processing performance. With a base clock of 3.4 GHz and the ability to boost higher, it excels in tasks like gaming, video editing, and 3D rendering. This processor is ideal for power users who demand maximum performance.'),
    (3, 'en-GB', 'AMD Ryzen 7 7800X3D 4.2 GHz/5 GHz', 'The AMD Ryzen 7 7800X3D delivers 8 cores and 16 threads with a base clock of 4.2 GHz, reaching up to 5 GHz boost clock speeds. It features AMD\'s innovative 3D V-Cache technology, enhancing gaming and application performance with increased cache size. Perfect for gamers and content creators seeking high performance with the latest technology.'),
    (4, 'en-GB', 'AMD Ryzen 7 5800X3D 3.4GHz', 'The AMD Ryzen 7 5800X3D is a high-performance processor featuring 8 cores and 16 threads. With a base clock of 3.4 GHz and AMD\'s 3D V-Cache technology, it delivers impressive gaming performance and speed in intensive tasks. The 3D V-Cache enhances performance by significantly increasing cache capacity, making this processor ideal for gamers and creators alike.'),
    (5, 'en-GB', 'AMD Ryzen 5 7600X 4.7 GHz', 'The AMD Ryzen 5 7600X is a 6-core, 12-thread processor that offers a base clock of 4.7 GHz, making it perfect for gaming, streaming, and productivity tasks. With impressive single-core performance, it excels in both gaming and everyday tasks. The Ryzen 5 7600X delivers high performance at an affordable price.'),
    (6, 'en-GB', 'AMD Ryzen 5 5600X 3.7 GHz', 'The AMD Ryzen 5 5600X features 6 cores and 12 threads, offering a base clock of 3.7 GHz with the ability to boost higher. It provides excellent gaming and productivity performance, making it a great option for gamers and PC enthusiasts looking for solid performance without breaking the bank.'),

    -- Intel processors
    (7, 'en-GB', 'Intel Core i7-14700K 3.4/5.6GHz', 'The Intel Core i7-14700K is a powerful processor with 8 cores and 16 threads, providing a base clock of 3.4 GHz and turbo boost up to 5.6 GHz. It offers excellent performance for gaming, content creation, and multitasking. With Intel\'s advanced architecture, it delivers great efficiency and speed, making it an excellent choice for high-performance computing.'),
    (8, 'en-GB', 'Intel Core i7-13700K 3.4 GHz', 'The Intel Core i7-13700K features 8 cores and 16 threads, with a base clock of 3.4 GHz and high turbo speeds. It delivers exceptional performance for gaming and demanding workloads, with the power to handle multitasking and content creation. The i7-13700K is a great choice for gamers and professionals who need a balance of performance and value.'),
    (9, 'en-GB', 'Intel Core i7-12700K 3.6 GHz', 'The Intel Core i7-12700K offers 12 cores and 20 threads with a base clock of 3.6 GHz. This processor is perfect for gaming, streaming, and content creation, delivering fast, reliable performance in both single-core and multi-core workloads. The i7-12700K is ideal for users who need power for complex tasks and high-performance gaming.'),
    (10, 'en-GB', 'Intel Core i9-14900K 3.2/6GHz', 'The Intel Core i9-14900K is a flagship processor with 8 performance cores and 16 efficiency cores, reaching speeds of up to 6 GHz. Offering extreme performance in gaming, 4K video editing, and other demanding tasks, it is designed for enthusiasts and professionals who require the best performance available.'),
    (11, 'en-GB', 'Intel Core i9-13900K 3 GHz', 'The Intel Core i9-13900K offers 8 performance cores and 16 efficiency cores, with a base clock of 3 GHz. This processor delivers outstanding performance in both gaming and productivity applications, making it a great choice for enthusiasts looking for top-tier power and speed.'),
    (12, 'en-GB', 'Intel Core i9-12900K 3.2 GHz', 'The Intel Core i9-12900K is a high-performance processor featuring 8 performance cores and 8 efficiency cores, with a base clock of 3.2 GHz. This processor is built for gamers, streamers, and creators who demand the best performance for multi-threaded workloads and single-threaded applications.'),

    -- Motherboards for Gigabyte brand
    (13, 'en-GB', 'Gigabyte B650 Aorus Elite AX', 'The Gigabyte B650 Aorus Elite AX motherboard features support for AMD Ryzen 7000 series processors with an AM5 socket. It offers robust power delivery, PCIe 5.0 support, Wi-Fi 6E, and excellent expandability with multiple M.2 slots and USB ports. Ideal for gamers and content creators looking for a high-performance, reliable motherboard for their next build.'),
    (14, 'en-GB', 'Gigabyte B550 Aorus Elite AX', 'The Gigabyte B550 Aorus Elite AX motherboard is designed for AMD Ryzen 5000 series processors, offering a solid foundation for gaming and performance. With PCIe 4.0 support, Wi-Fi 6, and multiple M.2 slots for high-speed storage, it provides excellent value for users looking for performance and connectivity at a competitive price.'),
    (15, 'en-GB', 'Gigabyte Z790 Aorus Elite AX', 'The Gigabyte Z790 Aorus Elite AX motherboard supports Intel 12th and 13th generation processors with an LGA 1700 socket. With advanced power delivery, PCIe 5.0, Wi-Fi 6, and robust cooling solutions, it’s designed for enthusiasts and gamers seeking exceptional performance and expandability.'),
    (16, 'en-GB', 'Gigabyte Z690 Gaming X DDR4', 'The Gigabyte Z690 Gaming X DDR4 motherboard is perfect for gamers who want to build a high-performance PC using Intel’s 12th Gen Alder Lake processors. With DDR4 memory support, PCIe 5.0, and advanced connectivity options, this motherboard provides a balanced mix of performance and features for competitive gamers.'),
    (17, 'en-GB', 'Gigabyte Z590 Aorus Master', 'The Gigabyte Z590 Aorus Master motherboard is a premium choice for Intel’s 10th and 11th generation processors. It offers robust power delivery, PCIe 4.0, and an array of high-end features such as Wi-Fi 6, 10GbE LAN, and advanced cooling, making it ideal for gamers and overclocking enthusiasts.'),
    
    -- Motherboards for ASUS brand
    (18, 'en-GB', 'ASUS ROG STRIX X670E-F GAMING WIFI', 'The ASUS ROG STRIX X670E-F GAMING WIFI motherboard supports AMD Ryzen 7000 series processors with an AM5 socket. Equipped with PCIe 5.0, Wi-Fi 6E, and premium power delivery systems, it’s designed for gamers and content creators who demand cutting-edge technology and top-tier performance.'),
    (19, 'en-GB', 'ASUS ROG Strix B550-F GAMING', 'The ASUS ROG Strix B550-F GAMING motherboard offers support for AMD Ryzen 5000 series processors, with PCIe 4.0, Wi-Fi 6, and robust power design. Its versatility and affordability make it a solid choice for gamers looking for great performance and connectivity.'),
    (20, 'en-GB', 'Asus ROG STRIX Z690-E GAMING WIFI', 'The ASUS ROG STRIX Z690-E GAMING WIFI motherboard offers excellent performance with Intel 12th Gen Alder Lake processors. Featuring PCIe 5.0, Wi-Fi 6E, and a premium power delivery system, it’s designed for gamers who need reliable, high-performance hardware for demanding applications and high-end gaming.'),
    (21, 'en-GB', 'Asus ROG STRIX Z590-E GAMING WIFI', 'The Asus ROG STRIX Z590-E GAMING WIFI motherboard is built for Intel’s 10th and 11th Gen processors, offering superior gaming and productivity performance. It features Wi-Fi 6, PCIe 4.0, and comprehensive cooling, providing great features for both casual and competitive gamers.'),
    (22, 'en-GB', 'ASUS ROG Maximus Z790 Hero', 'The ASUS ROG Maximus Z790 Hero is a high-end motherboard designed for Intel’s 12th and 13th Gen processors. With advanced power delivery, PCIe 5.0, and Wi-Fi 6E support, it’s perfect for extreme overclocking and high-performance gaming, offering premium features and solid durability for enthusiasts.'),
    
    -- RAM for Corsair brand
    (23, 'en-GB', 'Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16', 'Corsair Dominator DDR4 3200MHz 16GB (2x8GB) is a high-performance memory kit with exceptional overclocking potential. Its low-latency CL16 timings ensure excellent system responsiveness, making it ideal for gamers and creators who demand stable and fast memory performance.'),
    (24, 'en-GB', 'Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16', 'The Corsair Vengeance RGB Pro DDR4 3200MHz 32GB (2x16GB) memory kit offers high-speed performance and stunning RGB lighting effects. With CL16 timings and superior stability, it’s designed for enthusiasts who need both performance and aesthetics in their build.'),
    (25, 'en-GB', 'Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Negra', 'The Corsair Vengeance DDR5 6000MHz 32GB (2x16GB) memory kit offers cutting-edge performance for the latest Intel and AMD platforms. With high-speed data transfer rates and tight timings, it’s perfect for gamers and content creators who need high-bandwidth memory for demanding applications.'),
    (26, 'en-GB', 'Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32', 'Corsair Vengeance DDR5 6600MHz 64GB (2x32GB) is a high-performance memory kit designed for extreme gaming and content creation setups. With a large capacity and fast data transfer speeds, it ensures smooth multitasking, video editing, and gaming performance at the highest level.'),
    
-- SSDs for Samsung brand
    (27, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'The Samsung 990 Pro SSD offers lightning-fast read and write speeds thanks to PCIe 4.0 and NVMe M.2 technology. With a 500GB capacity, it delivers excellent performance for gaming, content creation, and everyday tasks. Built for durability and efficiency, it’s a top choice for users who demand the best storage performance.'),
    (28, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'The Samsung 990 Pro SSD with 1TB capacity provides high-speed PCIe 4.0 NVMe M.2 performance for intensive workloads, including gaming, video editing, and data-heavy applications. Offering a balanced mix of speed, capacity, and reliability, it’s perfect for both professionals and enthusiasts.'),
    (29, 'en-GB', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'The Samsung 990 Pro 2TB SSD is designed to handle large files and demanding applications with PCIe 4.0 NVMe M.2 speeds. With 2TB of storage, it offers ample space for high-performance gaming, video editing, and heavy multitasking, making it an excellent choice for advanced users.'),
    (30, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'The Samsung 980 Pro SSD 500GB provides ultra-fast performance with PCIe 4.0 and NVMe M.2 technology. With rapid read and write speeds, this drive is ideal for gamers and content creators who require fast storage for their applications and data.'),
    (31, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'With 1TB of storage capacity, the Samsung 980 Pro SSD offers exceptional PCIe 4.0 performance for demanding tasks like gaming, 4K video editing, and file-intensive applications. Its high-speed transfer rates ensure maximum efficiency and a smoother user experience.'),
    (32, 'en-GB', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'The Samsung 980 Pro 2TB SSD delivers outstanding PCIe 4.0 NVMe M.2 performance for users who require large storage capacity and high-speed data transfer. Perfect for gaming, creative workflows, and power users who need a fast, reliable, and spacious drive.'),
    (33, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 500GB', 'The Samsung 970 EVO Plus SSD 500GB provides high-performance storage with NVMe M.2 technology. Offering fast read and write speeds, it’s ideal for users who want fast load times and smooth performance for gaming, video editing, and general computing tasks.'),
    (34, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 1TB', 'The Samsung 970 EVO Plus 1TB SSD brings exceptional speed and capacity with NVMe M.2 technology, providing quick load times and improved multitasking capabilities. Ideal for gamers and creators who need fast, reliable storage for large applications and files.'),
    (35, 'en-GB', 'Samsung 970 EVO Plus SSD NVMe M.2 2TB', 'The Samsung 970 EVO Plus 2TB SSD delivers impressive NVMe M.2 performance with 2TB of storage, making it an excellent choice for gamers, professionals, and content creators who need fast read/write speeds and ample storage capacity.'),
    
    -- SSDs for Western Digital brand
    (36, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 500GB', 'The WD Red SA500 NAS SSD with 500GB capacity is designed for NAS (Network-Attached Storage) systems, offering fast read and write speeds. Its SATA M.2 2280 form factor provides reliable performance, ideal for home and small office NAS applications that need durability and efficiency.'),
    (37, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 1TB', 'The WD Red SA500 NAS SSD 1TB is a high-performance storage solution for NAS systems. Offering faster data transfers, improved reliability, and long-lasting durability, it’s perfect for data-heavy workloads and intensive applications in small business environments.'),
    (38, 'en-GB', 'WD Red SA500 NAS SSD SATA M.2 2280 2TB', 'The WD Red SA500 NAS SSD with 2TB capacity provides optimal performance and reliability for NAS applications. Its SATA M.2 2280 form factor ensures high-speed data transfer and durability, making it ideal for home and small office NAS environments.'),
    (39, 'en-GB', 'Western Digital Blue 2.5" SSD 1TB SATA 3', 'The Western Digital Blue 1TB SSD offers reliable performance with SATA III technology, perfect for upgrading your PC with faster read/write speeds. It’s ideal for users who need a dependable, high-performance SSD for everyday computing and gaming applications.'),
    
    -- HDDs for Western Digital brand
    (40, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 2TB', 'The Western Digital Red Plus 2TB HDD is engineered for NAS systems, offering reliable and efficient storage with SATA3 interface. It’s perfect for small businesses or home servers, providing scalable and consistent performance for heavy data access and storage.'),
    (41, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 3TB', 'The Western Digital Red Plus 3TB HDD provides high-capacity storage for NAS systems. With SATA3 technology and optimized for RAID configurations, it ensures reliable performance for home and small office environments where data integrity and speed are crucial.'),
    (42, 'en-GB', 'Western Digital Red Plus 3.5" SATA3 4TB', 'The Western Digital Red Plus 4TB HDD offers scalable, high-capacity storage designed for NAS systems. With a SATA3 interface, it delivers stable and reliable performance for businesses and home users who need large-scale storage solutions and quick access to files.'),
    
-- NVIDIA graphics cards
    (43, 'en-GB', 'EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X', 'The EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X is built for high-end gaming and content creation. Featuring a robust 10GB GDDR6X memory and powered by NVIDIA’s Ampere architecture, this card delivers exceptional performance, smooth frame rates, and real-time ray tracing for a truly immersive gaming experience.'),
    (44, 'en-GB', 'EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X', 'The EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X offers powerful graphics performance with its 8GB of GDDR6X memory. Based on the Turing architecture, this graphics card supports real-time ray tracing and AI-powered gaming for a premium experience in 4K gaming and VR.'),
    (45, 'en-GB', 'ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3', 'The ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3 is a top-tier GPU designed for gamers and creators. With 16GB of GDDR6X memory and support for DLSS 3 and ray tracing, it delivers unparalleled performance and visual fidelity in next-gen gaming, rendering, and content creation.'),
    
    -- AMD graphics cards
    (46, 'en-GB', 'Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6', 'The Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6 offers powerful performance for modern games and applications. With 12GB of GDDR6 memory and advanced RDNA 3 architecture, it provides smooth gameplay and enhanced efficiency, delivering excellent 1440p and 4K gaming experiences.'),
    (47, 'en-GB', 'Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6', 'The Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6 is optimized for high-resolution gaming, offering exceptional 1440p performance. Equipped with 12GB of GDDR6 memory and RDNA 2 architecture, it delivers high frame rates and stunning visuals for an immersive gaming experience.'),
    (48, 'en-GB', 'ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6', 'The ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6 is a powerhouse for 4K gaming and content creation. Featuring 24GB of GDDR6 memory and RDNA 3 technology, it excels in rendering, real-time ray tracing, and delivering ultra-smooth gameplay at high settings.'),
    
    -- Cases
    (49, 'en-GB', 'Fractal Design Node 202 Mini ITX Desktop Case', 'The Fractal Design Node 202 Mini ITX Desktop Case offers a sleek and compact design, perfect for those who need a powerful PC in a small form factor. Featuring excellent airflow and ample space for high-performance components, it’s a great choice for compact builds without compromising on performance.'),
    (50, 'en-GB', 'NZXT H510 Compact ATX Mid-Tower Case', 'The NZXT H510 Compact ATX Mid-Tower Case combines a minimalist design with modern features. With a tempered glass side panel, cable management system, and efficient airflow, it provides both aesthetics and performance for mid-range builds.'),
    (51, 'en-GB', 'Cooler Master MasterBox Q300L mATX Tower Case', 'The Cooler Master MasterBox Q300L mATX Tower Case offers versatile cooling options and a modular design. With support for a wide range of components and superior airflow, it’s perfect for building a high-performance, compact system with ease.'),
    (52, 'en-GB', 'Fractal Design Meshify C ATX Mid Tower Case', 'The Fractal Design Meshify C ATX Mid Tower Case is designed for high-performance builds. Featuring a mesh front panel for excellent airflow, it offers a spacious interior and a sleek design for building a powerful system with optimized cooling and cable management.'),
    
    -- Power Supplies
    (53, 'en-GB', 'EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Fully Modular', 'The EVGA SuperNOVA 550 G5 is a fully modular power supply offering 80 Plus Gold efficiency. With a 550W capacity, it provides reliable power delivery for your system while ensuring quiet operation and optimal efficiency. Ideal for mid-range builds.'),
    (54, 'en-GB', 'Corsair RM850x, 850W, 80 Plus Gold, Fully Modular', 'The Corsair RM850x 850W power supply delivers 80 Plus Gold efficiency, ensuring energy savings and stable performance. With fully modular cables and quiet operation, it is an ideal choice for high-performance gaming rigs and workstations that demand reliability and efficiency.'),
    (55, 'en-GB', 'Corsair HX1200i, 1200W, 80 Plus Platinum, Fully Modular', 'The Corsair HX1200i 1200W power supply offers 80 Plus Platinum efficiency, providing excellent energy savings and optimal power delivery. Fully modular cables and high-quality components ensure a clean build with reliable power for high-end systems and overclocking setups.');
    
INSERT INTO `PRODUCT_LOCALE` (`PRODUCT_ID`, `LOCALE_ID`, `NAME`, `DESCRIPTION`)
VALUES
    -- AMD processors
    (1, 'fr-FR', 'AMD Ryzen 9 7950X3D 4,2 GHz/5,7 GHz', 'Le processeur AMD Ryzen 9 7950X3D offre des performances exceptionnelles avec une fréquence de base de 4,2 GHz et une fréquence boostée atteignant 5,7 GHz. Idéal pour les joueurs et les créateurs de contenu exigeants, ce processeur 16 cœurs et 32 threads est équipé de la technologie 3D V-Cache pour une mémoire cache améliorée et une efficacité énergétique remarquable.'),
    (1, 'es-ES', 'AMD Ryzen 9 7950X3D 4,2 GHz/5,7 GHz', 'El procesador AMD Ryzen 9 7950X3D ofrece un rendimiento excepcional con una frecuencia base de 4,2 GHz y un boost que alcanza los 5,7 GHz. Perfecto para jugadores y creadores de contenido exigentes, este procesador de 16 núcleos y 32 hilos incluye tecnología 3D V-Cache para mayor memoria caché y eficiencia energética destacada.'),

    (2, 'fr-FR', 'AMD Ryzen 9 5950X 3,4 GHz', 'Le processeur AMD Ryzen 9 5950X est une solution haut de gamme offrant une fréquence de base de 3,4 GHz et des performances impressionnantes pour les charges de travail intenses. Avec 16 cœurs et 32 threads, il est conçu pour le multitâche, les jeux et la création de contenu professionnel.'),
    (2, 'es-ES', 'AMD Ryzen 9 5950X 3,4 GHz', 'El procesador AMD Ryzen 9 5950X es una solución de gama alta que ofrece una frecuencia base de 3,4 GHz y un rendimiento impresionante para tareas intensas. Con 16 núcleos y 32 hilos, está diseñado para multitarea, juegos y creación de contenido profesional.'),

    (3, 'fr-FR', 'AMD Ryzen 7 7800X3D 4,2 GHz/5 GHz', 'L’AMD Ryzen 7 7800X3D est un processeur performant doté d’une fréquence de base de 4,2 GHz et pouvant atteindre 5 GHz en mode boost. Avec 8 cœurs et 16 threads, il est parfait pour les jeux compétitifs et les charges de travail multitâches, grâce à sa technologie avancée 3D V-Cache.'),
    (3, 'es-ES', 'AMD Ryzen 7 7800X3D 4,2 GHz/5 GHz', 'El AMD Ryzen 7 7800X3D es un procesador de alto rendimiento con una frecuencia base de 4,2 GHz que puede alcanzar hasta 5 GHz en modo boost. Con 8 núcleos y 16 hilos, es ideal para juegos competitivos y tareas multitarea exigentes, gracias a su avanzada tecnología 3D V-Cache.'),

    (4, 'fr-FR', 'AMD Ryzen 7 5800X3D 3,4 GHz', 'L’AMD Ryzen 7 5800X3D combine 8 cœurs et 16 threads pour offrir des performances solides à une fréquence de base de 3,4 GHz. Sa mémoire cache augmentée grâce à la 3D V-Cache en fait un excellent choix pour les jeux et les applications multitâches.'),
    (4, 'es-ES', 'AMD Ryzen 7 5800X3D 3,4 GHz', 'El AMD Ryzen 7 5800X3D combina 8 núcleos y 16 hilos para ofrecer un rendimiento sólido con una frecuencia base de 3,4 GHz. Su caché mejorado mediante tecnología 3D V-Cache lo convierte en una excelente opción para juegos y aplicaciones multitarea.'),

    (5, 'fr-FR', 'AMD Ryzen 5 7600X 4,7 GHz', 'Le processeur AMD Ryzen 5 7600X est conçu pour offrir des performances impressionnantes avec une fréquence de base de 4,7 GHz. Avec 6 cœurs et 12 threads, il est idéal pour les jeux et les tâches quotidiennes exigeantes, offrant une excellente réactivité et une efficacité énergétique accrue.'),
    (5, 'es-ES', 'AMD Ryzen 5 7600X 4,7 GHz', 'El procesador AMD Ryzen 5 7600X está diseñado para ofrecer un rendimiento impresionante con una frecuencia base de 4,7 GHz. Con 6 núcleos y 12 hilos, es ideal para juegos y tareas diarias exigentes, ofreciendo una excelente capacidad de respuesta y eficiencia energética mejorada.'),

    (6, 'fr-FR', 'AMD Ryzen 5 5600X 3,7 GHz', 'L’AMD Ryzen 5 5600X est un processeur polyvalent avec une fréquence de base de 3,7 GHz, conçu pour les utilisateurs recherchant un équilibre entre performance et budget. Avec 6 cœurs et 12 threads, il offre d’excellentes performances pour les jeux et les tâches quotidiennes.'),
    (6, 'es-ES', 'AMD Ryzen 5 5600X 3,7 GHz', 'El AMD Ryzen 5 5600X es un procesador versátil con una frecuencia base de 3,7 GHz, diseñado para usuarios que buscan un equilibrio entre rendimiento y presupuesto. Con 6 núcleos y 12 hilos, ofrece un excelente rendimiento para juegos y tareas diarias.'),
    
    -- Intel processors
    (7, 'fr-FR', 'Intel Core i7-14700K 3,4/5,6 GHz', 'Le processeur Intel Core i7-14700K offre des performances de pointe avec une fréquence de base de 3,4 GHz et une fréquence turbo pouvant atteindre 5,6 GHz. Avec ses 20 cœurs (8 Performance et 12 Efficience) et 28 threads, il est conçu pour les jeux, la création de contenu, et le multitâche exigeant.'),
    (7, 'es-ES', 'Intel Core i7-14700K 3,4/5,6 GHz', 'El procesador Intel Core i7-14700K ofrece un rendimiento de primer nivel con una frecuencia base de 3,4 GHz y un turbo que alcanza los 5,6 GHz. Con sus 20 núcleos (8 de rendimiento y 12 de eficiencia) y 28 hilos, está diseñado para juegos, creación de contenido y multitarea exigente.'),

    (8, 'fr-FR', 'Intel Core i7-13700K 3,4 GHz', 'L’Intel Core i7-13700K combine 16 cœurs (8 Performance et 8 Efficience) et 24 threads pour une performance impressionnante. Avec une fréquence de base de 3,4 GHz et des technologies avancées comme Intel Turbo Boost, il est idéal pour les applications intensives comme le gaming et l’édition vidéo.'),
    (8, 'es-ES', 'Intel Core i7-13700K 3,4 GHz', 'El Intel Core i7-13700K combina 16 núcleos (8 de rendimiento y 8 de eficiencia) y 24 hilos para un rendimiento impresionante. Con una frecuencia base de 3,4 GHz y tecnologías avanzadas como Intel Turbo Boost, es ideal para aplicaciones intensivas como juegos y edición de video.'),

    (9, 'fr-FR', 'Intel Core i7-12700K 3,6 GHz', 'L’Intel Core i7-12700K est un processeur puissant avec une fréquence de base de 3,6 GHz et une architecture hybride qui combine 12 cœurs (8 Performance et 4 Efficience) et 20 threads. Il offre des performances exceptionnelles pour les tâches multitâches et les jeux haut de gamme.'),
    (9, 'es-ES', 'Intel Core i7-12700K 3,6 GHz', 'El Intel Core i7-12700K es un procesador potente con una frecuencia base de 3,6 GHz y una arquitectura híbrida que combina 12 núcleos (8 de rendimiento y 4 de eficiencia) y 20 hilos. Ofrece un rendimiento excepcional para tareas multitarea y juegos de alta gama.'),

    (10, 'fr-FR', 'Intel Core i9-14900K 3,2/6 GHz', 'Le processeur Intel Core i9-14900K repousse les limites des performances avec une fréquence de base de 3,2 GHz et un turbo maximal atteignant 6 GHz. Doté de 24 cœurs (8 Performance et 16 Efficience) et 32 threads, il est idéal pour les utilisateurs exigeants, les jeux ultra-fluides, et la création de contenu complexe.'),
    (10, 'es-ES', 'Intel Core i9-14900K 3,2/6 GHz', 'El procesador Intel Core i9-14900K lleva el rendimiento al máximo con una frecuencia base de 3,2 GHz y un turbo que alcanza los 6 GHz. Con 24 núcleos (8 de rendimiento y 16 de eficiencia) y 32 hilos, es ideal para usuarios exigentes, juegos ultra fluidos y creación de contenido complejo.'),

    (11, 'fr-FR', 'Intel Core i9-13900K 3 GHz', 'L’Intel Core i9-13900K est un processeur haut de gamme conçu pour les performances extrêmes. Avec 24 cœurs (8 Performance et 16 Efficience), 32 threads, et une fréquence de base de 3 GHz, il gère sans effort les jeux AAA, le rendu 3D et le multitâche.'),
    (11, 'es-ES', 'Intel Core i9-13900K 3 GHz', 'El Intel Core i9-13900K es un procesador de gama alta diseñado para un rendimiento extremo. Con 24 núcleos (8 de rendimiento y 16 de eficiencia), 32 hilos, y una frecuencia base de 3 GHz, maneja sin esfuerzo juegos AAA, renderizado 3D y multitarea.'),

    (12, 'fr-FR', 'Intel Core i9-12900K 3,2 GHz', 'L’Intel Core i9-12900K offre une performance exceptionnelle grâce à son architecture hybride innovante, combinant 16 cœurs (8 Performance et 8 Efficience) et 24 threads. Avec une fréquence de base de 3,2 GHz, il est idéal pour les joueurs et les professionnels recherchant des performances maximales.'),
    (12, 'es-ES', 'Intel Core i9-12900K 3,2 GHz', 'El Intel Core i9-12900K ofrece un rendimiento excepcional gracias a su innovadora arquitectura híbrida, que combina 16 núcleos (8 de rendimiento y 8 de eficiencia) y 24 hilos. Con una frecuencia base de 3,2 GHz, es ideal para jugadores y profesionales que buscan el máximo rendimiento.'),
    
    -- Motherboards for Gigabyte brand
    (13, 'fr-FR', 'Gigabyte B650 Aorus Elite AX', 'La carte mère Gigabyte B650 Aorus Elite AX est une solution performante compatible avec les processeurs AMD Ryzen de dernière génération. Elle offre des connectivités avancées telles que PCIe 5.0, un support DDR5, et des ports USB-C rapides, le tout dans un design robuste et optimisé pour les joueurs et les créateurs.'),
    (13, 'es-ES', 'Gigabyte B650 Aorus Elite AX', 'La placa base Gigabyte B650 Aorus Elite AX es una solución potente compatible con los procesadores AMD Ryzen de última generación. Ofrece conectividad avanzada como PCIe 5.0, soporte DDR5 y puertos USB-C rápidos, todo en un diseño robusto y optimizado para gamers y creadores.'),

    (14, 'fr-FR', 'Gigabyte B550 Aorus Elite AX', 'La carte mère Gigabyte B550 Aorus Elite AX est une excellente option pour les processeurs AMD Ryzen de série 3000 et 5000. Avec un support PCIe 4.0, le Wi-Fi 6 intégré, et une conception thermique avancée, elle est idéale pour les systèmes performants et fiables.'),
    (14, 'es-ES', 'Gigabyte B550 Aorus Elite AX', 'La placa base Gigabyte B550 Aorus Elite AX es una excelente opción para procesadores AMD Ryzen de las series 3000 y 5000. Con soporte PCIe 4.0, Wi-Fi 6 integrado y un diseño térmico avanzado, es ideal para sistemas de alto rendimiento y confiabilidad.'),

    (15, 'fr-FR', 'Gigabyte Z790 Aorus Elite AX', 'La carte mère Gigabyte Z790 Aorus Elite AX est conçue pour les processeurs Intel Core de 12e et 13e génération. Elle offre des performances de pointe grâce à PCIe 5.0, au support DDR5 et au Wi-Fi 6E intégré, combinés à une solution de refroidissement robuste pour un fonctionnement stable.'),
    (15, 'es-ES', 'Gigabyte Z790 Aorus Elite AX', 'La placa base Gigabyte Z790 Aorus Elite AX está diseñada para procesadores Intel Core de 12ª y 13ª generación. Ofrece un rendimiento de primer nivel con PCIe 5.0, soporte DDR5 y Wi-Fi 6E integrado, combinado con una solución de refrigeración robusta para un funcionamiento estable.'),

    (16, 'fr-FR', 'Gigabyte Z690 Gaming X DDR4', 'La carte mère Gigabyte Z690 Gaming X DDR4 est idéale pour les joueurs recherchant des performances équilibrées avec un support pour les processeurs Intel Core de 12e génération. Elle prend en charge la mémoire DDR4, offre des connectivités modernes comme PCIe 4.0 et USB-C, et intègre un design axé sur la durabilité.'),
    (16, 'es-ES', 'Gigabyte Z690 Gaming X DDR4', 'La placa base Gigabyte Z690 Gaming X DDR4 es ideal para gamers que buscan un rendimiento equilibrado con soporte para procesadores Intel Core de 12ª generación. Es compatible con memoria DDR4, ofrece conectividad moderna como PCIe 4.0 y USB-C, y cuenta con un diseño enfocado en la durabilidad.'),

    (17, 'fr-FR', 'Gigabyte Z590 Aorus Master', 'La carte mère Gigabyte Z590 Aorus Master est une solution haut de gamme pour les utilisateurs d’Intel Core de 10e et 11e génération. Avec un support PCIe 4.0, une connectivité rapide comme le Wi-Fi 6 et Thunderbolt 4, et un refroidissement avancé, elle est parfaite pour les systèmes hautes performances.'),
    (17, 'es-ES', 'Gigabyte Z590 Aorus Master', 'La placa base Gigabyte Z590 Aorus Master es una solución de gama alta para usuarios de Intel Core de 10ª y 11ª generación. Con soporte PCIe 4.0, conectividad rápida como Wi-Fi 6 y Thunderbolt 4, y refrigeración avanzada, es perfecta para sistemas de alto rendimiento.'),

    -- Motherboards for ASUS brand
    (18, 'fr-FR', 'ASUS ROG STRIX X670E-F GAMING WIFI', 'La carte mère ASUS ROG STRIX X670E-F GAMING WIFI est une solution haut de gamme pour les joueurs et créateurs exigeants. Compatible avec les processeurs AMD Ryzen de 5e génération, elle offre un support PCIe 5.0, la mémoire DDR5, et une connectivité Wi-Fi 6E pour des performances ultra-rapides et une expérience de jeu immersive. Son design robuste et son refroidissement performant assurent une stabilité optimale même lors de sessions intensives.'),
    (18, 'es-ES', 'ASUS ROG STRIX X670E-F GAMING WIFI', 'La placa base ASUS ROG STRIX X670E-F GAMING WIFI es una solución de gama alta para jugadores y creadores exigentes. Compatible con procesadores AMD Ryzen de 5ª generación, ofrece soporte PCIe 5.0, memoria DDR5 y conectividad Wi-Fi 6E para un rendimiento ultrarrápido y una experiencia de juego inmersiva. Su diseño robusto y refrigeración eficiente garantizan estabilidad óptima incluso durante sesiones intensivas.'),

    (19, 'fr-FR', 'ASUS ROG Strix B550-F GAMING', 'La carte mère ASUS ROG Strix B550-F GAMING est idéale pour les joueurs à la recherche d’un excellent rapport qualité/prix. Elle prend en charge les processeurs AMD Ryzen de 3e et 5e génération, avec un support PCIe 4.0, Wi-Fi 6 et une solution de refroidissement avancée. Son design élégant et ses performances de haut niveau en font un choix parfait pour les configurations de jeu haut de gamme.'),
    (19, 'es-ES', 'ASUS ROG Strix B550-F GAMING', 'La placa base ASUS ROG Strix B550-F GAMING es ideal para jugadores que buscan una excelente relación calidad/precio. Es compatible con procesadores AMD Ryzen de 3ª y 5ª generación, con soporte PCIe 4.0, Wi-Fi 6 y una solución de refrigeración avanzada. Su diseño elegante y su alto rendimiento la convierten en una opción perfecta para configuraciones de juego de gama alta.'),

    (20, 'fr-FR', 'Asus ROG STRIX Z690-E GAMING WIFI', 'L’ASUS ROG STRIX Z690-E GAMING WIFI est une carte mère haut de gamme pour les processeurs Intel Core de 12e génération. Elle offre des vitesses de transfert ultra-rapides grâce à PCIe 5.0, prend en charge la mémoire DDR5, et dispose de la connectivité Wi-Fi 6E. Avec son design optimisé pour les jeux et la création de contenu, elle offre également des options de refroidissement avancées pour un fonctionnement stable même lors de l’utilisation des applications les plus gourmandes.'),
    (20, 'es-ES', 'Asus ROG STRIX Z690-E GAMING WIFI', 'La ASUS ROG STRIX Z690-E GAMING WIFI es una placa base de gama alta para procesadores Intel Core de 12ª generación. Ofrece velocidades de transferencia ultrarrápidas gracias a PCIe 5.0, soporte para memoria DDR5 y conectividad Wi-Fi 6E. Con su diseño optimizado para juegos y creación de contenido, también incluye opciones de refrigeración avanzada para un rendimiento estable incluso con las aplicaciones más exigentes.'),

    (21, 'fr-FR', 'Asus ROG STRIX Z590-E GAMING WIFI', 'La carte mère ASUS ROG STRIX Z590-E GAMING WIFI est conçue pour offrir des performances exceptionnelles avec les processeurs Intel Core de 10e et 11e génération. Elle prend en charge PCIe 4.0, Wi-Fi 6 et un refroidissement avancé pour des performances optimisées lors des sessions de jeu ou de création de contenu. Le tout dans un design élégant et durable.'),
    (21, 'es-ES', 'Asus ROG STRIX Z590-E GAMING WIFI', 'La placa base ASUS ROG STRIX Z590-E GAMING WIFI está diseñada para ofrecer un rendimiento excepcional con procesadores Intel Core de 10ª y 11ª generación. Es compatible con PCIe 4.0, Wi-Fi 6 y cuenta con refrigeración avanzada para un rendimiento optimizado en sesiones de juego o creación de contenido. Todo ello en un diseño elegante y duradero.'),

    (22, 'fr-FR', 'ASUS ROG Maximus Z790 Hero', 'L’ASUS ROG Maximus Z790 Hero est la carte mère ultime pour les joueurs et professionnels exigeants. Elle est compatible avec les processeurs Intel Core de 13e génération et propose des fonctionnalités haut de gamme telles que PCIe 5.0, DDR5, Wi-Fi 6E, et un refroidissement de qualité supérieure. Son design épuré et ses fonctionnalités avancées font d’elle un choix parfait pour des configurations hautes performances.'),
    (22, 'es-ES', 'ASUS ROG Maximus Z790 Hero', 'La ASUS ROG Maximus Z790 Hero es la placa base definitiva para jugadores y profesionales exigentes. Es compatible con procesadores Intel Core de 13ª generación y ofrece características de gama alta como PCIe 5.0, DDR5, Wi-Fi 6E y refrigeración superior. Su diseño elegante y sus funciones avanzadas la convierten en una opción perfecta para configuraciones de alto rendimiento.'),

    -- RAM for Corsair brand
    (23, 'fr-FR', 'Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16', 'La mémoire Corsair Dominator DDR4 3200MHz 16GB (2x8GB) CL16 est conçue pour offrir des performances exceptionnelles avec une latence faible et une stabilité maximale. Parfaite pour les configurations hautes performances, elle est équipée de dissipateurs thermiques élégants en aluminium pour un refroidissement optimal. Idéale pour les joueurs, les créateurs de contenu et les utilisateurs exigeants.'),
    (23, 'es-ES', 'Corsair Dominator DDR4 3200MHz 16GB 2x8GB CL16', 'La memoria Corsair Dominator DDR4 3200MHz 16GB (2x8GB) CL16 está diseñada para ofrecer un rendimiento excepcional con una latencia baja y máxima estabilidad. Perfecta para configuraciones de alto rendimiento, cuenta con disipadores térmicos elegantes de aluminio para una refrigeración óptima. Ideal para jugadores, creadores de contenido y usuarios exigentes.'),

    (24, 'fr-FR', 'Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16', 'La mémoire Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB (2x16GB) CL16 combine des performances de pointe avec un éclairage RGB personnalisable. Offrant une bande passante de 3200 MHz et une latence de CL16, elle est idéale pour les configurations gaming et de création de contenu. Son design élégant avec des modules lumineux permet une personnalisation complète de votre PC.'),
    (24, 'es-ES', 'Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB 2x16GB CL16', 'La memoria Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 32GB (2x16GB) CL16 combina un rendimiento de alto nivel con iluminación RGB personalizable. Con una velocidad de 3200 MHz y latencia CL16, es ideal para configuraciones de gaming y creación de contenido. Su diseño elegante con módulos iluminados permite una personalización total de tu PC.'),

    (25, 'fr-FR', 'Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Noire', 'La mémoire Corsair Vengeance DDR5 6000MHz 32GB (2x16GB) CL36 Noire est conçue pour les utilisateurs les plus exigeants qui recherchent des performances exceptionnelles et un futur-proofing pour les plateformes modernes. Avec une vitesse impressionnante de 6000 MHz et une latence de CL36, elle est idéale pour les applications lourdes, le multitâche et le gaming de haute performance. Son design sobre et élégant s’intègre parfaitement dans toute configuration haut de gamme.'),
    (25, 'es-ES', 'Corsair Vengeance DDR5 6000MHz 32GB 2x16GB CL36 Negra', 'La memoria Corsair Vengeance DDR5 6000MHz 32GB (2x16GB) CL36 Negra está diseñada para los usuarios más exigentes que buscan un rendimiento excepcional y compatibilidad con las plataformas modernas. Con una impresionante velocidad de 6000 MHz y latencia CL36, es ideal para aplicaciones intensivas, multitarea y gaming de alto rendimiento. Su diseño sobrio y elegante encaja perfectamente en cualquier configuración de alto nivel.'),

    (26, 'fr-FR', 'Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32', 'La mémoire Corsair Vengeance DDR5 6600MHz 64GB (2x32GB) CL32 est une solution puissante pour les utilisateurs professionnels et les créateurs de contenu qui nécessitent des performances maximales. Avec une bande passante de 6600 MHz et une latence de CL32, elle est idéale pour les stations de travail, les rendus vidéo et les jeux de dernière génération. Ce kit de RAM est conçu pour offrir une stabilité extrême et une gestion thermique efficace, tout en offrant un excellent rapport qualité/prix pour les configurations haut de gamme.'),
    (26, 'es-ES', 'Corsair Vengeance DDR5 6600MHz 64GB 2x32GB CL32', 'La memoria Corsair Vengeance DDR5 6600MHz 64GB (2x32GB) CL32 es una solución potente para usuarios profesionales y creadores de contenido que necesitan un rendimiento máximo. Con una velocidad de 6600 MHz y latencia CL32, es ideal para estaciones de trabajo, renders de video y juegos de última generación. Este kit de RAM está diseñado para ofrecer extrema estabilidad y una gestión térmica eficaz, todo mientras ofrece una excelente relación calidad-precio para configuraciones de alto nivel.'),
    
 -- SSDs for Samsung brand
    (27, 'fr-FR', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500 Go', 'Le Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500 Go offre des vitesses de lecture et d\'écriture exceptionnelles, jusqu\'à 7 450 Mo/s et 6 900 Mo/s respectivement, pour des performances inégalées dans le stockage de données. Conçu pour les joueurs et créateurs de contenu, il maximise la vitesse et l\'efficacité de vos systèmes tout en offrant une fiabilité à long terme. Sa conception compacte M.2 2280 est idéale pour les PC de bureau et les ordinateurs portables modernes.'),
    (27, 'es-ES', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'El Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 500 GB ofrece velocidades de lectura y escritura excepcionales, hasta 7,450 MB/s y 6,900 MB/s respectivamente, brindando un rendimiento sin igual en el almacenamiento de datos. Diseñado para jugadores y creadores de contenido, maximiza la velocidad y eficiencia de sus sistemas mientras asegura fiabilidad a largo plazo. Su diseño compacto M.2 2280 es perfecto para PCs de sobremesa y portátiles modernos.'),

    (28, 'fr-FR', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1 To', 'Le Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1 To combine une vitesse fulgurante avec une capacité idéale pour les utilisateurs exigeants. Avec des vitesses de lecture allant jusqu\'à 7 450 Mo/s et d\'écriture atteignant 6 900 Mo/s, ce disque offre une réactivité instantanée pour le gaming, l\'édition vidéo et les applications professionnelles. Son interface PCIe 4.0 garantit un transfert de données ultra-rapide et une fiabilité exceptionnelle pour un stockage à la hauteur des besoins modernes.'),
    (28, 'es-ES', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'El Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 1 TB combina una velocidad vertiginosa con una capacidad ideal para usuarios exigentes. Con velocidades de lectura de hasta 7,450 MB/s y escritura de hasta 6,900 MB/s, este disco ofrece una capacidad de respuesta instantánea para gaming, edición de video y aplicaciones profesionales. Su interfaz PCIe 4.0 garantiza transferencias ultrarrápidas y una fiabilidad excepcional para un almacenamiento a la altura de las necesidades actuales.'),

    (29, 'fr-FR', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2 To', 'Le Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2 To est la solution ultime pour les utilisateurs à la recherche de performances inégalées et d\'une capacité étendue. Avec une vitesse de lecture allant jusqu\'à 7 450 Mo/s et une écriture à 6 900 Mo/s, il est conçu pour les tâches les plus exigeantes, telles que les jeux AAA, la création de contenu en 4K et les simulations lourdes. Sa capacité de 2 To vous permet de stocker un large volume de données sans compromis sur la vitesse.'),
    (29, 'es-ES', 'Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'El Samsung 990 Pro SSD PCIe 4.0 NVMe M.2 2 TB es la solución definitiva para usuarios que buscan un rendimiento sin igual y una capacidad ampliada. Con velocidades de lectura de hasta 7,450 MB/s y escritura de hasta 6,900 MB/s, está diseñado para las tareas más exigentes, como juegos AAA, creación de contenido en 4K y simulaciones pesadas. Su capacidad de 2 TB le permite almacenar grandes volúmenes de datos sin comprometer la velocidad.'),

    (30, 'fr-FR', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500 Go', 'Le Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500 Go offre des performances exceptionnelles avec des vitesses de lecture jusqu\'à 7 000 Mo/s et des vitesses d\'écriture allant jusqu\'à 5 100 Mo/s. Il s\'agit d\'une solution idéale pour les utilisateurs souhaitant améliorer leur expérience de gaming ou de création de contenu grâce à des temps de chargement ultra-rapides et un accès instantané aux données. L\'interface PCIe 4.0 vous permet de tirer pleinement parti des performances des processeurs modernes.'),
    (30, 'es-ES', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500GB', 'El Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 500 GB ofrece un rendimiento excepcional con velocidades de lectura de hasta 7,000 MB/s y velocidades de escritura de hasta 5,100 MB/s. Es la solución ideal para usuarios que buscan mejorar su experiencia de gaming o creación de contenido, con tiempos de carga ultrarrápidos y acceso instantáneo a datos. Su interfaz PCIe 4.0 le permite aprovechar al máximo el rendimiento de los procesadores modernos.'),

    (31, 'fr-FR', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1 To', 'Le Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1 To est conçu pour répondre aux besoins des utilisateurs qui nécessitent des vitesses de transfert extrêmement rapides et une grande capacité de stockage. Avec des vitesses de lecture jusqu\'à 7 000 Mo/s et d\'écriture jusqu\'à 5 100 Mo/s, il offre une performance supérieure pour les applications exigeantes comme le montage vidéo 4K et le gaming haute résolution. Sa capacité de 1 To est idéale pour stocker de grandes quantités de fichiers tout en maintenant des performances exceptionnelles.'),
    (31, 'es-ES', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1TB', 'El Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 1 TB está diseñado para satisfacer las necesidades de los usuarios que requieren velocidades de transferencia ultrarrápidas y una gran capacidad de almacenamiento. Con velocidades de lectura de hasta 7,000 MB/s y escritura de hasta 5,100 MB/s, ofrece un rendimiento superior para aplicaciones exigentes como la edición de video 4K y gaming de alta resolución. Su capacidad de 1 TB es perfecta para almacenar grandes cantidades de archivos manteniendo un rendimiento excepcional.'),

    (32, 'fr-FR', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2 To', 'Le Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2 To offre une capacité de stockage impressionnante de 2 To avec des vitesses de transfert exceptionnelles. Il atteint des vitesses de lecture allant jusqu\'à 7 000 Mo/s et des vitesses d\'écriture jusqu\'à 5 100 Mo/s, ce qui en fait une option parfaite pour les utilisateurs qui ont besoin de rapidité et d\'espace. Que ce soit pour les jeux, la création de contenu ou les charges de travail professionnelles, il est conçu pour offrir des performances fiables et durables.'),
    (32, 'es-ES', 'Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2TB', 'El Samsung 980 Pro SSD PCIe 4.0 NVMe M.2 2 TB ofrece una impresionante capacidad de almacenamiento de 2 TB con velocidades de transferencia excepcionales. Alcanzando velocidades de lectura de hasta 7,000 MB/s y velocidades de escritura de hasta 5,100 MB/s, es una opción perfecta para usuarios que necesitan rapidez y espacio. Ya sea para gaming, creación de contenido o tareas profesionales, está diseñado para ofrecer un rendimiento fiable y duradero.'),

    (33, 'fr-FR', 'Samsung 970 EVO Plus SSD NVMe M.2 500 Go', 'Le Samsung 970 EVO Plus SSD NVMe M.2 500 Go est une solution haut de gamme pour les utilisateurs à la recherche de performances solides à un prix compétitif. Avec des vitesses de lecture atteignant jusqu\'à 3 500 Mo/s et des vitesses d\'écriture jusqu\'à 3 300 Mo/s, il est idéal pour les applications quotidiennes, le gaming et les tâches créatives. Il utilise la technologie NAND V-NAND 3D et offre une fiabilité et une endurance exceptionnelles pour un stockage rapide et durable.'),
    (33, 'es-ES', 'Samsung 970 EVO Plus SSD NVMe M.2 500GB', 'El Samsung 970 EVO Plus SSD NVMe M.2 500 GB es una solución de alto rendimiento para usuarios que buscan fiabilidad a un precio competitivo. Con velocidades de lectura de hasta 3,500 MB/s y escritura de hasta 3,300 MB/s, es ideal para aplicaciones diarias, gaming y tareas creativas. Utiliza la tecnología NAND V-NAND 3D y ofrece una fiabilidad y durabilidad excepcionales para un almacenamiento rápido y fiable.'),

    (34, 'fr-FR', 'Samsung 970 EVO Plus SSD NVMe M.2 1 To', 'Le Samsung 970 EVO Plus SSD NVMe M.2 1 To est une solution de stockage fiable et rapide, idéale pour les utilisateurs ayant besoin de performances constantes. Avec des vitesses de lecture allant jusqu\'à 3 500 Mo/s et d\'écriture atteignant 3 300 Mo/s, il garantit un accès rapide aux données et des performances fluides pour les applications exigeantes. Son endurance et sa fiabilité sont renforcées par la technologie NAND V-NAND 3D.'),
    (34, 'es-ES', 'Samsung 970 EVO Plus SSD NVMe M.2 1TB', 'El Samsung 970 EVO Plus SSD NVMe M.2 1 TB es una solución de almacenamiento fiable y rápido, ideal para usuarios que necesitan un rendimiento constante. Con velocidades de lectura de hasta 3,500 MB/s y escritura de hasta 3,300 MB/s, garantiza un acceso rápido a los datos y un rendimiento fluido en aplicaciones exigentes. Su durabilidad y fiabilidad están mejoradas por la tecnología NAND V-NAND 3D.'),

    (35, 'fr-FR', 'Samsung 970 EVO Plus SSD NVMe M.2 2 To', 'Le Samsung 970 EVO Plus SSD NVMe M.2 2 To offre une grande capacité de stockage avec des vitesses de lecture allant jusqu\'à 3 500 Mo/s et d\'écriture jusqu\'à 3 300 Mo/s. Il est parfait pour les utilisateurs qui ont besoin d\'une capacité de stockage importante sans sacrifier la vitesse. Sa technologie V-NAND 3D et son endurance élevée en font un choix idéal pour les utilisateurs professionnels, les créateurs de contenu et les gamers.'),
    (35, 'es-ES', 'Samsung 970 EVO Plus SSD NVMe M.2 2TB', 'El Samsung 970 EVO Plus SSD NVMe M.2 2 TB ofrece una gran capacidad de almacenamiento con velocidades de lectura de hasta 3,500 MB/s y escritura de hasta 3,300 MB/s. Es perfecto para usuarios que necesitan una gran capacidad de almacenamiento sin sacrificar la velocidad. Su tecnología V-NAND 3D y su alta durabilidad lo convierten en una opción ideal para usuarios profesionales, creadores de contenido y gamers.'),

    -- SSDs for Western Digital brand
    (36, 'fr-FR', 'WD Red SA500 NAS SSD SATA M.2 2280 500 Go', 'Le WD Red SA500 NAS SSD SATA M.2 2280 500 Go est spécialement conçu pour les systèmes NAS (Network Attached Storage). Il offre une performance fiable et constante avec des vitesses de lecture allant jusqu\'à 560 Mo/s, ce qui en fait une solution idéale pour les environnements de stockage multi-utilisateurs. Ce SSD est optimisé pour la durabilité et l\'efficacité énergétique, tout en offrant une gestion thermique avancée pour des performances à long terme dans des conditions de travail intensives.'),
    (36, 'es-ES', 'WD Red SA500 NAS SSD SATA M.2 2280 500GB', 'El WD Red SA500 NAS SSD SATA M.2 2280 500 GB está diseñado específicamente para sistemas NAS (almacenamiento conectado a la red). Ofrece un rendimiento fiable y constante con velocidades de lectura de hasta 560 MB/s, lo que lo convierte en una solución ideal para entornos de almacenamiento multiusuario. Este SSD está optimizado para la durabilidad y eficiencia energética, y cuenta con un sistema de gestión térmica avanzada para un rendimiento a largo plazo en condiciones de trabajo intensivas.'),

    (37, 'fr-FR', 'WD Red SA500 NAS SSD SATA M.2 2280 1 To', 'Le WD Red SA500 NAS SSD SATA M.2 2280 1 To est un disque SSD performant et fiable, conçu pour répondre aux besoins des environnements NAS. Avec une vitesse de lecture atteignant 560 Mo/s, ce SSD est parfait pour les applications nécessitant une lecture rapide des données dans un réseau partagé. Il est particulièrement adapté aux petites entreprises ou aux utilisateurs domestiques qui ont besoin d\'un stockage NAS fiable et rapide. Sa conception à faible consommation d\'énergie et sa gestion thermique avancée permettent une utilisation optimale sur le long terme.'),
    (37, 'es-ES', 'WD Red SA500 NAS SSD SATA M.2 2280 1TB', 'El WD Red SA500 NAS SSD SATA M.2 2280 1 TB es un disco SSD de alto rendimiento y fiabilidad, diseñado para satisfacer las necesidades de los entornos NAS. Con una velocidad de lectura de hasta 560 MB/s, este SSD es perfecto para aplicaciones que requieren una lectura rápida de datos en una red compartida. Es especialmente adecuado para pequeñas empresas o usuarios domésticos que necesitan almacenamiento NAS fiable y rápido. Su diseño de bajo consumo de energía y su gestión térmica avanzada permiten un rendimiento óptimo a largo plazo.'),

    (38, 'fr-FR', 'WD Red SA500 NAS SSD SATA M.2 2280 2 To', 'Le WD Red SA500 NAS SSD SATA M.2 2280 2 To offre un stockage plus vaste pour les utilisateurs de NAS. Il garantit des performances solides avec une vitesse de lecture allant jusqu\'à 560 Mo/s et une fiabilité accrue pour les charges de travail plus importantes. Ce SSD est optimisé pour la gestion des données dans un environnement de réseau partagé, tout en offrant une consommation énergétique optimisée et une gestion thermique avancée pour des performances stables à long terme.'),
    (38, 'es-ES', 'WD Red SA500 NAS SSD SATA M.2 2280 2TB', 'El WD Red SA500 NAS SSD SATA M.2 2280 2 TB ofrece almacenamiento más amplio para usuarios de NAS. Garantiza un rendimiento constante con una velocidad de lectura de hasta 560 MB/s y una fiabilidad mejorada para cargas de trabajo más grandes. Este SSD está optimizado para la gestión de datos en entornos de red compartida, además de ofrecer un consumo de energía optimizado y gestión térmica avanzada para un rendimiento estable a largo plazo.'),

    (39, 'fr-FR', 'Western Digital Blue 2,5" SSD 1 To SATA 3', 'Le Western Digital Blue 2,5" SSD 1 To SATA 3 est une solution de stockage fiable et rapide pour les utilisateurs à la recherche d\'un disque SSD abordable avec des performances solides. Avec des vitesses de lecture allant jusqu\'à 560 Mo/s et des vitesses d\'écriture atteignant 530 Mo/s, il est idéal pour améliorer les performances de votre ordinateur portable ou de bureau. Sa conception à faible consommation d\'énergie et sa résistance aux chocs garantissent une durabilité exceptionnelle pour un usage quotidien.'),
    (39, 'es-ES', 'Western Digital Blue 2,5" SSD 1TB SATA 3', 'El Western Digital Blue 2,5" SSD 1 TB SATA 3 es una solución de almacenamiento fiable y rápida para usuarios que buscan un disco SSD asequible con un rendimiento sólido. Con velocidades de lectura de hasta 560 MB/s y velocidades de escritura de hasta 530 MB/s, es ideal para mejorar el rendimiento de su portátil o PC de escritorio. Su diseño de bajo consumo energético y su resistencia a los golpes garantizan una durabilidad excepcional para el uso diario.'),

    -- HDDs for Western Digital brand
    (40, 'fr-FR', 'Western Digital Red Plus 3,5" SATA3 2 To', 'Le Western Digital Red Plus 3,5" SATA3 2 To est un disque dur HDD conçu pour les environnements NAS. Il offre des performances fiables et optimisées pour un usage à faible consommation énergétique dans des systèmes de stockage multi-utilisateurs. Avec une capacité de 2 To, il est idéal pour les petites entreprises ou pour un usage domestique, garantissant une grande durabilité et une gestion thermique efficace. Il est parfait pour les configurations NAS à faible ou moyenne charge de travail.'),
    (40, 'es-ES', 'Western Digital Red Plus 3,5" SATA3 2TB', 'El Western Digital Red Plus 3,5" SATA3 2 TB es un disco duro HDD diseñado para entornos NAS. Ofrece un rendimiento fiable y optimizado para su uso con bajo consumo energético en sistemas de almacenamiento multiusuario. Con una capacidad de 2 TB, es ideal para pequeñas empresas o uso doméstico, garantizando una gran durabilidad y una eficaz gestión térmica. Es perfecto para configuraciones NAS con cargas de trabajo bajas o medias.'),

    (41, 'fr-FR', 'Western Digital Red Plus 3,5" SATA3 3 To', 'Le Western Digital Red Plus 3,5" SATA3 3 To est un disque dur de haute capacité conçu pour les systèmes NAS. Avec 3 To de stockage, ce HDD offre une fiabilité et une performance optimisées pour les environnements avec plusieurs utilisateurs ou de données en réseau. Il est particulièrement adapté pour les utilisateurs recherchant une solution de stockage robuste avec une gestion thermique et une consommation énergétique optimisées.'),
    (41, 'es-ES', 'Western Digital Red Plus 3,5" SATA3 3TB', 'El Western Digital Red Plus 3,5" SATA3 3 TB es un disco duro de alta capacidad diseñado para sistemas NAS. Con 3 TB de almacenamiento, este HDD ofrece un rendimiento y fiabilidad optimizados para entornos de múltiples usuarios o redes de datos. Es ideal para usuarios que buscan una solución de almacenamiento robusta con una eficaz gestión térmica y bajo consumo energético.'),

    (42, 'fr-FR', 'Western Digital Red Plus 3,5" SATA3 4 To', 'Le Western Digital Red Plus 3,5" SATA3 4 To est un disque dur performant conçu pour les systèmes NAS à charge de travail élevée. Avec 4 To de capacité, il permet une gestion efficace des données tout en garantissant une fiabilité et des performances constantes. Ce modèle est parfait pour les petites et moyennes entreprises ou pour un usage domestique nécessitant une grande capacité de stockage et une solution évolutive pour les applications de stockage réseau.'),
    (42, 'es-ES', 'Western Digital Red Plus 3,5" SATA3 4TB', 'El Western Digital Red Plus 3,5" SATA3 4 TB es un disco duro de alto rendimiento diseñado para sistemas NAS con cargas de trabajo elevadas. Con 4 TB de capacidad, permite una gestión eficaz de los datos mientras garantiza un rendimiento y fiabilidad constantes. Este modelo es ideal para pequeñas y medianas empresas o uso doméstico que requieren gran capacidad de almacenamiento y una solución escalable para aplicaciones de almacenamiento en red.'),
    
-- NVIDIA graphics cards
    (43, 'fr-FR', 'EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10 Go GDDR6X', 'La EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10 Go GDDR6X est une carte graphique haut de gamme idéale pour les joueurs et les créateurs de contenu. Dotée de la puce NVIDIA RTX 3080, elle offre une performance exceptionnelle pour les jeux en 4K et le ray tracing. Avec 10 Go de mémoire GDDR6X ultra-rapide, cette carte assure des performances stables et fluides, même avec des titres exigeants et les dernières technologies graphiques. Le modèle LHR réduit les capacités d\'extraction de cryptomonnaies pour une meilleure disponibilité pour les joueurs.'),
    (43, 'es-ES', 'EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10GB GDDR6X', 'La EVGA GeForce RTX 3080 FTW3 ULTRA GAMING LHR 10 GB GDDR6X es una tarjeta gráfica de alta gama ideal para jugadores y creadores de contenido. Con el chip NVIDIA RTX 3080, ofrece un rendimiento excepcional para juegos en 4K y trazado de rayos. Con 10 GB de memoria GDDR6X ultra rápida, esta tarjeta asegura un rendimiento estable y fluido incluso con títulos exigentes y las últimas tecnologías gráficas. El modelo LHR reduce las capacidades de minería de criptomonedas, garantizando mejor disponibilidad para los jugadores.'),

    (44, 'fr-FR', 'EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8 Go GDDR6X', 'La EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8 Go GDDR6X est une carte graphique puissante pour les jeux et la création multimédia. Dotée de la puce NVIDIA RTX 2080 et 8 Go de mémoire GDDR6X, elle permet d\'obtenir des rendus graphiques époustouflants et de jouer en haute définition avec une fluidité exceptionnelle. Le système iCX2 offre un refroidissement amélioré, permettant une gestion thermique optimale lors des sessions de jeu prolongées.'),
    (44, 'es-ES', 'EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8GB GDDR6X', 'La EVGA GeForce RTX 2080 FTW3 Ultra Gaming iCX2 8 GB GDDR6X es una tarjeta gráfica potente para juegos y creación multimedia. Con el chip NVIDIA RTX 2080 y 8 GB de memoria GDDR6X, permite obtener gráficos impresionantes y jugar en alta definición con una fluidez excepcional. El sistema iCX2 ofrece una mejor refrigeración, garantizando una gestión térmica óptima durante largas sesiones de juego.'),

    (45, 'fr-FR', 'ASUS ROG Strix GeForce RTX 4080 OC Edition 16 Go GDDR6X DLSS3', 'La ASUS ROG Strix GeForce RTX 4080 OC Edition 16 Go GDDR6X DLSS3 est une carte graphique haut de gamme conçue pour les jeux et les applications créatives de nouvelle génération. Elle est équipée de 16 Go de mémoire GDDR6X ultra-rapide, permettant des performances exceptionnelles en 4K et 8K. Le DLSS3 de NVIDIA améliore encore la fluidité des jeux grâce à l\'intelligence artificielle. Avec un système de refroidissement avancé et un overclocking d\'usine, cette carte est idéale pour des performances maximales dans les jeux et la création de contenu.'),
    (45, 'es-ES', 'ASUS ROG Strix GeForce RTX 4080 OC Edition 16GB GDDR6X DLSS3', 'La ASUS ROG Strix GeForce RTX 4080 OC Edition 16 GB GDDR6X DLSS3 es una tarjeta gráfica de gama alta diseñada para juegos y aplicaciones creativas de última generación. Equipado con 16 GB de memoria GDDR6X ultrarrápida, ofrece un rendimiento excepcional en 4K y 8K. El DLSS3 de NVIDIA mejora aún más la fluidez de los juegos gracias a la inteligencia artificial. Con un avanzado sistema de refrigeración y overclocking de fábrica, esta tarjeta es ideal para obtener el máximo rendimiento en juegos y creación de contenido.'),
    
    -- AMD graphics cards
    (46, 'fr-FR', 'Gigabyte AMD Radeon RX 7700 XT GAMING OC 12 Go GDDR6', 'La Gigabyte AMD Radeon RX 7700 XT GAMING OC 12 Go GDDR6 est une carte graphique haut de gamme conçue pour offrir des performances exceptionnelles dans les jeux modernes et les applications graphiques exigeantes. Elle dispose de 12 Go de mémoire GDDR6 ultra-rapide, permettant une fluidité parfaite dans les jeux en haute définition et un rendu graphique de qualité. Le modèle GAMING OC bénéficie d\'un système de refroidissement avancé pour maintenir des températures optimales même lors de sessions de jeu prolongées.'),
    (46, 'es-ES', 'Gigabyte AMD Radeon RX 7700 XT GAMING OC 12GB GDDR6', 'La Gigabyte AMD Radeon RX 7700 XT GAMING OC 12 GB GDDR6 es una tarjeta gráfica de gama alta diseñada para ofrecer un rendimiento excepcional en juegos modernos y aplicaciones gráficas exigentes. Cuenta con 12 GB de memoria GDDR6 ultrarrápida, lo que permite una fluidez perfecta en juegos en alta definición y una calidad de renderizado superior. El modelo GAMING OC incluye un sistema de refrigeración avanzado para mantener temperaturas óptimas incluso durante sesiones de juego prolongadas.'),

    (47, 'fr-FR', 'Gigabyte AMD Radeon RX 6700 XT GAMING OC 12 Go GDDR6', 'La Gigabyte AMD Radeon RX 6700 XT GAMING OC 12 Go GDDR6 est une carte graphique puissante idéale pour les jeux en haute définition et les applications graphiques intenses. Avec ses 12 Go de mémoire GDDR6, elle garantit une expérience de jeu fluide et de haute qualité, tout en étant capable de supporter des technologies comme le ray tracing. Le système de refroidissement GAMING OC assure des performances optimales tout en maintenant des températures basses.'),
    (47, 'es-ES', 'Gigabyte AMD Radeon RX 6700 XT GAMING OC 12GB GDDR6', 'La Gigabyte AMD Radeon RX 6700 XT GAMING OC 12 GB GDDR6 es una tarjeta gráfica potente, ideal para juegos en alta definición y aplicaciones gráficas intensivas. Con 12 GB de memoria GDDR6, ofrece una experiencia de juego fluida y de alta calidad, y es capaz de soportar tecnologías como el trazado de rayos. El sistema de refrigeración GAMING OC garantiza un rendimiento óptimo manteniendo temperaturas bajas.'),

    (48, 'fr-FR', 'ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24 Go GDDR6', 'La ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24 Go GDDR6 est une carte graphique haut de gamme conçue pour offrir des performances de jeu exceptionnelles en 4K et au-delà. Avec ses 24 Go de mémoire GDDR6, elle est parfaitement équipée pour les jeux les plus gourmands en ressources et les applications créatives. L\'édition OC offre un overclocking d\'usine pour des performances maximales, tandis que le système de refroidissement TUF garantit une stabilité à toute épreuve, même sous pression.'),
    (48, 'es-ES', 'ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24GB GDDR6', 'La ASUS TUF Gaming Radeon RX 7900 XTX OC Edition 24 GB GDDR6 es una tarjeta gráfica de gama alta diseñada para ofrecer un rendimiento excepcional en juegos 4K y más allá. Con 24 GB de memoria GDDR6, está perfectamente equipada para juegos que requieren muchos recursos y aplicaciones creativas. La edición OC ofrece un overclocking de fábrica para un rendimiento máximo, mientras que el sistema de refrigeración TUF garantiza estabilidad incluso bajo cargas intensas.'),
    
    -- Cases
    (49, 'fr-FR', 'Fractal Design Node 202 Mini ITX Desktop Case', 'Le Fractal Design Node 202 Mini ITX Desktop Case est un boîtier compact et élégant conçu pour les configurations Mini ITX. Il combine des fonctionnalités de refroidissement efficaces avec un design minimaliste, idéal pour les utilisateurs à la recherche d\'un boîtier compact sans compromis sur les performances. Ce boîtier est parfait pour les configurations de bureau ou les petits espaces tout en offrant une excellente gestion de l\'airflow et un espace suffisant pour des composants puissants.'),
    (49, 'es-ES', 'Fractal Design Node 202 Mini ITX Desktop Case', 'El Fractal Design Node 202 Mini ITX Desktop Case es una caja compacta y elegante diseñada para configuraciones Mini ITX. Combina características de refrigeración eficientes con un diseño minimalista, ideal para usuarios que buscan una caja compacta sin comprometer el rendimiento. Esta caja es perfecta para configuraciones de escritorio o espacios reducidos, ofreciendo una excelente gestión del flujo de aire y espacio suficiente para componentes potentes.'),

    (50, 'fr-FR', 'NZXT H510 Boîtier Compact ATX Tour Moyenne', 'Le NZXT H510 est un boîtier ATX moyen tour qui offre un excellent équilibre entre performance et esthétisme. Son design épuré et moderne inclut une gestion des câbles simplifiée, un panneau latéral en verre trempé et un espace de refroidissement suffisant pour accueillir des composants haut de gamme. Il est idéal pour ceux qui souhaitent assembler une configuration puissante dans un boîtier compact et élégant.'),
    (50, 'es-ES', 'NZXT H510 Caja Compacta ATX Torre Media', 'El NZXT H510 es una caja ATX de torre media que ofrece un excelente equilibrio entre rendimiento y estética. Su diseño limpio y moderno incluye una gestión simplificada de cables, un panel lateral de vidrio templado y suficiente espacio de refrigeración para componentes de alto rendimiento. Es ideal para aquellos que desean montar una configuración potente en una caja compacta y elegante.'),

    (51, 'fr-FR', 'Cooler Master MasterBox Q300L Boîtier mATX', 'Le Cooler Master MasterBox Q300L est un boîtier mATX compact et flexible, offrant une excellente modularité et un refroidissement efficace. Ce boîtier est conçu pour accueillir facilement vos composants tout en offrant de multiples options d\'agencement et un flux d\'air optimal. Sa conception en mesh et son espace généreux en font une option idéale pour ceux qui recherchent une solution abordable et performante pour leurs configurations mATX.'),
    (51, 'es-ES', 'Cooler Master MasterBox Q300L Caja mATX', 'El Cooler Master MasterBox Q300L es una caja compacta y flexible mATX, que ofrece una excelente modularidad y refrigeración eficiente. Esta caja está diseñada para albergar fácilmente sus componentes, ofreciendo múltiples opciones de disposición y un flujo de aire óptimo. Su diseño en malla y su espacioso interior la convierten en una opción ideal para aquellos que buscan una solución económica y de alto rendimiento para configuraciones mATX.'),

    (52, 'fr-FR', 'Fractal Design Meshify C Boîtier ATX Tour Moyenne', 'Le Fractal Design Meshify C est un boîtier ATX moyen tour qui allie performance de refroidissement, espace intérieur optimal et un design élégant. Sa façade en mesh permet un flux d\'air exceptionnel pour maintenir des températures basses, même lors des sessions de jeu ou d\'utilisation intensive. Il offre une grande modularité et peut accueillir une large gamme de composants. Ce boîtier est parfait pour les utilisateurs qui recherchent à la fois un boîtier esthétique et performant.'),
    (52, 'es-ES', 'Fractal Design Meshify C Caja ATX Torre Media', 'El Fractal Design Meshify C es una caja ATX de torre media que combina un rendimiento de refrigeración excepcional, un espacio interior óptimo y un diseño elegante. Su panel frontal de malla permite un flujo de aire excepcional para mantener bajas las temperaturas, incluso durante sesiones intensivas de juegos o uso. Ofrece una gran modularidad y puede albergar una amplia gama de componentes. Esta caja es perfecta para los usuarios que buscan una solución estética y de alto rendimiento.'),
    
-- Power Supplies
    (53, 'fr-FR', 'EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Modulaire', 'L\'EVGA SuperNOVA 550 G5 est une alimentation modulaire certifiée 80 Plus Gold d\'une puissance de 550W, offrant une excellente efficacité énergétique et une performance fiable. Grâce à sa conception modulaire, vous pouvez choisir les câbles dont vous avez besoin, réduisant ainsi l\'encombrement et améliorant la gestion des câbles. Cette alimentation est idéale pour les configurations de taille moyenne, offrant une stabilité et une durabilité exceptionnelles.'),
    (53, 'es-ES', 'EVGA SuperNOVA 550 G5, 80 Plus Gold 550W, Totalmente Modular', 'La EVGA SuperNOVA 550 G5 es una fuente de alimentación totalmente modular certificada 80 Plus Gold con una potencia de 550W, ofreciendo una excelente eficiencia energética y un rendimiento fiable. Gracias a su diseño modular, puede seleccionar los cables necesarios, reduciendo el desorden y mejorando la gestión de cables. Esta fuente de alimentación es ideal para configuraciones de tamaño medio, proporcionando estabilidad y durabilidad excepcionales.'),

    (54, 'fr-FR', 'Corsair RM850x, 850W, 80 Plus Gold, Modulaire', 'La Corsair RM850x est une alimentation modulaire certifiée 80 Plus Gold d\'une puissance de 850W, offrant une excellente efficacité énergétique et un fonctionnement silencieux grâce à son ventilateur à faible bruit. Idéale pour les configurations de gaming ou de travail puissantes, elle assure des performances stables et fiables. Son design modulaire permet une installation propre et une gestion optimale des câbles.'),
    (54, 'es-ES', 'Corsair RM850x, 850W, 80 Plus Gold, Totalmente Modular', 'La Corsair RM850x es una fuente de alimentación totalmente modular certificada 80 Plus Gold con una potencia de 850W, ofreciendo una excelente eficiencia energética y un funcionamiento silencioso gracias a su ventilador de bajo ruido. Ideal para configuraciones de gaming o trabajo potentes, proporciona un rendimiento estable y fiable. Su diseño modular permite una instalación limpia y una gestión óptima de cables.'),

    (55, 'fr-FR', 'Corsair HX1200i, 1200W, 80 Plus Platinum, Modulaire', 'La Corsair HX1200i est une alimentation modulaire certifiée 80 Plus Platinum, offrant une puissance de 1200W pour les configurations les plus exigeantes. Elle combine une efficacité énergétique exceptionnelle avec des performances stables. Grâce à la gestion de câbles modulaire, vous bénéficiez d\'une installation soignée et optimisée. Elle est équipée de la technologie Corsair Link pour un contrôle et une surveillance avancée des performances.'),
    (55, 'es-ES', 'Corsair HX1200i, 1200W, 80 Plus Platinum, Totalmente Modular', 'La Corsair HX1200i es una fuente de alimentación totalmente modular certificada 80 Plus Platinum, con una potencia de 1200W para configuraciones de alto rendimiento. Combina una eficiencia energética excepcional con un rendimiento estable. Gracias a la gestión modular de cables, puede disfrutar de una instalación limpia y optimizada. Está equipada con la tecnología Corsair Link para un control y monitoreo avanzados de las prestaciones.');


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
    
    INSERT INTO `ATTRIBUTE_TYPE_LOCALE` (`ATTRIBUTE_TYPE_ID`, `LOCALE_ID`, `NAME`)
VALUES
    -- Brand
    (1, 'fr-FR', 'Marque'),
    (1, 'es-ES', 'Marca'),

    -- Processor attributes
    (2, 'fr-FR', 'Fréquence de Base (MHz)'),
    (2, 'es-ES', 'Frecuencia Base (MHz)'),
    (3, 'fr-FR', 'Fréquence Boost (MHz)'),
    (3, 'es-ES', 'Frecuencia Boost (MHz)'),
    (4, 'fr-FR', 'Nombre de Cœurs'),
    (4, 'es-ES', 'Número de Núcleos'),
    (5, 'fr-FR', 'Nombre de Threads'),
    (5, 'es-ES', 'Número de Hilos'),
    (6, 'fr-FR', 'Mémoire Cache L1 (KB)'),
    (6, 'es-ES', 'Memoria Caché L1 (KB)'),
    (7, 'fr-FR', 'Mémoire Cache L2 (KB)'),
    (7, 'es-ES', 'Memoria Caché L2 (KB)'),
    (8, 'fr-FR', 'Mémoire Cache L3 (MB)'),
    (8, 'es-ES', 'Memoria Caché L3 (MB)'),
    (9, 'fr-FR', 'TDP (Puissance Thermique, W)'),
    (9, 'es-ES', 'TDP (Potencia de Diseño Térmico, W)'),
    (10, 'fr-FR', 'Socket'),
    (10, 'es-ES', 'Socket'),
    (11, 'fr-FR', 'Processus de Fabrication (nm)'),
    (11, 'es-ES', 'Proceso de Fabricación (nm)'),
    (12, 'fr-FR', 'Graphiques Intégrés'),
    (12, 'es-ES', 'Gráficos Integrados'),
    (13, 'fr-FR', 'Processeur Graphique Intégré'),
    (13, 'es-ES', 'Procesador Gráfico Integrado'),
    (14, 'fr-FR', 'Fréquence de Base des Graphiques Intégrés (MHz)'),
    (14, 'es-ES', 'Frecuencia Base de los Gráficos Integrados (MHz)'),
    (15, 'fr-FR', 'Fréquence Max des Graphiques Intégrés (MHz)'),
    (15, 'es-ES', 'Frecuencia Máxima de los Gráficos Integrados (MHz)'),
    (16, 'fr-FR', 'Unités d’Exécution des Graphiques Intégrés'),
    (16, 'es-ES', 'Unidades de Ejecución de los Gráficos Integrados'),
    (17, 'fr-FR', 'Cœurs Graphiques Intégrés'),
    (17, 'es-ES', 'Núcleos de los Gráficos Integrados'),
    (18, 'fr-FR', 'Type de Mémoire'),
    (18, 'es-ES', 'Tipo de Memoria'),

    -- Motherboard attributes
    (19, 'fr-FR', 'Format'),
    (19, 'es-ES', 'Formato'),
    (20, 'fr-FR', 'Chipset'),
    (20, 'es-ES', 'Chipset'),
    (21, 'fr-FR', 'Slots Mémoire'),
    (21, 'es-ES', 'Ranuras de Memoria'),
    (22, 'fr-FR', 'Mémoire Max (GB)'),
    (22, 'es-ES', 'Memoria Máxima (GB)'),
    (23, 'fr-FR', 'Slots PCIe x16'),
    (23, 'es-ES', 'Ranuras PCIe x16'),
    (24, 'fr-FR', 'Slots PCIe x1'),
    (24, 'es-ES', 'Ranuras PCIe x1'),
    (25, 'fr-FR', 'Ports SATA III'),
    (25, 'es-ES', 'Puertos SATA III'),
    (26, 'fr-FR', 'Slots M.2'),
    (26, 'es-ES', 'Ranuras M.2'),

    -- RAM attributes
    (27, 'fr-FR', 'Capacité RAM (GB)'),
    (27, 'es-ES', 'Capacidad RAM (GB)'),
    (28, 'fr-FR', 'Fréquence (MHz)'),
    (28, 'es-ES', 'Frecuencia (MHz)'),
    (29, 'fr-FR', 'Latence CAS'),
    (29, 'es-ES', 'Latencia CAS'),
    (30, 'fr-FR', 'Tension (V)'),
    (30, 'es-ES', 'Voltaje (V)'),
    (31, 'fr-FR', 'Dissipateur Thermique'),
    (31, 'es-ES', 'Disipador Térmico'),
    (32, 'fr-FR', 'Nombre de Modules RAM'),
    (32, 'es-ES', 'Número de Módulos RAM'),

    -- Storage device attributes
    (33, 'fr-FR', 'Format de Stockage'),
    (33, 'es-ES', 'Formato de Almacenamiento'),
    (34, 'fr-FR', 'Capacité de Stockage (GB)'),
    (34, 'es-ES', 'Capacidad de Almacenamiento (GB)'),
    (35, 'fr-FR', 'Interface'),
    (35, 'es-ES', 'Interfaz'),
    (36, 'fr-FR', 'Vitesse de Lecture (MB/s)'),
    (36, 'es-ES', 'Velocidad de Lectura (MB/s)'),
    (37, 'fr-FR', 'Vitesse d’Écriture (MB/s)'),
    (37, 'es-ES', 'Velocidad de Escritura (MB/s)'),

    -- SSD specific attributes
    (38, 'fr-FR', 'Type de NAND'),
    (38, 'es-ES', 'Tipo de NAND'),
    (39, 'fr-FR', 'Endurance (TBW)'),
    (39, 'es-ES', 'Resistencia (TBW)'),

    -- HDD specific attributes
    (40, 'fr-FR', 'Vitesse de Rotation (RPM)'),
    (40, 'es-ES', 'Velocidad de Rotación (RPM)'),

    -- GPU specific attributes
    (41, 'fr-FR', 'Cœurs CUDA'),
    (41, 'es-ES', 'Núcleos CUDA'),
    (42, 'fr-FR', 'Taille de Mémoire (GB)'),
    (42, 'es-ES', 'Tamaño de Memoria (GB)'),
    (43, 'fr-FR', 'Bande Passante de Mémoire (GB/s)'),
    (43, 'es-ES', 'Ancho de Banda de Memoria (GB/s)'),

    -- Power Supply specific attributes
    (44, 'fr-FR', 'Consommation Électrique Max (W)'),
    (44, 'es-ES', 'Consumo Máximo de Energía (W)'),
    (45, 'fr-FR', 'Classification d’Efficacité'),
    (45, 'es-ES', 'Clasificación de Eficiencia'),
    (46, 'fr-FR', 'Modularité'),
    (46, 'es-ES', 'Modularidad'),
    (47, 'fr-FR', 'Connecteurs PCIe'),
    (47, 'es-ES', 'Conectores PCIe'),
    (48, 'fr-FR', 'Connecteurs SATA'),
    (48, 'es-ES', 'Conectores SATA'),
    (49, 'fr-FR', 'Connecteurs Molex'),
    (49, 'es-ES', 'Conectores Molex'),
    (50, 'fr-FR', 'Connecteurs EPS'),
    (50, 'es-ES', 'Conectores EPS'),

    -- Case specific attributes
    (51, 'fr-FR', 'Longueur Max GPU (mm)'),
    (51, 'es-ES', 'Longitud Máxima GPU (mm)'),
    (52, 'fr-FR', 'Hauteur Max Refroidisseur CPU (mm)'),
    (52, 'es-ES', 'Altura Máxima del Disipador CPU (mm)'),
    (53, 'fr-FR', 'Longueur Max Bloc d’Alimentation (mm)'),
    (53, 'es-ES', 'Longitud Máxima de la Fuente de Alimentación (mm)'),
    (54, 'fr-FR', 'Baies de Lecteur Externe 5.25"'),
    (54, 'es-ES', 'Bahías de Unidad Externa 5.25"'),
    (55, 'fr-FR', 'Baies de Lecteur Interne 3.5"'),
    (55, 'es-ES', 'Bahías de Unidad Interna 3.5"'),
    (56, 'fr-FR', 'Baies de Lecteur Interne 2.5"'),
    (56, 'es-ES', 'Bahías de Unidad Interna 2.5"');
    
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
	(11, 41), -- CUDA Cores
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
