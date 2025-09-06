INSERT INTO LOCALE (ID, NAME) 
VALUES 
('en-GB', 'English (United Kingdom)'),
('es-ES', 'Español (España)'),
('fr-FR', 'Français');

INSERT INTO `CATEGORY` (`ID`, `PARENT_ID`) 
VALUES
    (1, NULL),
    (2, NULL),
    (3, NULL),
    (4, NULL),
    (5, NULL),
    (6, NULL),
    (7, NULL),
    (8, NULL),
    (9, 4),
    (10, 4),
    (11, 5),
    (12, 5),
    (13, 11),
    (14, 11),
    (15, 14),
    (16, 14),
    (17, 14);

INSERT INTO `CATEGORY_LOCALE` (`CATEGORY_ID`, `LOCALE_ID`, `NAME`) 
VALUES
    (1, 'en-GB', 'Processor'),
    (2, 'en-GB', 'Motherboard'),
    (3, 'en-GB', 'RAM'),
    (4, 'en-GB', 'Storage'),
    (5, 'en-GB', 'Graphics Card'),
    (6, 'en-GB', 'Power Supply'),
    (7, 'en-GB', 'Case'),
    (8, 'en-GB', 'Cooling'),
    (9, 'en-GB', 'SSD'),
    (10, 'en-GB', 'HDD'),
    (11, 'en-GB', 'NVIDIA Graphics Card'),
    (12, 'en-GB', 'AMD Graphics Card'),
    (13, 'en-GB', 'GTX Graphics Card'),
    (14, 'en-GB', 'RTX Graphics Card'),
    (15, 'en-GB', 'RTX 2000-series'),
    (16, 'en-GB', 'RTX 3000-series'),
    (17, 'en-GB', 'RTX 4000-series');

-- SQL Script for fr-FR (French)
INSERT INTO `CATEGORY_LOCALE` (`CATEGORY_ID`, `LOCALE_ID`, `NAME`) 
VALUES
    (1, 'fr-FR', 'Processeur'),
    (2, 'fr-FR', 'Carte Mère'),
    (3, 'fr-FR', 'RAM'),
    (4, 'fr-FR', 'Stockage'),
    (5, 'fr-FR', 'Carte Graphique'),
    (6, 'fr-FR', 'Alimentation'),
    (7, 'fr-FR', 'Boîtier'),
    (8, 'fr-FR', 'Refroidissement'),
    (9, 'fr-FR', 'SSD'),
    (10, 'fr-FR', 'HDD'),
    (11, 'fr-FR', 'Carte Graphique NVIDIA'),
    (12, 'fr-FR', 'Carte Graphique AMD'),
    (13, 'fr-FR', 'Carte Graphique GTX'),
    (14, 'fr-FR', 'Carte Graphique RTX'),
    (15, 'fr-FR', 'RTX série 2000'),
    (16, 'fr-FR', 'RTX série 3000'),
    (17, 'fr-FR', 'RTX série 4000');

-- SQL Script for es-ES (Spanish)
INSERT INTO `CATEGORY_LOCALE` (`CATEGORY_ID`, `LOCALE_ID`, `NAME`) 
VALUES
    (1, 'es-ES', 'Procesador'),
    (2, 'es-ES', 'Placa Base'),
    (3, 'es-ES', 'RAM'),
    (4, 'es-ES', 'Almacenamiento'),
    (5, 'es-ES', 'Tarjeta Gráfica'),
    (6, 'es-ES', 'Fuente de Alimentación'),
    (7, 'es-ES', 'Caja'),
    (8, 'es-ES', 'Refrigeración'),
    (9, 'es-ES', 'SSD'),
    (10, 'es-ES', 'HDD'),
    (11, 'es-ES', 'Tarjeta Gráfica NVIDIA'),
    (12, 'es-ES', 'Tarjeta Gráfica AMD'),
    (13, 'es-ES', 'Tarjeta Gráfica GTX'),
    (14, 'es-ES', 'Tarjeta Gráfica RTX'),
    (15, 'es-ES', 'RTX serie 2000'),
    (16, 'es-ES', 'RTX serie 3000'),
    (17, 'es-ES', 'RTX serie 4000');

    
