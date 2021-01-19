INSERT INTO user (id, email, company_name, password) VALUES (1, "email1@gmail.com", "company1", "$2a$10$BPAZbYxTyiggL2X81SJ1uOg4DFOVvVwajuXLkVnzq5hFE1MO6VNFi");
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (1, 1, "AA-11-AA", "BMW", "Diesel", "M4", 5, 5, "Manual", 2017, 1);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 1);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 1);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 1);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 1);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-4 10:50:00", 0.1, 0.94, "50 50", 10, 25, 0.99, 11, 1);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-4 11:50:00", 0.1, 0.94, "50 50", 10, 25, 0.99, 11, 2);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-4 12:50:00", 0.1, 0.94, "50 50", 10, 25, 0.99, 11, 3);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-5 10:50:00", 0.1, 0.94, "50 50", 10, 25, 0.99, 11, 4);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 11:50:00", 0, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 12:50:00", 1, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 13:50:00", 0, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 14:50:00", 1, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-5 10:50:00", 0, 1);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-6 10:50:00", 1, 1);

INSERT INTO user (id, email, company_name, password) VALUES (2, "email2@gmail.com", "company2", "$2a$10$BPAZbYxTyiggL2X81SJ1uOg4DFOVvVwajuXLkVnzq5hFE1MO6VNFi");
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (2, 2, "BB-22-BB", "Renault", "Diesel", "Megane", 5, 5, "Manual", 2015, 2);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 2);
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (3, 3, "CC-22-CC", "Alfa Romeo", "Diesel", "Stelvio", 5, 5, "Manual", 2015, 2);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 3);
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (4, 4, "DD-22-DD", "Alfa Romeo", "Diesel", "Giulia", 5, 5, "Manual", 2015, 2);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 4);
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (5, 4, "EE-22-EE", "Alfa Romeo", "Diesel", "Giulia", 5, 5, "Manual", 2015, 2);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 5);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 2);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 5);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-6 10:50:00", 0.1, 0.94, "50 50", 10, 25, 0.99, 11, 5);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-4 12:35:00", 0.12, 0.97, "51 51", 14, 27, 1, 15, 6);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (112, "2020-12-4 12:35:15", 0.12, 0.97, "52 52", 20, 35, 1, 15, 6);

INSERT INTO user (id, email, company_name, password) VALUES (3, "teste@gmail.com", "companytest", "$2a$10$BPAZbYxTyiggL2X81SJ1uOg4DFOVvVwajuXLkVnzq5hFE1MO6VNFi");
INSERT INTO car (id, sensor_id, license_plate, brand, fuel, model, number_of_doors, number_of_seats, transmission, year, user_id) VALUES (6, 2, "66-ZZ-66", "Honda", "Diesel", "NSX", 3, 2, "Manual", 2015, 3);
INSERT INTO status_history (date, status, car_id) VALUES ("2020-12-4 10:50:00", 1, 6);
INSERT INTO anomaly (viewed, classification, car_id) VALUES (false, "smoke", 6);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (106.94837165236385, "2020-12-13 12:33:25", 54636, 67.76241096252281, "41.5608 -8.3968", 9.4, 4.0, 1000.4701618115755, 22.978046875, 7);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (106.25184206206909, "2020-12-13 12:33:30", 54267, 67.90405621619276, "41.5608 -8.3968", 9.5, 3.9, 1000.5529695202706, 23.010859375, 7);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (104.73167477902417, "2020-12-13 12:33:42", 53062, 67.76106163728036, "41.5608 -8.3968", 6.5, 3.6, 1000.733715601405, 23.0985546875, 7);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (104.27883149472028, "2020-12-13 12:33:48", 53641, 67.22982582213493, "41.5608 -8.3968", 6.1, 3.5, 1000.7875632395404, 23.1423046875, 7);
INSERT INTO measurement (altitude, date, gas, humidity, car_location, pm10, pm25, pressure, temperature, anomaly_id) VALUES (103.74879482370577, "2020-12-13 12:33:53", 54602, 65.90775666613479, "41.5608 -8.3968", 6.1, 3.5, 1000.8505929306501, 23.1895703125, 7);