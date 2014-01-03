CREATE TABLE binary_data (
  id int IDENTITY(1,1) PRIMARY KEY,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  contentType varchar(511) DEFAULT NULL,
  data varbinary(max),
  filename varchar(255) DEFAULT NULL,
  size bigint NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  CONSTRAINT FK_ds85rbfuowf9kw14c9o3bbw63 FOREIGN KEY (id_modificator) REFERENCES [user] (id),
  CONSTRAINT FK_5xn81iixg7o4xunwjfp4idsak FOREIGN KEY (id_creator) REFERENCES [user] (id)
);
CREATE INDEX FK_5xn81iixg7o4xunwjfp4idsak ON binary_data (id_creator);
CREATE INDEX FK_ds85rbfuowf9kw14c9o3bbw63 ON binary_data (id_modificator);
GO

ALTER TABLE server
  ADD id_binary_data_icon int DEFAULT NULL,
  CONSTRAINT FK_avn9jpavh927jrcn6abp2r1ju FOREIGN KEY (id_binary_data_icon) REFERENCES binary_data (id);
CREATE INDEX FK_avn9jpavh927jrcn6abp2r1ju ON server (id_binary_data_icon);
GO