--liquibase formatted sql

--changeset anderson:v001
CREATE TABLE test.purchase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_amount DECIMAL(10, 2) NOT NULL,
    purchase_date TIMESTAMP NOT NULL,
    description VARCHAR(50) NOT NULL,
);