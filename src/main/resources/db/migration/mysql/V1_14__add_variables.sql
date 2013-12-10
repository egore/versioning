CREATE TABLE variable (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  id_server int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_fayeaas4rxt7i1fhnijoquido (id_creator),
  KEY FK_y2q5jvlx878m5kb4fqpuxjri (id_modificator),
  KEY FK_5d1vvlwc2xnudfj0gwnfrhb7a (id_server),
  CONSTRAINT FK_5d1vvlwc2xnudfj0gwnfrhb7a FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_fayeaas4rxt7i1fhnijoquido FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_y2q5jvlx878m5kb4fqpuxjri FOREIGN KEY (id_modificator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
