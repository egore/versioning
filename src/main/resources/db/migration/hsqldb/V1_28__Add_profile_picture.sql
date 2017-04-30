ALTER TABLE user_
  ADD COLUMN picture_id int DEFAULT NULL;
ALTER TABLE user_
  ADD CONSTRAINT FK_user_binarydata_picture FOREIGN KEY (picture_id) REFERENCES binary_data (id);
CREATE INDEX FK_user_binarydata_picture_IX ON user_ (picture_id);

ALTER TABLE binary_data
  ALTER COLUMN data varbinary(1000000);
