INSERT INTO role_permissions(role_id, permission)
 VALUES ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_USERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'SHOW_ROLES'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_ROLES');