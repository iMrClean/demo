--liquibase formatted sql

--changeset author:1
CREATE TABLE PRIMARY_ITEM
(
    ID   NUMBER(19, 0) NOT NULL,
    NAME VARCHAR2(100),
    CONSTRAINT PRIMARY_ITEM$PK PRIMARY KEY (ID)
);