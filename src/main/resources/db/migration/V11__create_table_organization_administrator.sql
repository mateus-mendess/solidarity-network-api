CREATE TABLE organization_administrator(
    organization_id uuid,
    administrator_id uuid,
    PRIMARY KEY(organization_id, administrator_id),
    FOREIGN KEY (organization_id) REFERENCES organization(id),
    FOREIGN KEY (administrator_id) REFERENCES administrator(id)
);