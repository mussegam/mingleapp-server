# Relationships schema

# --- !Ups

CREATE TABLE groups_users (
    group_id bigint(20) NOT NULL,
    linkeduser_id bigint(20) NOT NULL,
    PRIMARY KEY (group_id, linkeduser_id),
    FOREIGN KEY (group_id) REFERENCES groups(linkedId) ON DELETE CASCADE,
    FOREIGN KEY (linkeduser_id) REFERENCES linkedusers(id)
);

CREATE TABLE skills_users (
    skill_id bigint(20) NOT NULL,
    linkeduser_id bigint(20) NOT NULL,
    PRIMARY KEY (skill_id, linkeduser_id),
    FOREIGN KEY (skill_id) REFERENCES skills(linkedId) ON DELETE CASCADE,
    FOREIGN KEY (linkeduser_id) REFERENCES linkedusers(id)
);

CREATE TABLE newssources_users (
    news_id bigint(20) NOT NULL,
    linkeduser_id bigint(20) NOT NULL,
    PRIMARY KEY (news_id, linkeduser_id),
    FOREIGN KEY (news_id) REFERENCES newssources(linkedId) ON DELETE CASCADE,
    FOREIGN KEY (linkeduser_id) REFERENCES linkedusers(id)
);

# --- !Downs

DROP TABLE groups_users;
DROP TABLE skills_users;
DROP TABLE newssources_users;