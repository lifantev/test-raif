CREATE TABLE IF NOT EXISTS sock
(
    id BIGINT PRIMARY KEY NOT NULL,
    color VARCHAR(400) NOT NULL,
    cotton_part INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE INDEX sock_cotton_part_idx ON sock(cotton_part);