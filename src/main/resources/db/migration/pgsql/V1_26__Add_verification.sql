CREATE TABLE verification (
  id SERIAL,
  created timestamp DEFAULT NULL,
  modified timestamp DEFAULT NULL,
  group_id varchar(255) NOT NULL,
  artifact_id varchar(255) NOT NULL,
  version varchar(255) NOT NULL,
  packaging varchar(31) NULL,
  creator_id int DEFAULT NULL,
  modificator_id int DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_verification_creator FOREIGN KEY (creator_id) REFERENCES user_ (id),
  CONSTRAINT FK_verification_modificator FOREIGN KEY (modificator_id) REFERENCES user_ (id)
);

CREATE INDEX FK_verification_creator_IX ON verification(creator_id);
CREATE INDEX FK_verification_modificator_IX ON verification(modificator_id);
