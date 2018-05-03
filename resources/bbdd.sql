-- CREATE DATABASE DPOO_Restaurant;
USE DPOO_Restaurant;

DROP TABLE IF EXISTS Carta CASCADE;
DROP TABLE IF EXISTS Comanda CASCADE;
DROP TABLE IF EXISTS Reserva CASCADE;
DROP TABLE IF EXISTS Taula CASCADE;



CREATE TABLE Taula(
	id_taula INT NOT NULL AUTO_INCREMENT,
    num_seients int,
    ocupada BOOL,
    PRIMARY KEY (id_taula)
);


CREATE TABLE Reserva(
    id_reserva INT NOT NULL AUTO_INCREMENT,
	id_taula INT,
    nom_reserva VARCHAR(255),
    password_ VARCHAR(255),
    num_comensals INT,
    data_reserva DATETIME,
    conectat bool,
    PRIMARY KEY (id_reserva),
    FOREIGN KEY (id_taula) REFERENCES Taula(id_taula)
);

CREATE TABLE Carta(
	id_plat INT NOT NULL AUTO_INCREMENT,
	tipus_plat INT,
    nom_plat VARCHAR(255),
    preu FLOAT,
    quantitat INT,
    semanals INT,
    totals INT,
    PRIMARY KEY (id_plat)
);

CREATE TABLE Comanda(
    id_comanda INT NOT NULL AUTO_INCREMENT,
    id_plat INT NOT NULL,
    id_taula INT NOT NULL,
    servit BOOL DEFAULT FALSE,
    PRIMARY KEY (id_comanda),
    FOREIGN KEY (id_taula) REFERENCES Taula(id_taula)
    );

SELECT * FROM Carta ORDER BY totals DESC LIMIT 5;

INSERT INTO Taula (num_seients, ocupada) VALUES(4,true);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1,'Bistec', 12.50, 20, 0 ,5);
INSERT INTO Reserva (id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES(1, 'Angel', 'ABC123', 4, STR_TO_DATE('09-04-2018  23:11:00', '%d-%m-%Y %H:%i:%s'), false);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Vi', 50.50, 10, 0, 9);
INSERT INTO Comanda (id_comanda, id_plat, id_taula, servit) VALUES (1, 4, 1, false);
INSERT INTO Comanda (id_comanda, id_plat, id_taula, servit) VALUES (4, 6, 1, true);


SELECT * FROM Taula;
SELECT * FROM Reserva;
SELECT * FROM Carta;
SELECT * FROM Comanda;

SELECT preu * (SELECT quantitat_demanada FROM Comanda WHERE id_taula = 1) FROM Carta WHERE id_plat = (SELECT id_plat FROM Comanda WHERE id_taula = 1);
SELECT ((SELECT preu FROM Carta WHERE id_plat = (SELECT id_plat FROM Comanda WHERE id_taula = 1)) * (SELECT quantitat_demanada FROM Comanda WHERE id_taula = 1)) AS Total;

DELIMITER $$
DROP EVENT IF EXISTS updateWeekly $$
CREATE EVENT IF	NOT	EXISTS updateWeekly
ON SCHEDULE EVERY 1 WEEK
STARTS '2018-05-23	00:00:00'
DO BEGIN
UPDATE Carta SET semanals = 0;
END	$$
DELIMITER ;