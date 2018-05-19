CREATE DATABASE IF NOT EXISTS DPOO_Restaurant;
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
    id_reserva INT NOT NULL,
    servit BOOL DEFAULT FALSE,
    hora TIMESTAMP,
    PRIMARY KEY (id_comanda),
    FOREIGN KEY (id_taula) REFERENCES Taula(id_taula),
    FOREIGN KEY (id_reserva) REFERENCES Reserva(id_reserva)
    );
        
DELIMITER $$
DROP EVENT IF EXISTS updateWeekly $$
CREATE EVENT IF	NOT	EXISTS updateWeekly
ON SCHEDULE EVERY 1 WEEK
STARTS '2018-05-23	00:00:00'
DO BEGIN
UPDATE Carta SET semanals = 0;
END	$$
DELIMITER ;
        
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Sopa', 8.50, 50, 0 ,0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Amanida', 7.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Cargols a la llauna', 10.50, 10, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Macarrons', 6.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Canalons de la iaia', 6.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Sorpresa del chef', 9.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Paleta de jamón', 12.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Tabla de quesos', 10.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Paleta d\'embutits', 10.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Fideuà', 8.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Espagueti', 9.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(1, 'Paella', 13.00, 50, 0, 0);

INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Entrecot', 23.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Llom', 11.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Lluç',15.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Filet a la brasa', 20.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Pulpo a la  gallega', 12.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Peus de porc', 15.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Gambes de Palamós', 9.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Rap', 10.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Anec a la taronja', 16.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Costella', 11.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Llenguado a la planxa', 9.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(2, 'Calamars amb vi i ceba', 14.50, 50, 0, 0);

INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Natilles', 3.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Crema catalana', 4.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Coulant de chocolata', 3.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Trufes', 4.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Macedónia', 3.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Coco', 3.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Gelat multisabor', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Cheesecake', 5.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Pastis de pastanaga', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Pastis de chcolata', 2.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Sorbet de llimona', 2.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Gelat multisabor', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(3, 'Frutos del bosque', 2.00, 50, 0, 0);

INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Vi', 50.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Aigua', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Cocacola', 3.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Fanta', 3.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Café sol', 1.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Café amb llet', 1.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Café amb gel', 1.30, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Café llarg', 1.30, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Carajillo', 2.50, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Chupito', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Suc de llimona', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Suc de taronja', 2.00, 50, 0, 0);
INSERT INTO Carta (tipus_plat, nom_plat, preu, quantitat, semanals, totals) VALUES(4, 'Cervesa', 1.50, 50, 0, 0);

INSERT INTO Taula (id_taula, num_seients, ocupada) VALUES (1, 1, false);
INSERT INTO Taula (id_taula, num_seients, ocupada) VALUES (2, 2, false);
INSERT INTO Taula (id_taula, num_seients, ocupada) VALUES (3, 3, false);
INSERT INTO Reserva (id_reserva, id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES (1, 1, 'a', 'a', 1, current_date(), 0);
INSERT INTO Reserva (id_reserva, id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES (2, 2, 'q', 'q', 1, current_date(), 0);
INSERT INTO Reserva (id_reserva, id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES (3, 3, 's', 's', 1, current_date(), 0);

SELECT * FROM Taula;
SELECT * FROM Reserva;
SELECT * FROM Carta;
SELECT * FROM Comanda;
