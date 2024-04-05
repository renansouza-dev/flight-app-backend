CREATE TABLE flights (
  id UUID NOT NULL,
   airline VARCHAR(85) NOT NULL,
   supplier VARCHAR(85) NOT NULL,
   fare DECIMAL NOT NULL,
   departure_airport VARCHAR(3) NOT NULL,
   arrival_airport VARCHAR(3) NOT NULL,
   departure_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   arrival_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_flights PRIMARY KEY (id)
);

ALTER TABLE flights ADD CONSTRAINT uc_flightsentity_airline UNIQUE (airline, departure_airport, arrival_airport, departure_time, arrival_time);

CREATE UNIQUE INDEX idx_flightsentity_airline_unq ON flights(airline, departure_airport, arrival_airport, departure_time, arrival_time);