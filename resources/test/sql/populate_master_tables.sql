INSERT INTO `CATEGORY` (`ID`, `NAME`, `PARENT_ID`) 
VALUES
	(1, 'Processor', NULL), (2, 'Motherboard', NULL), (3, 'RAM', NULL), (4, 'Storage', NULL), (5, 'Graphics Card', NULL),
	(6, 'Power Supply', NULL), (7, 'Case', NULL), (8, 'Cooling', NULL), (9, 'SSD', 4), (10, 'HDD', 4), (11, 'NVIDIA Graphics Card', 5), (12, 'AMD Graphics Card', 5),
	(13, 'GTX Graphics Card', 11), (14, 'RTX Graphics Card', 11), (15, 'RTX 2000-series', 14), (16, 'RTX 3000-series', 14), (17, 'RTX 4000-series', 14);
    
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
    
INSERT INTO `DEPARTMENT` (`ID`, `NAME`)
VALUES
    ('SAL', 'Sales'),
    ('MKT', 'Marketing'),
    ('HRS', 'Human Resources'),
    ('EXC', 'Executive'),
    ('FIN', 'Finance'),
    ('OPS', 'Operations'),
    ('SUP', 'Support');