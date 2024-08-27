CREATE TABLE IF NOT EXISTS book_reservation
(
    uuid UUID NOT NULL,
    book_uuid UUID NOT NULL,
    user_uuid UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_received BOOLEAN NOT NULL DEFAULT FALSE,
    is_returned BOOLEAN NOT NULL DEFAULT FALSE,
    is_cancelled BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (uuid)
);