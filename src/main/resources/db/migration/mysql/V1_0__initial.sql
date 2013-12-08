CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  email varchar(255) NOT NULL,
  login varchar(63) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(40) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_s2jrcrakotyc6ej7v3i0ohp5u (id_creator),
  KEY FK_kdphgulfr5qyj8yorpt532e6a (id_modificator),
  CONSTRAINT FK_kdphgulfr5qyj8yorpt532e6a FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_s2jrcrakotyc6ej7v3i0ohp5u FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_8e4noh44dhdi0ulf0d58wvslo (id_creator),
  KEY FK_jkqu5ixcuhlvgr3jkxnjktkr (id_modificator),
  CONSTRAINT FK_8e4noh44dhdi0ulf0d58wvslo FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_jkqu5ixcuhlvgr3jkxnjktkr FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permissions (
  id_role int(11) NOT NULL,
  permission varchar(255) DEFAULT NULL,
  KEY FK_jblsl6vi1sgpwh79ik1jkp8gm (id_role),
  CONSTRAINT FK_jblsl6vi1sgpwh79ik1jkp8gm FOREIGN KEY (id_role) REFERENCES role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_role (
  id_user int(11) NOT NULL,
  id_role int(11) NOT NULL,
  KEY FK_56olsq329osn3lxem8ftn9q9h (id_role),
  KEY FK_qibq3rh7mo4f8372yxkn549j7 (id_user),
  CONSTRAINT FK_56olsq329osn3lxem8ftn9q9h FOREIGN KEY (id_role) REFERENCES role (id),
  CONSTRAINT FK_qibq3rh7mo4f8372yxkn549j7 FOREIGN KEY (id_user) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE vcshost (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  uri varchar(255) NOT NULL,
  vcs varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY pvcshost__name (name),
  KEY FK_tivndyh6ivgvvt69cu8sqqt1l (id_creator),
  KEY FK_rh6kawdkugkj9tut91jmi8tck (id_modificator),
  CONSTRAINT FK_rh6kawdkugkj9tut91jmi8tck FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_tivndyh6ivgvvt69cu8sqqt1l FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_vcshost int(11) NOT NULL,
  vcsPath varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY project__name (name),
  KEY FK_74pb5ee6fww1w9nq9j2ywdsbh (id_creator),
  KEY FK_lvhq256oh6xeukhjrhnorukq4 (id_modificator),
  KEY FK_1nd9om0ro9vonffewmcip7uwb (id_vcshost),
  CONSTRAINT FK_1nd9om0ro9vonffewmcip7uwb FOREIGN KEY (id_vcshost) REFERENCES vcshost (id),
  CONSTRAINT FK_74pb5ee6fww1w9nq9j2ywdsbh FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_lvhq256oh6xeukhjrhnorukq4 FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE server (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY project__name (name),
  KEY FK_tlrtteh75b7c46re3o17tvlq4 (id_creator),
  KEY FK_9j7nyy64yp542g69mg9vpewsk (id_modificator),
  CONSTRAINT FK_9j7nyy64yp542g69mg9vpewsk FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_tlrtteh75b7c46re3o17tvlq4 FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project_configured_server (
  id_project int(11) NOT NULL,
  id_server int(11) NOT NULL,
  KEY FK_5tt2hyu3axale1kn4d2shcu0c (id_server),
  KEY FK_k112b8vc3g68q8x3p9uonut6j (id_project),
  CONSTRAINT FK_5tt2hyu3axale1kn4d2shcu0c FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_k112b8vc3g68q8x3p9uonut6j FOREIGN KEY (id_project) REFERENCES project (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE version (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  vcs_tag varchar(50) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_project int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY version__project_vcstag (id_project,vcs_tag),
  KEY FK_huqpnr0sjb7x0od8n9dorvyd (id_creator),
  KEY FK_35oqgmo4aaxsrli2ux0n1abw5 (id_modificator),
  KEY FK_gm5byguuaox4ols1262je575y (id_project),
  CONSTRAINT FK_35oqgmo4aaxsrli2ux0n1abw5 FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_gm5byguuaox4ols1262je575y FOREIGN KEY (id_project) REFERENCES project (id),
  CONSTRAINT FK_huqpnr0sjb7x0od8n9dorvyd FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE deployment (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  deployment datetime DEFAULT NULL,
  undeployment datetime DEFAULT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_server int(11) NOT NULL,
  id_version int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY deployment__version_server (id_version,id_server),
  KEY FK_a5yujbhda47f05kcljkindfyu (id_creator),
  KEY FK_hmat4vhy9j3iqdsrvwm4j4l0t (id_modificator),
  KEY FK_rpwlxaw1e1eij6el847onpmg9 (id_server),
  KEY FK_hiojy4gb4y7d4jq7p985jkxcx (id_version),
  CONSTRAINT FK_a5yujbhda47f05kcljkindfyu FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_hiojy4gb4y7d4jq7p985jkxcx FOREIGN KEY (id_version) REFERENCES version (id),
  CONSTRAINT FK_hmat4vhy9j3iqdsrvwm4j4l0t FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_rpwlxaw1e1eij6el847onpmg9 FOREIGN KEY (id_server) REFERENCES server (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
