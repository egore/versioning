CREATE TABLE verification (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  group_id varchar(255) NOT NULL,
  artifact_id varchar(255) NOT NULL,
  version varchar(255) NOT NULL,
  packaging varchar(31) NULL,
  creator_id int DEFAULT NULL,
  modificator_id int DEFAULT NULL,
  KEY FK_verification_creator_IX (creator_id),
  KEY FK_verification_modificator_IX (modificator_id),
  PRIMARY KEY(id),
  CONSTRAINT FK_verification_creator FOREIGN KEY (creator_id) REFERENCES user_ (id),
  CONSTRAINT FK_verification_modificator FOREIGN KEY (modificator_id) REFERENCES user_ (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

