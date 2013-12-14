CREATE TABLE action_replacement (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_project int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_1pi028gl1wyh0yoks579jsqm9 (id_creator),
  KEY FK_g6f9y8ycrlxc87umkbmgj3mjs (id_modificator),
  KEY FK_ddv0pub3gmvuw995v33215ksv (id_project),
  CONSTRAINT FK_ddv0pub3gmvuw995v33215ksv FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_1pi028gl1wyh0yoks579jsqm9 FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_g6f9y8ycrlxc87umkbmgj3mjs FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE replacement (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(511) NOT NULL,
  variable varchar(511) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_action_replace int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_4w9afvf4ug3n5pkkp3i62jtti (id_creator),
  KEY FK_6inj005dkwa7j73l8n68im91v (id_modificator),
  KEY FK_ayua8296uycqj1vc4lbefcwul (id_action_replace),
  CONSTRAINT FK_ayua8296uycqj1vc4lbefcwul FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_4w9afvf4ug3n5pkkp3i62jtti FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_6inj005dkwa7j73l8n68im91v FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE replacementfile (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(511) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_action_replace int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_bm3r05tbvyjvpybjy9b42effs (id_creator),
  KEY FK_1a6d2w8g491grcofcol161o12 (id_modificator),
  KEY FK_kqsq6f721qgjugycd3swu1m7y (id_action_replace),
  CONSTRAINT FK_kqsq6f721qgjugycd3swu1m7y FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_1a6d2w8g491grcofcol161o12 FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_bm3r05tbvyjvpybjy9b42effs FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE wildcard (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(254) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_action_replace int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_pmkypt4g974o5xr12y8rrr5vc (id_creator),
  KEY FK_7qjedol9oh68ksf0ww7yj530g (id_modificator),
  KEY FK_mb00n2xoy2xikhh1s47040j7o (id_action_replace),
  CONSTRAINT FK_mb00n2xoy2xikhh1s47040j7o FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_7qjedol9oh68ksf0ww7yj530g FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_pmkypt4g974o5xr12y8rrr5vc FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
