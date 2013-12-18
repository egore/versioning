CREATE TABLE binary_data (
  id int(11) NOT NULL AUTO_INCREMENT,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  contentType varchar(511) DEFAULT NULL,
  data blob,
  filename varchar(255) DEFAULT NULL,
  size bigint(20) NOT NULL,
  id_creator int(11) DEFAULT NULL,
  id_modificator int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_5xn81iixg7o4xunwjfp4idsak (id_creator),
  KEY FK_ds85rbfuowf9kw14c9o3bbw63 (id_modificator),
  CONSTRAINT FK_ds85rbfuowf9kw14c9o3bbw63 FOREIGN KEY (id_modificator) REFERENCES user (id),
  CONSTRAINT FK_5xn81iixg7o4xunwjfp4idsak FOREIGN KEY (id_creator) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE server
  ADD COLUMN id_binary_data_icon int(11) DEFAULT NULL,
  ADD KEY FK_avn9jpavh927jrcn6abp2r1ju (id_binary_data_icon),
  ADD CONSTRAINT FK_avn9jpavh927jrcn6abp2r1ju FOREIGN KEY (id_binary_data_icon) REFERENCES binary_data (id);