ALTER TABLE action_checkout
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE action_checkout
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE action_copy
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE action_copy
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE action_extraction
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE action_extraction
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE action_replacement
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE action_replacement
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE binary_data
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE binary_data
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE deployment
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE deployment
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE extraction
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE extraction
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE maven_artifact
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE maven_artifact
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE maven_repository
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE maven_repository
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE project
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE project
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE replacement
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE replacement
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE replacementfile
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE replacementfile
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE role
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE role
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE role_permissions
CHANGE id_role role_id int(11) NOT NULL;

ALTER TABLE server
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE server
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE spacer_url
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE spacer_url
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE tag_transformer
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE tag_transformer
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE user_
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE user_
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE user_role
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE user_role
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE variable
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE variable
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE vcshost
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE vcshost
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE version
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE version
RENAME COLUMN id_modificator TO modificator_id;

ALTER TABLE wildcard
RENAME COLUMN id_creator TO creator_id;
ALTER TABLE wildcard
RENAME COLUMN id_modificator TO modificator_id;
