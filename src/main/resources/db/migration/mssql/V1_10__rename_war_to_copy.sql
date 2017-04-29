EXEC sp_rename 'action_war', 'action_copy';
GO

ALTER TABLE spacer_url
  DROP CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi;
EXEC sp_rename 'spacer_url.id_war', 'id_action_copy', 'COLUMN';
ALTER TABLE spacer_url
  ADD CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);
GO

ALTER TABLE maven_artifact
  DROP CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5;
EXEC sp_rename 'maven_artifact.id_war', 'id_action_copy', 'COLUMN';
ALTER TABLE maven_artifact
  ADD CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5 FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);
GO

ALTER TABLE maven_artifact
  ADD packaging VARCHAR(31) NULL;
GO