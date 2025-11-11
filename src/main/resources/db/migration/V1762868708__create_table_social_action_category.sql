CREATE TABLE social_action_category(
    social_action_id uuid NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY(social_action_id, category_id),
    FOREIGN KEY (social_action_id) REFERENCES social_action(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);