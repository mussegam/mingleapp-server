# Minor classes schema

# --- !Ups

CREATE TABLE groups (
    linkedId bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (linkedId)
);

CREATE TABLE skills (
    linkedId bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (linkedId)
);

CREATE TABLE newssources (
    linkedId bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (linkedId)
);

# --- !Downs

DROP TABLE groups;
DROP TABLE skills;
DROP TABLE newssources;