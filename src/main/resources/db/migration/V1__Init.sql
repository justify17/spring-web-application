CREATE TABLE roles(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role_id INT NOT NULL,
    account_non_locked boolean DEFAULT TRUE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES ticket_office.roles(id)
);

CREATE TABLE user_information(
	user_id INT PRIMARY KEY,
    first_name VARCHAR(30),
    surname VARCHAR(30),
    phone_number VARCHAR(20),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES ticket_office.users(id)
);

CREATE TABLE carriages(
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(30) NOT NULL,
    comfort_level VARCHAR(30),
    seats INT
);

CREATE TABLE trains(
	id INT PRIMARY KEY AUTO_INCREMENT,
	number VARCHAR(30) NOT NULL,
	type VARCHAR(30) NOT NULL
);

CREATE TABLE trains_carriages(
    id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    carriage_id INT,
    train_carriage_number INT,
    CONSTRAINT fk_train FOREIGN KEY (train_id) REFERENCES ticket_office.trains(id),
    CONSTRAINT fk_carriage FOREIGN KEY (carriage_id) REFERENCES ticket_office.carriages(id)
);

CREATE TABLE stations(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE routes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(30) NOT NULL
);

CREATE TABLE routes_stations(
    id INT PRIMARY KEY AUTO_INCREMENT,
    route_id INT,
    station_id INT,
    route_stop_number INT,
    price_to_next_station INT,
    minutes_to_next_station INT,
    stop_minutes INT,
    CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES ticket_office.routes(id),
    CONSTRAINT fk_station FOREIGN KEY (station_id) REFERENCES ticket_office.stations(id)
);

CREATE TABLE departures(
    id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    route_id INT,
    departure_date DATETIME NOT NULL,
    CONSTRAINT fk_train_1 FOREIGN KEY (train_id) REFERENCES ticket_office.trains(id),
    CONSTRAINT fk_route_1 FOREIGN KEY (route_id) REFERENCES ticket_office.routes(id)
);

CREATE TABLE tickets(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    departure_id INT,
    user_departure_station_id INT,
    user_departure_date DATETIME NOT NULL,
    user_arrival_station_id INT,
    user_arrival_date DATETIME NOT NULL,
    carriage_number INT,
    seat_number INT,
    price INT NOT NULL,
    CONSTRAINT fk_user_1 FOREIGN KEY (user_id) REFERENCES ticket_office.users(id),
    CONSTRAINT fk_departure FOREIGN KEY (departure_id) REFERENCES ticket_office.departures(id),
    CONSTRAINT fk_user_departure_station FOREIGN KEY (user_departure_station_id) REFERENCES ticket_office.stations(id),
    CONSTRAINT fk_user_arrival_station FOREIGN KEY (user_arrival_station_id) REFERENCES ticket_office.stations(id)
);