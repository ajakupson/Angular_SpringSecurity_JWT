CREATE TABLE IF NOT EXISTS users
(
    uuid UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    UNIQUE(email),
    PRIMARY KEY (uuid)
);