CREATE TABLE action_extraction (
  id SERIAL,
  created timestamp DEFAULT NULL,
  modified timestamp DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_cf88apbjes93ofghiibpuqhri FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_6jaekbvf4rmeqiw72qb4nxy8u FOREIGN KEY (id_modificator) REFERENCES "user" (id),
  CONSTRAINT FK_arcpfd4poo0dg5w2n5u9hpkv6 FOREIGN KEY (id_creator) REFERENCES "user" (id)
);
CREATE UNIQUE INDEX UK_cf88apbjes93ofghiibpuqhri ON action_extraction (id_project);
CREATE INDEX FK_arcpfd4poo0dg5w2n5u9hpkv6 ON action_extraction (id_creator);
CREATE INDEX FK_6jaekbvf4rmeqiw72qb4nxy8u ON action_extraction (id_modificator);
CREATE INDEX FK_cf88apbjes93ofghiibpuqhri ON action_extraction (id_project);

CREATE TABLE extraction (
  id SERIAL,
  created timestamp DEFAULT NULL,
  modified timestamp DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_action_extraction int NOT NULL,
  destination varchar(511) NOT NULL,
  source varchar(511) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_4an23gicaw2o4nt9yh8wyn6is FOREIGN KEY (id_creator) REFERENCES "user" (id),
  CONSTRAINT FK_flbq75id4pxa4bd5t56amfc5r FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id),
  CONSTRAINT FK_pmfb2q50t9clkgn88078vr7rd FOREIGN KEY (id_modificator) REFERENCES "user" (id)
);
CREATE INDEX FK_4an23gicaw2o4nt9yh8wyn6is ON extraction (id_creator);
CREATE INDEX FK_pmfb2q50t9clkgn88078vr7rd ON extraction (id_modificator);
CREATE INDEX FK_flbq75id4pxa4bd5t56amfc5r ON extraction (id_action_extraction);

DROP INDEX UK_fsio2r4kfwoupmiljkt308he5;
ALTER TABLE maven_artifact
  ADD id_action_extraction int NULL,
  ADD CONSTRAINT FK_kivt0mkhrkjmxgj9dy9ge2eqd FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);
ALTER TABLE maven_artifact
  ALTER COLUMN id_action_copy SET NOT NULL;
CREATE INDEX FK_kivt0mkhrkjmxgj9dy9ge2eqd ON maven_artifact (id_action_extraction);

DROP INDEX UK_fxjsjddujg8pqxhsgf5wssyfi;
ALTER TABLE spacer_url
  ADD id_action_extraction int NULL,
  ADD CONSTRAINT FK_qrup4t5c0gjextscs4fuj63ap FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);
ALTER TABLE spacer_url
  ALTER COLUMN id_action_copy SET NOT NULL;
CREATE INDEX FK_qrup4t5c0gjextscs4fuj63ap ON spacer_url (id_action_extraction);
