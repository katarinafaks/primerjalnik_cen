INSERT INTO kategorija (ime, opis) VALUES ('Pisala', 'Kemični svinčniki in nalivna peresa.');
INSERT INTO kategorija (ime, opis) VALUES ('Mlecni izdelki', 'Vsebujejo laktozo.');
INSERT INTO trgovina(ime) VALUES('Lidl')
INSERT INTO trgovina (ime) VALUES ('Merkator')
INSERT INTO kosarica (ime) VALUES ('Prva Kosarica')
INSERT INTO izdelek (ime, opis, cena,kategorija_id,trgovina_id,kosarica_id) VALUES ('Nalivno pero', 'Kakovostno nalivno pero zelene barve.', 10,1,2,1);
INSERT INTO izdelek (ime, opis, cena,kategorija_id,trgovina_id,kosarica_id) VALUES ('Crnilo', 'Nalivno crnilo modre barve.', 5,1,1,1);