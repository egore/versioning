CREATE TABLE [user] (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_kdphgulfr5qyj8yorpt532e6a FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_s2jrcrakotyc6ej7v3i0ohp5u FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE INDEX FK_s2jrcrakotyc6ej7v3i0ohp5u ON [user] (id_creator);
CREATE INDEX FK_kdphgulfr5qyj8yorpt532e6a ON [user] (id_modificator);
GO

CREATE TABLE role (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_8e4noh44dhdi0ulf0d58wvslo FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_jkqu5ixcuhlvgr3jkxnjktkr FOREIGN KEY (id_modificator) REFERENCES [user] (id)
);
CREATE INDEX FK_8e4noh44dhdi0ulf0d58wvslo ON role (id_creator);
CREATE INDEX FK_jkqu5ixcuhlvgr3jkxnjktkr ON role (id_modificator);
GO

CREATE TABLE role_permissions (
  id_role int NOT NULL,
  permission varchar(255) DEFAULT NULL,
  CONSTRAINT FK_jblsl6vi1sgpwh79ik1jkp8gm FOREIGN KEY (id_role) REFERENCES role (id)
);
CREATE INDEX FK_jblsl6vi1sgpwh79ik1jkp8gm ON role_permissions (id_role);
GO

CREATE TABLE user_role (
  id_user int NOT NULL,
  id_role int NOT NULL,
  CONSTRAINT FK_56olsq329osn3lxem8ftn9q9h FOREIGN KEY (id_role) REFERENCES role (id),
  CONSTRAINT FK_qibq3rh7mo4f8372yxkn549j7 FOREIGN KEY (id_user) REFERENCES [user] (id)
);
CREATE INDEX FK_56olsq329osn3lxem8ftn9q9h ON user_role (id_role);
CREATE INDEX FK_qibq3rh7mo4f8372yxkn549j7 ON user_role (id_user);
GO

CREATE TABLE vcshost (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  uri varchar(255) NOT NULL,
  vcs varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_rh6kawdkugkj9tut91jmi8tck FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_tivndyh6ivgvvt69cu8sqqt1l FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX pvcshost__name ON vcshost (name);
CREATE INDEX FK_tivndyh6ivgvvt69cu8sqqt1l ON vcshost (id_creator);
CREATE INDEX FK_rh6kawdkugkj9tut91jmi8tck ON vcshost (id_modificator);
GO

CREATE TABLE project (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_vcshost int NOT NULL,
  vcsPath varchar(255) DEFAULT NULL,
  CONSTRAINT FK_1nd9om0ro9vonffewmcip7uwb FOREIGN KEY (id_vcshost) REFERENCES vcshost (id),
  CONSTRAINT FK_74pb5ee6fww1w9nq9j2ywdsbh FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_lvhq256oh6xeukhjrhnorukq4 FOREIGN KEY (id_modificator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX project__name ON project (name);
CREATE UNIQUE INDEX FK_74pb5ee6fww1w9nq9j2ywdsbh ON project (id_creator);
CREATE UNIQUE INDEX FK_lvhq256oh6xeukhjrhnorukq4 ON project (id_modificator);
CREATE UNIQUE INDEX FK_1nd9om0ro9vonffewmcip7uwb ON project (id_vcshost);
GO

CREATE TABLE server (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_9j7nyy64yp542g69mg9vpewsk FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_tlrtteh75b7c46re3o17tvlq4 FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX project__name ON server (name);
CREATE INDEX FK_tlrtteh75b7c46re3o17tvlq4 ON server (id_creator);
CREATE INDEX FK_9j7nyy64yp542g69mg9vpewsk ON server (id_modificator);
GO

CREATE TABLE project_configured_server (
  id_project int NOT NULL,
  id_server int NOT NULL,
  CONSTRAINT FK_5tt2hyu3axale1kn4d2shcu0c FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_k112b8vc3g68q8x3p9uonut6j FOREIGN KEY (id_project) REFERENCES project (id)
);
CREATE INDEX FK_5tt2hyu3axale1kn4d2shcu0c ON project_configured_server (id_server);
CREATE INDEX FK_k112b8vc3g68q8x3p9uonut6j ON project_configured_server (id_project);
GO

CREATE TABLE version (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  vcs_tag varchar(50) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_project int NOT NULL,
  CONSTRAINT FK_35oqgmo4aaxsrli2ux0n1abw5 FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_gm5byguuaox4ols1262je575y FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_huqpnr0sjb7x0od8n9dorvyd FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE UNIQUE INDEX version__project_vcstag ON version (id_project,vcs_tag);
CREATE INDEX FK_huqpnr0sjb7x0od8n9dorvyd ON version (id_creator);
CREATE INDEX FK_35oqgmo4aaxsrli2ux0n1abw5 ON version (id_modificator);
CREATE INDEX FK_gm5byguuaox4ols1262je575y ON version (id_project);
GO

CREATE TABLE deployment (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  deployment datetime DEFAULT NULL,
  undeployment datetime DEFAULT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_server int NOT NULL,
  id_version int NOT NULL,
  CONSTRAINT FK_a5yujbhda47f05kcljkindfyu FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_hiojy4gb4y7d4jq7p985jkxcx FOREIGN KEY (id_version) REFERENCES version (id),
  CONSTRAINT FK_hmat4vhy9j3iqdsrvwm4j4l0t FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_rpwlxaw1e1eij6el847onpmg9 FOREIGN KEY (id_server) REFERENCES server (id)
);
CREATE UNIQUE INDEX deployment__version_server ON deployment (id_version,id_server);
CREATE INDEX FK_a5yujbhda47f05kcljkindfyu ON deployment (id_creator);
CREATE INDEX FK_hmat4vhy9j3iqdsrvwm4j4l0t ON deployment (id_modificator);
CREATE INDEX FK_rpwlxaw1e1eij6el847onpmg9 ON deployment (id_server);
CREATE INDEX FK_hiojy4gb4y7d4jq7p985jkxcx ON deployment (id_version);
GO
