CREATE TABLE action_replacement (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  CONSTRAINT FK_ddv0pub3gmvuw995v33215ksv FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_1pi028gl1wyh0yoks579jsqm9 FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_g6f9y8ycrlxc87umkbmgj3mjs FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE replacement (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(511) NOT NULL,
  variable varchar(511) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_action_replace int NOT NULL,
  CONSTRAINT FK_ayua8296uycqj1vc4lbefcwul FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_4w9afvf4ug3n5pkkp3i62jtti FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_6inj005dkwa7j73l8n68im91v FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE replacementfile (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(511) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_action_replace int NOT NULL,
  CONSTRAINT FK_kqsq6f721qgjugycd3swu1m7y FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_1a6d2w8g491grcofcol161o12 FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_bm3r05tbvyjvpybjy9b42effs FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE wildcard (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  value varchar(254) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_action_replace int NOT NULL,
  CONSTRAINT FK_mb00n2xoy2xikhh1s47040j7o FOREIGN KEY (id_action_replace) REFERENCES action_replacement (id),
  CONSTRAINT FK_7qjedol9oh68ksf0ww7yj530g FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_pmkypt4g974o5xr12y8rrr5vc FOREIGN KEY (id_creator) REFERENCES user (id)
);
