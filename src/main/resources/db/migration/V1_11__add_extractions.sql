CREATE TABLE action_extraction (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_project int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_cf88apbjes93ofghiibpuqhri (id_project),
  KEY FK_arcpfd4poo0dg5w2n5u9hpkv6 (id_creator),
  KEY FK_6jaekbvf4rmeqiw72qb4nxy8u (id_modificator),
  KEY FK_cf88apbjes93ofghiibpuqhri (id_project),
  CONSTRAINT FK_cf88apbjes93ofghiibpuqhri FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_6jaekbvf4rmeqiw72qb4nxy8u FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_arcpfd4poo0dg5w2n5u9hpkv6 FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE extraction (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_action_extraction int(11) NOT NULL,
  destination varchar(511) NOT NULL,
  source varchar(511) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_4an23gicaw2o4nt9yh8wyn6is (id_creator),
  KEY FK_pmfb2q50t9clkgn88078vr7rd (id_modificator),
  KEY FK_flbq75id4pxa4bd5t56amfc5r (id_action_extraction),
  CONSTRAINT FK_4an23gicaw2o4nt9yh8wyn6is FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_flbq75id4pxa4bd5t56amfc5r FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id),
  CONSTRAINT FK_pmfb2q50t9clkgn88078vr7rd FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE maven_artifact
  ADD COLUMN id_action_extraction int(11) NULL,
  CHANGE COLUMN id_action_copy id_action_copy int(11) NULL,
  DROP KEY UK_fsio2r4kfwoupmiljkt308he5,
  ADD KEY FK_kivt0mkhrkjmxgj9dy9ge2eqd (id_action_extraction),
  ADD CONSTRAINT FK_kivt0mkhrkjmxgj9dy9ge2eqd FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);

ALTER TABLE spacer_url
  ADD COLUMN id_action_extraction int(11) NULL,
  CHANGE COLUMN id_action_copy id_action_copy int(11) NULL,
  DROP KEY UK_fxjsjddujg8pqxhsgf5wssyfi,
  ADD KEY FK_qrup4t5c0gjextscs4fuj63ap (id_action_extraction),
  ADD CONSTRAINT FK_qrup4t5c0gjextscs4fuj63ap FOREIGN KEY (id_action_extraction) REFERENCES action_extraction (id);