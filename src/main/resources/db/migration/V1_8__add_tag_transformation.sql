CREATE TABLE tag_transformer (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  replacementPattern varchar(511) NOT NULL,
  searchPattern varchar(511) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_8qakfw6l96aue0s2ncpbnxu0a (id_creator),
  KEY FK_ogolh0xa400fgt3vwth79d7kb (id_modificator),
  CONSTRAINT FK_ogolh0xa400fgt3vwth79d7kb FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_8qakfw6l96aue0s2ncpbnxu0a FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE project
  ADD COLUMN id_tagtransformer int(11) DEFAULT NULL,
  ADD KEY FK_97ssoqcyb71qfb3nh52au6g9e (id_tagtransformer),
  ADD CONSTRAINT FK_97ssoqcyb71qfb3nh52au6g9e FOREIGN KEY (id_tagtransformer) REFERENCES tag_transformer (id);