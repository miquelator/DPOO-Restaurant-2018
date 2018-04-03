-- CREATE DATABASE DPOO_Restaurant;
USE DPOO_Restaurant;

DROP TABLE IF EXISTS Reserva CASCADE;
DROP TABLE IF EXISTS Taula CASCADE;
DROP TABLE IF EXISTS Carta CASCADE;


CREATE TABLE Taula(
	id_taula INT NOT NULL AUTO_INCREMENT,
    num_seients int,
    ocupada BOOL,
    PRIMARY KEY (id_taula)
);


CREATE TABLE Reserva(
	id_taula INT,
    nom_reserva VARCHAR(255),
    password_ VARCHAR(255),
    num_comensals INT,
    data_reserva DATE,
    PRIMARY KEY (nom_reserva),
    FOREIGN KEY (id_taula) REFERENCES Taula(id_taula)
);

CREATE TABLE Carta(
	id_plat INT NOT NULL AUTO_INCREMENT,
    nom_plat VARCHAR(255),
    preu FLOAT,
    quantitat INT,
    PRIMARY KEY (id_plat)
);

/*
SELECT * FROM Taula;
SELECT * FROM Reserva;
SELECT * FROM Carta;
*/

INSERT INTO Taula (num_seients, ocupada) VALUES(4,true);
INSERT INTO Carta (nom_plat, preu, quantitat) VALUES('Bistec', 12.50, 20);
INSERT INTO Reserva (id_taula, nom_reserva, password_, num_comensals, data_reserva) VALUES(1, 'Angel', 'ABC123', 6, STR_TO_DATE('1-01-2012', '%d-%m-%Y'));
