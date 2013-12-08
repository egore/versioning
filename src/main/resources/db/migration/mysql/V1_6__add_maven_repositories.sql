CREATE TABLE maven_repository (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  baseUrl varchar(511) NOT NULL,
  name varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY mavenrepository__name (name),
  KEY FK_lmt9663jdnocvnabag1t3lmvj (id_creator),
  KEY FK_t5kskcf8s36fhp0lbk4eeh5qb (id_modificator),
  CONSTRAINT FK_t5kskcf8s36fhp0lbk4eeh5qb FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_lmt9663jdnocvnabag1t3lmvj FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE project
  ADD COLUMN id_mavenrepository int(11) DEFAULT NULL,
  ADD KEY FK_e50f4sg1vtd5udecjy0b9bo1h (id_mavenrepository),
  ADD CONSTRAINT FK_e50f4sg1vtd5udecjy0b9bo1h FOREIGN KEY (id_mavenrepository) REFERENCES maven_repository (id);

INSERT INTO maven_repository(created, modified, baseUrl, name, id_creator, id_modificator)
  VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'http://central.maven.org/maven2/', 'Central Maven 2 repository', (SELECT id FROM user WHERE login = 'admin'), (SELECT id FROM user WHERE login = 'admin'));