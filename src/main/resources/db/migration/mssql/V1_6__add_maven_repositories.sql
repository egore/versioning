CREATE TABLE maven_repository (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  baseUrl varchar(511) NOT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_t5kskcf8s36fhp0lbk4eeh5qb FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_lmt9663jdnocvnabag1t3lmvj FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX mavenrepository__name ON maven_repository (name);
CREATE INDEX FK_lmt9663jdnocvnabag1t3lmvj ON maven_repository (id_creator);
CREATE INDEX FK_t5kskcf8s36fhp0lbk4eeh5qb ON maven_repository (id_modificator);
GO

ALTER TABLE project
  ADD id_mavenrepository int DEFAULT NULL,
  CONSTRAINT FK_e50f4sg1vtd5udecjy0b9bo1h FOREIGN KEY (id_mavenrepository) REFERENCES maven_repository (id);
CREATE INDEX FK_e50f4sg1vtd5udecjy0b9bo1h ON project (id_mavenrepository);
GO

INSERT INTO maven_repository(created, modified, baseUrl, name, id_creator, id_modificator)
  VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'http://central.maven.org/maven2/', 'Central Maven 2 repository', (SELECT id FROM [user] WHERE login = 'admin'), (SELECT id FROM [user] WHERE login = 'admin'));
GO