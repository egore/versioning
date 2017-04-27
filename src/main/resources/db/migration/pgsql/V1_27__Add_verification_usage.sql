ALTER TABLE verification
  DROP COLUMN packaging;

CREATE TABLE used_artifact (
  id SERIAL,
  created timestamp DEFAULT NULL,
  modified timestamp DEFAULT NULL,
  group_id varchar(255) NOT NULL,
  artifact_id varchar(255) NOT NULL,
  version varchar(255) NOT NULL,
  packaging varchar(31) NULL,
  verification_id int NOT NULL,
  last_seen timestamp DEFAULT NULL,
  creator_id int DEFAULT NULL,
  modificator_id int DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_usedartifact_verification FOREIGN KEY (verification_id) REFERENCES verification (id),
  CONSTRAINT FK_usedartifact_creator FOREIGN KEY (creator_id) REFERENCES user_ (id),
  CONSTRAINT FK_usedartifact_modificator FOREIGN KEY (modificator_id) REFERENCES user_ (id)
);

CREATE INDEX FK_usedartifact_verification_IX ON used_artifact(verification_id);
CREATE INDEX FK_usedartifact_creator_IX ON used_artifact(creator_id);
CREATE INDEX FK_usedartifact_modificator_IX ON used_artifact(modificator_id);
