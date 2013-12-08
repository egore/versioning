CREATE TABLE tag_transformer (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  replacementPattern varchar(511) NOT NULL,
  searchPattern varchar(511) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_ogolh0xa400fgt3vwth79d7kb FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_8qakfw6l96aue0s2ncpbnxu0a FOREIGN KEY (id_creator) REFERENCES user (id)
);

ALTER TABLE project
  ADD COLUMN id_tagtransformer int DEFAULT NULL;

ALTER TABLE project
  ADD CONSTRAINT FK_97ssoqcyb71qfb3nh52au6g9e FOREIGN KEY (id_tagtransformer) REFERENCES tag_transformer (id);