CREATE TABLE social_action(
    id uuid PRIMARY KEY,
    title VARCHAR(70) NOT NULL,
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    volunteers_limit INTEGER NOT NULL,
    volunteers_registered INTEGER DEFAULT 0,
    image VARCHAR(255),
    visibility VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    organization_id uuid NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);