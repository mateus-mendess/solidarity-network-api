CREATE TABLE address(
    id SERIAL PRIMARY KEY,
    postal_code VARCHAR(30) NOT NULL,
    neighborhood VARCHAR(70) NOT NULL,
    street VARCHAR(70) NOT NULL,
    state CHAR(2) NOT NULL,
    city VARCHAR(40) NOT NULL,
    latitude NUMERIC NOT NULL,
    longitude NUMERIC NOT NULL,
    ong_id uuid,
    FOREIGN KEY (ong_id) REFERENCES organization(id)
);