CREATE TABLE action_checkout (
  id SERIAL,
  created timestamp DEFAULT NULL,
  modified timestamp DEFAULT NULL,
  target_path varchar(511) DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_3ayfyv259wcvhkc84agqvv5re FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_g3i7qqay42knvympj1uwpv1oq FOREIGN KEY (id_creator) REFERENCES "user" (id),
  CONSTRAINT FK_p1409no53tmf3rhpwreqoko0w FOREIGN KEY (id_modificator) REFERENCES "user" (id)
);
CREATE INDEX FK_g3i7qqay42knvympj1uwpv1oq ON action_checkout (id_creator);
CREATE INDEX FK_p1409no53tmf3rhpwreqoko0w ON action_checkout (id_modificator);
CREATE INDEX FK_3ayfyv259wcvhkc84agqvv5re ON action_checkout (id_project);

ALTER TABLE server
  ADD target_path varchar(511) DEFAULT NULL,
  ADD vcsPath varchar(255) DEFAULT NULL,
  ADD id_vcshost int DEFAULT NULL,
  ADD CONSTRAINT FK_ah4nxyxki48jwhblm1narn061 FOREIGN KEY (id_vcshost) REFERENCES vcshost (id);
CREATE INDEX FK_ah4nxyxki48jwhblm1narn061 ON server (id_vcshost);
