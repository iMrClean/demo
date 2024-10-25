--liquibase formatted sql

--changeset author:1
CREATE TABLE ITEM
(
    ID   NUMBER(19, 0) NOT NULL,
    NAME VARCHAR2(100),
    CONSTRAINT ITEM$PK PRIMARY KEY (ID)
);