INSERT INTO `COUNTRY` (`ID`, `NAME`)
VALUES
    ('ESP', 'Spain'),
    ('DEU', 'Germany'),
    ('FRA', 'France'),
    ('ITA', 'Italy'),
    ('NLD', 'Netherlands'),
    ('BEL', 'Belgium'),
    ('AUT', 'Austria'),
    ('PRT', 'Portugal'),
    ('SWE', 'Sweden'),
    ('DNK', 'Denmark'),
    ('FIN', 'Finland'),
    ('GRC', 'Greece'),
    ('IRL', 'Ireland'),
    ('LUX', 'Luxembourg'),
    ('CYP', 'Cyprus'),
    ('EST', 'Estonia'),
    ('LVA', 'Latvia'),
    ('LTU', 'Lithuania'),
    ('MLT', 'Malta'),
    ('POL', 'Poland'),
    ('SVK', 'Slovakia'),
    ('SVN', 'Slovenia'),
    ('CZE', 'Czech Republic'),
    ('HUN', 'Hungary'),
    ('BGR', 'Bulgaria'),
    ('ROU', 'Romania'),
    ('HRV', 'Croatia');

INSERT INTO `PROVINCE` (`NAME`, `COUNTRY_ID`)
VALUES
    ('Madrid', 'ESP'), ('Barcelona', 'ESP'), ('Valencia', 'ESP'), ('Seville', 'ESP'), ('Zaragoza', 'ESP'), ('Málaga', 'ESP'),
    ('Murcia', 'ESP'), ('Palma', 'ESP'), ('Las Palmas', 'ESP'), ('Bilbao', 'ESP'), ('Alicante', 'ESP'), ('Córdoba', 'ESP'),
    ('Valladolid', 'ESP'), ('Vigo', 'ESP'), ('Gijón', 'ESP'), ('Hospitalet de Llobregat', 'ESP'), ('Vitoria-Gasteiz', 'ESP'),
    ('Badalona', 'ESP'), ('Cartagena', 'ESP'), ('Tarragona', 'ESP'), ('Zamora', 'ESP'), ('Oviedo', 'ESP'), ('Pamplona', 'ESP'),
    ('Santander', 'ESP'), ('Logroño', 'ESP'), ('Toledo', 'ESP'), ('Segovia', 'ESP'), ('Cuenca', 'ESP'), ('Ávila', 'ESP'),
    ('Soria', 'ESP'), ('Teruel', 'ESP'), ('Huesca', 'ESP'), ('Jaén', 'ESP'), ('Ciudad Real', 'ESP'), ('Cáceres', 'ESP'),
    ('Badajoz', 'ESP'), ('Almería', 'ESP'), ('Huelva', 'ESP'), ('Cádiz', 'ESP'), ('Castellón', 'ESP'), ('Albacete', 'ESP'),
    ('Granada', 'ESP'), ('Lleida', 'ESP'), ('Girona', 'ESP'), ('A Coruña', 'ESP'), ('Ourense', 'ESP'), ('Lugo', 'ESP'),
    ('Pontevedra', 'ESP'), ('Salamanca', 'ESP'), ('Burgos', 'ESP'),
    
    ('Berlin', 'DEU'), ('Hamburg', 'DEU'), ('Munich', 'DEU'), ('Cologne', 'DEU'), ('Frankfurt', 'DEU'), ('Stuttgart', 'DEU'),
    ('Düsseldorf', 'DEU'), ('Dortmund', 'DEU'), ('Essen', 'DEU'), ('Leipzig', 'DEU'), ('Bremen', 'DEU'), ('Dresden', 'DEU'),
    ('Hanover', 'DEU'), ('Nuremberg', 'DEU'), ('Duisburg', 'DEU'), ('Bochum', 'DEU'), ('Wuppertal', 'DEU'), ('Bielefeld', 'DEU'),
    ('Bonn', 'DEU'), ('Münster', 'DEU'),
    
    ('Paris', 'FRA'), ('Marseille', 'FRA'), ('Lyon', 'FRA'), ('Toulouse', 'FRA'), ('Nice', 'FRA'), ('Nantes', 'FRA'),
    ('Strasbourg', 'FRA'), ('Montpellier', 'FRA'), ('Bordeaux', 'FRA'), ('Lille', 'FRA'), ('Rennes', 'FRA'), ('Reims', 'FRA'),
    ('Le Havre', 'FRA'), ('Saint-Étienne', 'FRA'), ('Toulon', 'FRA'), ('Grenoble', 'FRA'), ('Dijon', 'FRA'), ('Nîmes', 'FRA'),
    ('Angers', 'FRA'), ('Villeurbanne', 'FRA');


