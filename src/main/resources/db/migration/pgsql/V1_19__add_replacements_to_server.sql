ALTER TABLE action_replacement
  ADD id_server int DEFAULT NULL,
  ADD CONSTRAINT FK_b38em92ea0yw0ekkcs6bsyi8q FOREIGN KEY (id_server) REFERENCES server (id);
CREATE INDEX FK_b38em92ea0yw0ekkcs6bsyi8q ON action_replacement (id_server);
ALTER TABLE action_replacement
  ALTER COLUMN id_project DROP NOT NULL;
