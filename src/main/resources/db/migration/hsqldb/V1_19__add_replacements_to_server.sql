ALTER TABLE action_replacement
  ADD COLUMN id_server int DEFAULT NULL;
ALTER TABLE action_replacement
  ALTER COLUMN id_project SET NULL;
ALTER TABLE action_replacement
  ADD CONSTRAINT FK_b38em92ea0yw0ekkcs6bsyi8q FOREIGN KEY (id_server) REFERENCES server (id);