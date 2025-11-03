CREATE TABLE administrator(
    id uuid PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    birthday DATE NOT NULL,
    gender VARCHAR(15) NOT NULL,
    organization_id uuid NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);