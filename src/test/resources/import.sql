INSERT INTO vcshost(id, name, vcs, uri) VALUES (1, 'svn Server', 'svn', 'https://svn/repos');
INSERT INTO vcshost(id, name, vcs, uri) VALUES (2, 'git Server', 'git', 'ssh://git/repos');

INSERT INTO project(id, name, id_vcshost) VALUES (1, 'some_svn_project', 1);
INSERT INTO project(id, name, id_vcshost) VALUES (2, 'another_svn_project', 1);
INSERT INTO project(id, name, id_vcshost) VALUES (3, 'a_git_project', 2);

INSERT INTO version(id, id_project, vcs_tag) VALUES (1, 1, '1.0.0');
INSERT INTO version(id, id_project, vcs_tag) VALUES (2, 1, '1.0.1');
INSERT INTO version(id, id_project, vcs_tag) VALUES (3, 2, '3.0.0');
INSERT INTO version(id, id_project, vcs_tag) VALUES (4, 2, '3.0.1');
INSERT INTO version(id, id_project, vcs_tag) VALUES (5, 2, '3.0.2');

INSERT INTO server(id, name) VALUES (1, 'production_server');
INSERT INTO server(id, name) VALUES (2, 'test_server');

INSERT INTO project_configured_server(id_server, id_project) VALUES (1, 1);
INSERT INTO project_configured_server(id_server, id_project) VALUES (2, 1);
INSERT INTO project_configured_server(id_server, id_project) VALUES (2, 2);

INSERT INTO deployment(id_version, id_server, deployment, undeployment) VALUES (1, 1, '2013-11-22 12:34:56', null);