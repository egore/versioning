CREATE TABLE user (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_kdphgulfr5qyj8yorpt532e6a FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_s2jrcrakotyc6ej7v3i0ohp5u FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE role (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_8e4noh44dhdi0ulf0d58wvslo FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_jkqu5ixcuhlvgr3jkxnjktkr FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE role_permissions (
  id_role int NOT NULL,
  permission varchar(255) DEFAULT NULL,
  CONSTRAINT FK_jblsl6vi1sgpwh79ik1jkp8gm FOREIGN KEY (id_role) REFERENCES role (id)
);

CREATE TABLE user_role (
  id_user int NOT NULL,
  id_role int NOT NULL,
  CONSTRAINT FK_56olsq329osn3lxem8ftn9q9h FOREIGN KEY (id_role) REFERENCES role (id),
  CONSTRAINT FK_qibq3rh7mo4f8372yxkn549j7 FOREIGN KEY (id_user) REFERENCES user (id)
);

CREATE TABLE vcshost (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  uri varchar(255) NOT NULL,
  vcs varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  UNIQUE (name),
  CONSTRAINT FK_rh6kawdkugkj9tut91jmi8tck FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_tivndyh6ivgvvt69cu8sqqt1l FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE project (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_vcshost int NOT NULL,
  vcsPath varchar(255) DEFAULT NULL,
  UNIQUE (name),
  CONSTRAINT FK_1nd9om0ro9vonffewmcip7uwb FOREIGN KEY (id_vcshost) REFERENCES vcshost (id),
  CONSTRAINT FK_74pb5ee6fww1w9nq9j2ywdsbh FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_lvhq256oh6xeukhjrhnorukq4 FOREIGN KEY (id_modificator) REFERENCES user (id)
);

CREATE TABLE server (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  UNIQUE (name),
  CONSTRAINT FK_9j7nyy64yp542g69mg9vpewsk FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_tlrtteh75b7c46re3o17tvlq4 FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE project_configured_server (
  id_project int NOT NULL,
  id_server int NOT NULL,
  CONSTRAINT FK_5tt2hyu3axale1kn4d2shcu0c FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_k112b8vc3g68q8x3p9uonut6j FOREIGN KEY (id_project) REFERENCES project (id)
);

CREATE TABLE version (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  vcs_tag varchar(50) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  UNIQUE (id_project,vcs_tag),
  CONSTRAINT FK_35oqgmo4aaxsrli2ux0n1abw5 FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_gm5byguuaox4ols1262je575y FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_huqpnr0sjb7x0od8n9dorvyd FOREIGN KEY (id_creator) REFERENCES user (id)
);

CREATE TABLE deployment (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  deployment datetime DEFAULT NULL,
  undeployment datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_server int NOT NULL,
  id_version int NOT NULL,
  UNIQUE (id_version,id_server),
  CONSTRAINT FK_a5yujbhda47f05kcljkindfyu FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_hiojy4gb4y7d4jq7p985jkxcx FOREIGN KEY (id_version) REFERENCES version (id),
  CONSTRAINT FK_hmat4vhy9j3iqdsrvwm4j4l0t FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_rpwlxaw1e1eij6el847onpmg9 FOREIGN KEY (id_server) REFERENCES server (id)
);