INSERT INTO `CITY` (`NAME`, `PROVINCE_ID`) 
VALUES
	-- Inserting cities for Spain
	('Madrid', 1), ('Alcalá de Henares', 1), ('Leganés', 1), ('Getafe', 1), ('Móstoles', 1),
	('Barcelona', 2), ('L''Hospitalet de Llobregat', 2), ('Badalona', 2), ('Sabadell', 2), ('Terrassa', 2),
	('Valencia', 3), ('Gandía', 3), ('Torrent', 3), ('Paterna', 3), ('Sagunto', 3),
	('Seville', 4), ('Dos Hermanas', 4), ('Alcalá de Guadaíra', 4), ('Mairena del Aljarafe', 4), ('Écija', 4),
	('Alicante', 5), ('Elche', 5), ('Torrevieja', 5), ('Orihuela', 5), ('Benidorm', 5),
	('Murcia', 6), ('Palma', 7), ('Las Palmas', 8), ('Bilbao', 9), ('Córdoba', 10),
	('Valladolid', 11), ('Vigo', 12), ('Gijón', 13), ('L''Hospitalet de Llobregat', 14), ('Vitoria-Gasteiz', 15),
	('Badalona', 16), ('Cartagena', 17), ('Tarragona', 18), ('Zamora', 19), ('Oviedo', 20),

	-- Inserting cities for Germany
	('Berlin', 21), ('Hamburg', 22), ('Munich', 23), ('Cologne', 24), ('Frankfurt', 25),
	('Stuttgart', 26), ('Düsseldorf', 27), ('Dortmund', 28), ('Essen', 29), ('Leipzig', 30),
	('Bremen', 31), ('Dresden', 32), ('Hanover', 33), ('Nuremberg', 34), ('Duisburg', 35),
	('Bochum', 36), ('Wuppertal', 37), ('Bielefeld', 38), ('Bonn', 39), ('Münster', 40),

	-- Inserting cities for France
	('Paris', 41), ('Marseille', 42), ('Lyon', 43), ('Toulouse', 44), ('Nice', 45),
	('Nantes', 46), ('Strasbourg', 47), ('Montpellier', 48), ('Bordeaux', 49), ('Lille', 50),
	('Rennes', 51), ('Reims', 52), ('Le Havre', 53), ('Saint-Étienne', 54), ('Toulon', 55),
	('Grenoble', 56), ('Dijon', 57), ('Nîmes', 58), ('Angers', 59), ('Villeurbanne', 60);

INSERT INTO `DOCUMENT_TYPE` (`ID`, `NAME`) 
VALUES
	('NIF', 'National Identification Number'), 
    ('NIE', 'Foreign Identification Number'), 
    ('PPT', 'Passport'), 
    ('FOR', 'Foreign ID');

INSERT INTO `ATTRIBUTE_DATA_TYPE` (`ID`, `NAME`) 
VALUES
	('INT', 'BIGINT'),
	('VAR', 'VARCHAR(128)'),
	('DEC', 'DECIMAL(30,10)'),
	('BOO', 'TINYINT(1)');

INSERT INTO `ORDER_STATE` (`ID`, `NAME`)
VALUES
	('PND', 'Pending'),
	('PRS', 'Processing'),
	('SPD', 'Shipped'),
	('DEL', 'Delivered'),
	('CAN', 'Cancelled');

INSERT INTO `TICKET_TYPE` (`ID`, `NAME`)
VALUES
    ('SUP', 'Technical Support'),
    ('BIL', 'Billing Inquiry'),
    ('PRO', 'Product Inquiry'),
    ('RMA', 'Warranty and Returns');

INSERT INTO `TICKET_STATE` (`ID`, `NAME`)
VALUES
    ('OPN', 'Open'),
    ('PGS', 'In Progress'),
    ('RES', 'Resolved'),
    ('CLO', 'Closed');

INSERT INTO `RMA_STATE` (`ID`, `NAME`)
VALUES
    ('REC', 'Received'),
    ('PRS', 'Processing'),
    ('APP', 'Approved'),
    ('REJ', 'Rejected');
    
INSERT INTO `ROLE` (`ID`, `NAME`)
VALUES
	('admin', 'Administrator'),
    ('sales', 'Sales'),
    ('marketing', 'Marketing'),
    ('hr', 'Human Resources'),
    ('finance', 'Finance'),
    ('ops', 'Operations'),
    ('support', 'Support');