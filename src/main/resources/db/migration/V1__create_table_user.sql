CREATE TABLE users(
    id uuid PRIMARY KEY,
    email VARCHAR(175) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);