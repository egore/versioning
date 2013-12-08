CREATE TABLE action_war (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  UNIQUE (id_project),
  CONSTRAINT FK_75lbybuby84oea2dcvwqp8h9m FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_c9ikewxvig5ox6h39ygt61uix FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_snkn24tbbhuptul0qn47joc0u FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE spacer_url (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  url varchar(1023) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_war int NOT NULL,
  UNIQUE (id_war),
  CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi FOREIGN KEY (id_war) REFERENCES action_war (id),
  CONSTRAINT FK_9hfj0ob6d1hm9ltwcn9lon8nk FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_cuyp9ox5j5dr97utg6orw1a6s FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE maven_artifact (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  artifact_id varchar(255) NOT NULL,
  group_id varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_war int NOT NULL,
  UNIQUE (id_war),
  CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5 FOREIGN KEY (id_war) REFERENCES action_war (id),
  CONSTRAINT FK_45ng6br7mfdu7sol4wrmogokx FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_s65xxiiur77bktlq2417dp9lo FOREIGN KEY (id_creator) REFERENCES user (id)
);