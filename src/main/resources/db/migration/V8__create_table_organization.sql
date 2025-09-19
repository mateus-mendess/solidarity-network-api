CREATE TABLE organization(
    id uuid PRIMARY KEY REFERENCES users(id),
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    corporate_name VARCHAR(70) NOT NULL,
    about TEXT NOT NULL,
    phone VARCHAR(16) NOT NULL UNIQUE,
    profile_photo VARCHAR(255),
    cover_photo VARCHAR(255),
    website_url VARCHAR(255),
    verified BOOLEAN DEFAULT FALSE
);