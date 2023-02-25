create database diviction;
create table diviction.member(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    email VARCHAR(64) NOT NULL ,
    password VARCHAR(128) NOT NULL ,
    name VARCHAR(32) NOT NULL,
    birth VARCHAR(64) NOT NULL ,
    address VARCHAR(256) NOT NULL ,
    gender VARCHAR(32) NOT NULL ,
    profile_img_url VARCHAR(256) NOT NULL
);
create table diviction.counselor(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    email VARCHAR(64) NOT NULL ,
    password VARCHAR(128) NOT NULL ,
    name VARCHAR(32) NOT NULL,
    birth VARCHAR(64) NOT NULL ,
    address VARCHAR(256) NOT NULL ,
    gender VARCHAR(32) NOT NULL ,
    profile_img_url VARCHAR(256) NOT NULL,
    confirm BOOLEAN
);
