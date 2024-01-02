
DROP SCHEMA IF EXISTS wigellsdb;

CREATE TABLE customer (
                          customer_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          username VARCHAR(45) UNIQUE,
                          password VARCHAR(68),
                          authority VARCHAR(45) NOT NULL,
                          first_name VARCHAR(20),
                          last_name VARCHAR(30) ,
                          address_id INT,
                          trip_id INT DEFAULT 0,
                          email VARCHAR(45) ,
                          phone varchar(20) DEFAULT 0,
                          date_of_birth VARCHAR(10),
                          active TINYINT NOT NULL
);

CREATE TABLE customer_trips(
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               customer_id INT ,
                               trip_id INT
);

CREATE TABLE trip(
                     trip_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                     destination_id INT,
                     no_of_weeks INT DEFAULT 0,
                     total_price_SEK FLOAT(53) DEFAULT 0,
                     total_price_PLN FLOAT(53) DEFAULT 0,
                     departure_date VARCHAR(255)
);

CREATE TABLE destination (
                             destination_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                             price_per_week FLOAT(53) DEFAULT 0,
                             city VARCHAR(255),
                             country VARCHAR(255),
                             hotell_name VARCHAR(255)
);

CREATE TABLE address (
                         address_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                         street VARCHAR(30) ,
                         postal_code INT DEFAULT 0,
                         city VARCHAR(20)
);


INSERT INTO customer (username, password, authority, first_Name, last_name, email, phone, date_of_birth, address_id, active)
VALUES ('tyra', '{noop}tyra', 'ROLE_USER','Tyra', 'Persson', 'Tyra@cat.se', '0756481342', '2008-05-04', 1, 1),
       ('jerry', '{noop}jerry', 'ROLE_USER','Jerry', 'Persson', 'jerry@cat.se', '0766654665', '1948-01-06', 1, 1),
       ('hasse','{noop}hasse', 'ROLE_USER','Hasse', 'Andersson', 'hasse@tomtem.se', '0754785624', '1125-03-03', 2, 1),
       ('gota','{noop}gota', 'ROLE_USER','Göta', 'Petter', 'gp@posten.se', '0749654214', '1960-03-12', 3, 0),
       ('master','{noop}master', 'ROLE_ADMIN','Master', 'Wigell', 'wigell@wigell.se', '0767541258', '1978-05-08', 4, 1),
       ('lasse','{noop}lasse', 'ROLE_ADMIN','Lasse', 'Kongo', 'LK@AB.se)', '0746854984', '1978-08-03', 5, 1);


INSERT INTO address (street, postal_code, city)
VALUES ('Haspelvägen 3', 87445, 'Växsjö'),
       ('Pilgränd 1', 46532, 'LångtBortIStan'),
       ('Alstigen 4', 91831, 'Sävar'),
       ('Tjädervägen 14', 484848, 'Sundsvall'),
       ('skrubrna 34', 32158, 'Tallin');


-- Dummy trip data
INSERT INTO trip (no_of_weeks, total_price_SEK, total_price_PLN, departure_date, destination_id)
VALUES
    (1, 500, 0, '2023-02-15', 1),
    (2, 5654, 0, '2023-03-22', 2),
    (3, 213, 0, '2023-04-10', 2),
    (4, 23, 0, '2023-05-05', 4),
    (5, 111, 0, '2023-06-18', 5),
    (6, 111, 0, '2023-07-07', 12),
    (7, 111, 0, '2023-08-12', 25),
    (8, 111, 0, '2023-09-01', 31),
    (9, 111, 0, '2023-10-15', 43),
    (10, 111, 0, '2023-11-20', 54),
    (11, 111, 0, '2023-12-05', 62),
    (12, 111, 0, '2023-12-20', 51),
    (13, 111, 0, '2023-12-25', 23),
    (14, 111, 0, '2023-12-30', 14),
    (15, 111, 0, '2023-12-31', 35);

