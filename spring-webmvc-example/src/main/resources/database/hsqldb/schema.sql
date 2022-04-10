CREATE TABLE pet (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(30),
    owner VARCHAR(60),
    species VARCHAR(90),
    sex CHAR(1),
    birth DATE,
    death DATE
);