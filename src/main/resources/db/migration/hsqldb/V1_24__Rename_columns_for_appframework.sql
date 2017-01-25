ALTER TABLE action_checkout
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE action_checkout
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE action_copy
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE action_copy
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE action_extraction
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE action_extraction
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE action_replacement
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE action_replacement
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE binary_data
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE binary_data
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE deployment
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE deployment
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE extraction
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE extraction
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE maven_artifact
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE maven_artifact
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE maven_repository
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE maven_repository
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE project
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE project
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE replacement
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE replacement
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE replacementfile
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE replacementfile
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE role
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE role
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE role_permissions
  ALTER COLUMN id_role RENAME TO role_id;

ALTER TABLE server
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE server
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE spacer_url
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE spacer_url
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE tag_transformer
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE tag_transformer
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE user_
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE user_
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE user_role
  ALTER COLUMN id_user RENAME TO user_id;
ALTER TABLE user_role
  ALTER COLUMN id_role RENAME TO role_id;

ALTER TABLE variable
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE variable
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE vcshost
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE vcshost
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE version
  ALTER COLUMN id_creator RENAME TO creator_id;
ALTER TABLE version
  ALTER COLUMN id_modificator RENAME TO modificator_id;

ALTER TABLE wildcard
  DROP COLUMN id_creator;
ALTER TABLE wildcard
  DROP COLUMN id_modificator;
-- id IDENTITY NOT NULL,
-- created datetime DEFAULT NULL,
-- modified datetime DEFAULT NULL,
-- value varchar(254) NOT NULL,
-- id_creator int DEFAULT NULL,
-- id_modificator int DEFAULT NULL,
-- id_action_replace int NOT NULL,
