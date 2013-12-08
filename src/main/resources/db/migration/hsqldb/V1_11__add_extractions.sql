CREATE TABLE action_extraction (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  UNIQUE (id_project),
  CONSTRAINT FK_cf88apbjes93ofghiibpuqhri FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_6jaekbvf4rmeqiw72qb4nxy8u FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_arcpfd4poo0dg5w2n5u9hpkv6 FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE extraction (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_action_extraction int NOT NULL,
  destination varchar(511) NOT NULL,
  source varchar(511) NOT NULL,
  CONSTRAINT FK_4an23gicaw2o4nt9yh8wyn6is FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_flbq75id4pxa4bd5t56amfc5r FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id),
  CONSTRAINT FK_pmfb2q50t9clkgn88078vr7rd FOREIGN KEY (id_modificator) REFERENCES user (id)
);

ALTER TABLE maven_artifact
  ADD COLUMN id_action_extraction int NULL;
ALTER TABLE maven_artifact
  ALTER COLUMN id_action_copy SET NULL;
ALTER TABLE maven_artifact
  ADD CONSTRAINT FK_kivt0mkhrkjmxgj9dy9ge2eqd FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);

ALTER TABLE spacer_url
  ADD COLUMN id_action_extraction int NULL;
ALTER TABLE spacer_url
  ALTER COLUMN id_action_copy SET NULL;
ALTER TABLE spacer_url
  ADD CONSTRAINT FK_qrup4t5c0gjextscs4fuj63ap FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);