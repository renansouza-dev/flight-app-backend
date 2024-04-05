INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'TAP', 'OWN', 42.76, 'OPO', 'LIS', '2024-05-09T07:00:00.000Z', '2024-05-09T07:50:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'TAP', 'OWN', 332.52, 'LIS', 'GRU', '2024-05-09T12:15:00.000Z', '2024-05-09T18:35:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'TAP', 'OWN', 472.33, 'OPO', 'GRU', '2024-05-09T12:30:00.000Z', '2024-05-09T19:10:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'TAP', 'OWN', 455.21, 'GRU', 'OPO', '2024-05-23T20:45:00.000Z', '2024-05-24T10:45:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'AZUL', 'OWN', 723.33, 'VCP', 'LIS', '2024-05-09T18:30:00.000Z', '2024-05-10T08:00:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'AZUL', 'OWN', 723.33, 'VCP', 'LIS', '2024-05-09T22:25:00.000Z', '2024-05-10T11:55:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'AZUL', 'OWN', 548.80, 'LIS', 'VCP', '2024-05-23T11:00:00.000Z', '2024-05-24T17:20:00.000Z', false);
INSERT INTO public.flights
(id, airline, supplier, fare, departure_airport, arrival_airport, departure_time, arrival_time, deleted)
VALUES(gen_random_uuid () ::uuid, 'AZUL', 'OWN', 548.80, 'LIS', 'VCP', '2024-05-23T13:00:00.000Z', '2024-05-24T19:20:00.000Z', false);