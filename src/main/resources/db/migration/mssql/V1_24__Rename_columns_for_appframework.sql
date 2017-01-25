EXEC sp_rename 'action_checkout.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'action_checkout.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'action_copy.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'action_copy.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'action_extraction.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'action_extraction.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'action_replacement.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'action_replacement.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'binary_data.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'binary_data.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'deployment.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'deployment.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'extraction.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'extraction.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'maven_artifact.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'maven_artifact.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'maven_repository.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'maven_repository.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'project.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'project.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'replacement.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'replacement.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'replacementfile.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'replacementfile.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'role.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'role.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'role_permissions.id_role', 'role_id', 'COLUMN'
GO

EXEC sp_rename 'server.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'server.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'spacer_url.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'spacer_url.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'tag_transformer.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'tag_transformer.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'user_.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'user_.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'user_role.id_user', 'user_id', 'COLUMN'
GO
EXEC sp_rename 'user_role.id_role', 'role_id', 'COLUMN'
GO

EXEC sp_rename 'variable.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'variable.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'vcshost.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'vcshost.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'version.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'version.id_modificator', 'modificator_id', 'COLUMN'
GO

EXEC sp_rename 'wildcard.id_creator', 'creator_id', 'COLUMN'
GO
EXEC sp_rename 'wildcard.id_modificator', 'modificator_id', 'COLUMN'
GO
