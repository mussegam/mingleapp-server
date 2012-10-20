# Linkeduser schema

# --- !Ups

CREATE TABLE linkedusers (
    linkedId bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    headline varchar(255),
    picture varchar(255),
    numConnections int(5),
    lastPosition varchar(255),
    PRIMARY KEY (linkedId)
);

# --- !Downs

DROP TABLE linkedusers;