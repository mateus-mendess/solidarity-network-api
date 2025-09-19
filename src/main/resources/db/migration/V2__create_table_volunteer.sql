CREATE TABLE volunteer(
    id uuid PRIMARY KEY REFERENCES users(id),
    cpf VARCHAR(11) UNIQUE NOT NULL,
    name VARCHAR(30) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    birthday DATE NOT NULL,
    gender gender_type NOT NULL,
    profile_photo BYTEA,
    work VARCHAR(100) NOT NULL,
    authorized BOOLEAN NOT NULL
);
