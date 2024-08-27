CREATE TABLE IF NOT EXISTS book
(
    uuid UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    user_uuid UUID NOT NULL,
    PRIMARY KEY (uuid)
);