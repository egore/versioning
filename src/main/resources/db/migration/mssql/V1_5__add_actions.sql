CREATE TABLE action_war (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  CONSTRAINT FK_75lbybuby84oea2dcvwqp8h9m FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_c9ikewxvig5ox6h39ygt61uix FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_snkn24tbbhuptul0qn47joc0u FOREIGN KEY (id_modificator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX UK_75lbybuby84oea2dcvwqp8h9m ON action_war (id_project);
CREATE INDEX FK_c9ikewxvig5ox6h39ygt61uix ON action_war (id_creator);
CREATE INDEX FK_snkn24tbbhuptul0qn47joc0u ON action_war (id_modificator);
CREATE INDEX FK_75lbybuby84oea2dcvwqp8h9m ON action_war (id_project);
GO

CREATE TABLE spacer_url (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  url varchar(1023) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_war int NOT NULL,
  CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi FOREIGN KEY (id_war) REFERENCES action_war (id),
  CONSTRAINT FK_9hfj0ob6d1hm9ltwcn9lon8nk FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_cuyp9ox5j5dr97utg6orw1a6s FOREIGN KEY (id_modificator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX UK_fxjsjddujg8pqxhsgf5wssyfi ON spacer_url (id_war);
CREATE INDEX FK_9hfj0ob6d1hm9ltwcn9lon8nk ON spacer_url (id_creator);
CREATE INDEX FK_cuyp9ox5j5dr97utg6orw1a6s ON spacer_url (id_modificator);
CREATE INDEX FK_fxjsjddujg8pqxhsgf5wssyfi ON spacer_url (id_war);
GO

CREATE TABLE maven_artifact (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  artifact_id varchar(255) NOT NULL,
  group_id varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_war int NOT NULL,
  CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5 FOREIGN KEY (id_war) REFERENCES action_war (id),
  CONSTRAINT FK_45ng6br7mfdu7sol4wrmogokx FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_s65xxiiur77bktlq2417dp9lo FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX UK_fsio2r4kfwoupmiljkt308he5 ON maven_artifact (id_war);
CREATE INDEX FK_s65xxiiur77bktlq2417dp9lo ON maven_artifact (id_creator);
CREATE INDEX FK_45ng6br7mfdu7sol4wrmogokx ON maven_artifact (id_modificator);
CREATE INDEX FK_fsio2r4kfwoupmiljkt308he5 ON maven_artifact (id_war);
GO