INSERT INTO role(name,created,modified,id_creator,id_modificator)
 VALUES ('Administrators',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL),
        ('Users',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL);

INSERT INTO role_permissions(id_role,permission)
 VALUES ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_SETTINGS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_USERS'),
        ((SELECT id FROM role WHERE name = 'Users'),'USE');

INSERT INTO "user"(name,login,password,email,created,modified,id_creator,id_modificator)
 VALUES ('Administrator','admin','d033e22ae348aeb5660fc2140aec35850c4da997','admin@localhost',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,NULL);

INSERT INTO user_role(id_user,id_role)
 VALUES ((SELECT id FROM "user" WHERE name = 'Administrator'),(SELECT id FROM role WHERE name = 'Administrators'));