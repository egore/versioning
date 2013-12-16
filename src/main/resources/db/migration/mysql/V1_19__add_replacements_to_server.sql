ALTER TABLE action_replacement
  ADD COLUMN id_server int(11) DEFAULT NULL,
  CHANGE COLUMN id_project id_project int(11) DEFAULT NULL,
  ADD KEY FK_b38em92ea0yw0ekkcs6bsyi8q (id_server),
  ADD CONSTRAINT FK_b38em92ea0yw0ekkcs6bsyi8q FOREIGN KEY (id_server) REFERENCES server (id);