-- Dummy customer trip data
INSERT INTO customer_trips (customer_id, trip_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (2, 8),
    (2, 9),
    (2, 10),
    (3, 11),
    (4, 12),
    (5, 13),
    (5, 14);


INSERT INTO destination(price_per_week, city, country, hotell_name)
VALUES  (1225.5, 'Bryssel', 'Tyskland', 'The Continental'),
        (6444.8, 'Sundsvall', 'Sverige', 'Clarion'),
        (4444.5, 'Umeå', 'Sverige', 'Scandic'),
        (66748.8, 'Alcudia', 'Mallorca', 'Los Pyramidos'),
        (1300.00, 'Bangalore', 'India', 'Taj Mahal Palace'),
        (950.75, 'Dubrovnik', 'Croatia', 'Adriatic Sea Resort'),
        (850.25, 'Havana', 'Cuba', 'Revolution Plaza Hotel'),
        (1200.75, 'Reykjavik', 'Iceland', 'Blue Lagoon Retreat'),
        (1000.00, 'San Francisco', 'United States', 'Golden Gate View Inn'),
        (750.50, 'Helsinki', 'Finland', 'Archipelago Hideaway'),
        (1100.25, 'Riyadh', 'Saudi Arabia', 'Kingdom Tower Suites'),
        (950.80, 'Naples', 'Italy', 'Vesuvius View Hotel'),
        (850.00, 'Phuket', 'Thailand', 'Tropical Paradise Resort'),
        (1300.50, 'Panama City', 'Panama', 'Canal Zone Retreat'),
        (1500.00, 'Paris', 'France', 'Eiffel Palace Hotel'),
        (1200.50, 'Rome', 'Italy', 'Colosseum View Hotel'),
        (800.75, 'Barcelona', 'Spain', 'Sagrada Familia Resort'),
        (1000.25, 'New York', 'United States', 'Central Park Grand Hotel'),
        (900.80, 'Tokyo', 'Japan', 'Imperial Palace Inn'),
        (1300.00, 'Sydney', 'Australia', 'Opera House Suites'),
        (1100.50, 'Cape Town', 'South Africa', 'Table Mountain Resort'),
        (950.75, 'Dubai', 'United Arab Emirates', 'Burj Khalifa Towers Hotel'),
        (850.25, 'Rio de Janeiro', 'Brazil', 'Copacabana Beachfront Resort'),
        (1200.75, 'London', 'United Kingdom', 'Big Ben Boutique Hotel'),
        (1000.00, 'Marrakech', 'Morocco', 'Atlas Mountains Retreat'),
        (750.50, 'Bangkok', 'Thailand', 'Golden Temple Suites'),
        (1100.25, 'Vancouver', 'Canada', 'Rocky Mountain Lodge'),
        (950.80, 'Stockholm', 'Sweden', 'Royal Palace Plaza Hotel'),
        (850.00, 'Seoul', 'South Korea', 'Gyeongbokgung View Inn'),
        (1350.50, 'Cancun', 'Mexico', 'Mayan Riviera Resort'),
        (1050.75, 'Amsterdam', 'Netherlands', 'Windmill Charm Hotel'),
        (900.25, 'Cairo', 'Egypt', 'Pyramid Grand Suites'),
        (800.80, 'Athens', 'Greece', 'Acropolis Oasis Hotel'),
        (1300.00, 'Auckland', 'New Zealand', 'Harbour View Residences'),
        (1100.50, 'Helsinki', 'Finland', 'Northern Lights Lodge'),
        (950.75, 'Singapore', 'Singapore', 'Marina Bay Sands Palace'),
        (850.25, 'Prague', 'Czech Republic', 'Charles Bridge Retreat'),
        (1200.75, 'Istanbul', 'Turkey', 'Blue Mosque Courtyard Hotel'),
        (1000.00, 'Buenos Aires', 'Argentina', 'Tango Plaza Suites'),
        (750.50, 'Hanoi', 'Vietnam', 'Old Quarter Heritage Inn'),
        (1100.25, 'Berlin', 'Germany', 'Brandenburg Gate Mansion'),
        (950.80, 'Lisbon', 'Portugal', 'Belem Tower Residence'),
        (850.00, 'Moscow', 'Russia', 'Kremlin Palace Hotel'),
        (1350.50, 'Copenhagen', 'Denmark', 'Nyhavn Waterfront Suites'),
        (1050.75, 'Dublin', 'Ireland', 'Guinness Brewery Retreat'),
        (900.25, 'Jerusalem', 'Israel', 'Western Wall Suites'),
        (800.80, 'Vienna', 'Austria', 'Schönbrunn Palace Residency'),
        (1300.00, 'Seville', 'Spain', 'Alcazar Gardens Hotel'),
        (1100.50, 'Oslo', 'Norway', 'Viking Ship Harbor Inn'),
        (950.75, 'Budapest', 'Hungary', 'Chain Bridge Palace'),
        (850.25, 'Edinburgh', 'Scotland', 'Castle View Lodge'),
        (1200.75, 'Warsaw', 'Poland', 'Royal Castle Chambers'),
        (1000.00, 'Beijing', 'China', 'Forbidden City Suites'),
        (750.50, 'Nairobi', 'Kenya', 'Safari Plains Resort'),
        (1100.25, 'Casablanca', 'Morocco', 'Atlantic Coast Retreat'),
        (950.80, 'Wellington', 'New Zealand', 'Te Papa Museum Hotel'),
        (850.00, 'Zurich', 'Switzerland', 'Lake Zurich Panorama Inn'),
        (1350.50, 'Brisbane', 'Australia', 'Story Bridge Residences'),
        (1050.75, 'Kuala Lumpur', 'Malaysia', 'Petronas Towers Suites'),
        (900.25, 'Lima', 'Peru', 'Machu Picchu View Hotel'),
        (1050.75, 'Mumbai', 'India', 'Gateway of India Hotel'),
        (900.25, 'Nice', 'France', 'French Riviera Residence');

ALTER TABLE customer ADD FOREIGN KEY (address_id) REFERENCES address (address_id);
ALTER TABLE trip ADD FOREIGN KEY (destination_id) REFERENCES destination (destination_id);
ALTER TABLE customer_trips ADD FOREIGN KEY (trip_id) REFERENCES trip (trip_id);
ALTER TABLE customer_trips ADD FOREIGN KEY (customer_id) REFERENCES customer (customer_id);




