CREATE TABLE user_roles(
    user_id uuid,
    roles_id INT,
    PRIMARY KEY (user_id, roles_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (roles_id) REFERENCES roles(id)
);