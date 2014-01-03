CREATE TABLE variable (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_server int NOT NULL,
  CONSTRAINT FK_5d1vvlwc2xnudfj0gwnfrhb7a FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_fayeaas4rxt7i1fhnijoquido FOREIGN KEY (id_creator) REFERENCES [user] (id),
  CONSTRAINT FK_y2q5jvlx878m5kb4fqpuxjri FOREIGN KEY (id_modificator) REFERENCES [user] (id)
);
CREATE INDEX FK_fayeaas4rxt7i1fhnijoquido ON variable (id_creator);
CREATE INDEX FK_y2q5jvlx878m5kb4fqpuxjri ON variable (id_modificator);
CREATE INDEX FK_5d1vvlwc2xnudfj0gwnfrhb7a ON variable (id_server);
GO
