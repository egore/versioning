ALTER TABLE action_war RENAME TO action_copy;

ALTER TABLE spacer_url
  DROP CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi;
ALTER TABLE spacer_url
  ALTER COLUMN id_war RENAME TO id_action_copy;
ALTER TABLE spacer_url
  ADD CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);

ALTER TABLE maven_artifact
  DROP CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5;
ALTER TABLE maven_artifact
  ALTER COLUMN id_war RENAME TO id_action_copy;
ALTER TABLE maven_artifact
  ADD CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5 FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);

ALTER TABLE maven_artifact
  ADD COLUMN packaging VARCHAR(31) NULL;