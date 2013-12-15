CREATE TABLE action_checkout (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  target_path varchar(511) DEFAULT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_project int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_g3i7qqay42knvympj1uwpv1oq (id_creator),
  KEY FK_p1409no53tmf3rhpwreqoko0w (id_modificator),
  KEY FK_3ayfyv259wcvhkc84agqvv5re (id_project),
  CONSTRAINT FK_3ayfyv259wcvhkc84agqvv5re FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_g3i7qqay42knvympj1uwpv1oq FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_p1409no53tmf3rhpwreqoko0w FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE server
  ADD COLUMN target_path varchar(511) DEFAULT NULL,
  ADD COLUMN vcsPath varchar(255) DEFAULT NULL,
  ADD COLUMN id_vcshost int(11) DEFAULT NULL,
  ADD KEY FK_ah4nxyxki48jwhblm1narn061 (id_vcshost),
  ADD CONSTRAINT FK_ah4nxyxki48jwhblm1narn061 FOREIGN KEY (id_vcshost) REFERENCES vcshost (id);