# Linkeduser schema

# --- !Ups

CREATE SEQUENCE linkedusers_id_seq;
CREATE TABLE linkedusers (
    id bigint(20) NOT NULL DEFAULT nextval('linkedusers_id_seq'),
    linkedId varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    headline varchar(255),
    picture varchar(255),
    numConnections int(5),
    lastPosition varchar(255),
    PRIMARY KEY (linkedId)
);

# --- !Downs

DROP TABLE linkedusers;
DROP SEQUENCE linkedusers_id_seq;