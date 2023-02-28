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
create table diviction.matching(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    patient_id BIGINT NOT NULL,
    counselor_id BIGINT NOT NULL ,
    state VARCHAR(32) NOT NULL ,
    FOREIGN KEY(patient_id) REFERENCES diviction.member(id),
    FOREIGN KEY(counselor_id) REFERENCES diviction.counselor(id)
);
create table diviction.diagnosis_result(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    FOREIGN KEY(user_id) REFERENCES diviction.member(id),
    date VARCHAR(32) NOT NULL ,
    vP1 BIGINT NOT NULL ,
    vP2 BIGINT NOT NULL ,
    vP3 BIGINT NOT NULL
);
create table diviction.consulting(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    patient_id BIGINT NOT NULL ,
    foreign key(patient_id) REFERENCES diviction.member(id),
    counselor_id BIGINT NOT NULL ,
    foreign key (counselor_id) REFERENCES diviction.counselor(id),
    content VARCHAR(2048) NOT NULL ,
    date VARCHAR(32) NOT NULL
);
create table diviction.checklist(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    foreign key (user_id) REFERENCES diviction.member(id),
    start_date VARCHAR(32) NOT NULL ,
    end_date VARCHAR(32) NOT NULL ,
    content VARCHAR(2048) NOT NULL ,
    state VARCHAR(32) NOT NULL
);
