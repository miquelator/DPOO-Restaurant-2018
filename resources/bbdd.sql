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
    id_plat INT NOT NULL, id_taula INT NOT NULL,
    servit BOOL DEFAULT FALSE,
    PRIMARY KEY (id_comanda),
    FOREIGN KEY (id_taula) REFERENCES Taula(id_taula)
    );

SELECT * FROM Carta ORDER BY totals DESC LIMIT 5;





INSERT INTO Taula (num_seients, ocupada) VALUES(4,true);
INSERT INTO Carta (nom_plat, preu, quantitat, semanals, totals) VALUES('Bistec', 12.50, 20, 0 ,5);
INSERT INTO Reserva (id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES(1, 'Angel', 'ABC123', 4, STR_TO_DATE('09-04-2018  23:11:00', '%d-%m-%Y %H:%i:%s'), false);
INSERT INTO Carta (nom_plat, preu, quantitat, semanals, totals) VALUES('Vi', 50.50, 10, 0, 9);
SELECT * FROM Taula;
SELECT * FROM Reserva;
SELECT * FROM Carta;
