INSERT INTO role_permissions SELECT id_role, 'CREATE_VERSIONS' FROM role_permissions WHERE permission = 'USE';
INSERT INTO role_permissions SELECT id_role, 'DEPLOY' FROM role_permissions WHERE permission = 'USE';
DELETE FROM role_permissions WHERE permission = 'USE';