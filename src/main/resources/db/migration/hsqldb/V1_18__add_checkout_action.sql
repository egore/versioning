CREATE TABLE action_checkout (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  target_path varchar(511) DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  CONSTRAINT FK_3ayfyv259wcvhkc84agqvv5re FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_g3i7qqay42knvympj1uwpv1oq FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_p1409no53tmf3rhpwreqoko0w FOREIGN KEY (id_modificator) REFERENCES user (id)
);

ALTER TABLE server
  ADD COLUMN target_path varchar(511) DEFAULT NULL;
ALTER TABLE server
  ADD COLUMN vcsPath varchar(255) DEFAULT NULL;
ALTER TABLE server
  ADD COLUMN id_vcshost int DEFAULT NULL;
ALTER TABLE server
  ADD CONSTRAINT FK_ah4nxyxki48jwhblm1narn061 FOREIGN KEY (id_vcshost) REFERENCES vcshost (id);
