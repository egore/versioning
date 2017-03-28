ALTER TABLE action_checkout
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE action_checkout
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE action_copy
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE action_copy
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE action_extraction
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE action_extraction
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE action_replacement
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE action_replacement
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE binary_data
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE binary_data
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE deployment
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE deployment
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE extraction
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE extraction
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE maven_artifact
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE maven_artifact
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE maven_repository
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE maven_repository
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE project
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE project
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE replacement
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE replacement
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE replacementfile
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE replacementfile
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE role
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE role
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE role_permissions
CHANGE id_role role_id int(11) NOT NULL;

ALTER TABLE server
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE server
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE spacer_url
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE spacer_url
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE tag_transformer
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE tag_transformer
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE user_
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE user_
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE user_role
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE user_role
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE variable
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE variable
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE vcshost
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE vcshost
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE version
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE version
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;

ALTER TABLE wildcard
CHANGE id_creator creator_id int(11) DEFAULT NULL;
ALTER TABLE wildcard
CHANGE id_modificator modificator_id int(11) DEFAULT NULL;
