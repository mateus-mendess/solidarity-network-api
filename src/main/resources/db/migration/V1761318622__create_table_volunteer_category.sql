CREATE TABLE volunteer_category(
    id_volunteer uuid,
    id_category INT,
    PRIMARY KEY (id_volunteer, id_category),
    FOREIGN KEY (id_volunteer) REFERENCES volunteer(id),
    FOREIGN KEY (id_category) REFERENCES category(id)
);