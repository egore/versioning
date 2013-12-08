RENAME TABLE action_war TO action_copy;

ALTER TABLE spacer_url 
  DROP FOREIGN KEY FK_fxjsjddujg8pqxhsgf5wssyfi;
ALTER TABLE spacer_url 
  CHANGE COLUMN id_war id_action_copy INT(11) NOT NULL;
ALTER TABLE spacer_url 
  ADD CONSTRAINT FK_fxjsjddujg8pqxhsgf5wssyfi FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);

ALTER TABLE maven_artifact 
  DROP FOREIGN KEY FK_fsio2r4kfwoupmiljkt308he5;
ALTER TABLE maven_artifact 
  CHANGE COLUMN id_war id_action_copy INT(11) NOT NULL ;
ALTER TABLE maven_artifact 
  ADD CONSTRAINT FK_fsio2r4kfwoupmiljkt308he5 FOREIGN KEY (id_action_copy) REFERENCES action_copy (id);

ALTER TABLE maven_artifact 
  ADD COLUMN packaging VARCHAR(31) NULL;