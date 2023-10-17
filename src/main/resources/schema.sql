
CREATE SCHEMA IF NOT EXISTS memberdb;

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE member(
                       id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       first_name   VARCHAR(20) NOT NULL,
                       last_name    VARCHAR(30) NOT NULL,
                       address_id     INT NOT NULL,
                       email       VARCHAR(45) NOT NULL,
                       phone       INT default 0,
                       date_of_birth VARCHAR(10) NOT NULL
);

CREATE TABLE address(
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        street VARCHAR(30) NOT NULL,
                        postal_code INT NOT NULL,
                        city VARCHAR(20) NOT NULL
);

ALTER TABLE member ADD FOREIGN KEY (address_id) REFERENCES address (id);

INSERT INTO address (street, postal_code, city)
VALUES ('Haspelvägen 3', 87445, 'Växsjö'),
       ('Sjöbotten 1', 46532, 'LångtBortIStan'),
       ('Alstigen 4', 91831, 'Sävar'),
       ('Tjädervägen 14', 484848, 'Sundsvall'),
       ('skrubrna 34', 32158, 'Tallin');


INSERT INTO member (first_Name, last_name, email, phone, date_of_birth, address_id)
VALUES ('Tyra', 'Persson', 'Tyra@cat.se)', 015555666, '5/4-2008', 1),
       ('Jerry', 'Persson', 'jerry@cat.se', 66654665, '6/1-1948', 1),
       ('Hasse', 'Andersson', 'hasse@tomtem.se', 112, '3/3-1125', 2),
       ('Göta', 'Petter', 'gp@posten.se', 09052043, '12/3-19600', 3),
       ('Master', 'Wigell', 'wigell@wigell.se', 46843216, '8/5-1978', 4);

INSERT INTO member (first_Name, last_name, email, date_of_birth, address_id)
VALUES ('Lasse', 'Kongo', 'LK@AB.se)', '3/8-1978', 5);
