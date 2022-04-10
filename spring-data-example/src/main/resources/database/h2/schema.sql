CREATE TABLE pet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    owner VARCHAR(20),
    species VARCHAR(20),
    sex CHAR(1),
    birth DATE,
    death DATE
);

CREATE TABLE person (
    id BIGINT PRIMARY KEY,
    name VARCHAR(20),
    gender CHAR(1)
);