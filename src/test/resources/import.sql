INSERT INTO vcshost(id, name, vcs, uri) VALUES (1, 'svn Server', 'svn', 'https://svn/repos');
INSERT INTO vcshost(id, name, vcs, uri) VALUES (2, 'git Server', 'git', 'ssh://git/repos');
INSERT INTO vcshost(id, name, vcs, uri) VALUES (3, 'another git Server', 'git', 'ssh://git2/repos');

INSERT INTO project(id, name, id_vcshost) VALUES (1, 'some_svn_project', 1);
INSERT INTO project(id, name, id_vcshost) VALUES (2, 'another_svn_project', 1);
INSERT INTO project(id, name, id_vcshost) VALUES (3, 'a_git_project', 2);
INSERT INTO project(id, name, id_vcshost) VALUES (4, 'another_git_project', 3);

INSERT INTO version(id, id_project, vcs_tag) VALUES (1, 1, '1.0.0');
INSERT INTO version(id, id_project, vcs_tag) VALUES (2, 1, '1.0.1');
INSERT INTO version(id, id_project, vcs_tag) VALUES (3, 2, '3.0.0');
INSERT INTO version(id, id_project, vcs_tag) VALUES (4, 2, '3.0.1');
INSERT INTO version(id, id_project, vcs_tag) VALUES (5, 2, '3.0.2');

INSERT INTO server(id, name) VALUES (1, 'production_server');
INSERT INTO server(id, name) VALUES (2, 'test_server');
INSERT INTO server(id, name) VALUES (3, 'random_server');

INSERT INTO project_configured_server(id_server, id_project) VALUES (1, 1);
INSERT INTO project_configured_server(id_server, id_project) VALUES (2, 1);
INSERT INTO project_configured_server(id_server, id_project) VALUES (2, 2);

INSERT INTO deployment(id_version, id_server, deployment, undeployment) VALUES (1, 1, '2013-11-22 12:34:56', null);
INSERT INTO deployment(id_version, id_server, deployment, undeployment) VALUES (3, 2, '2013-11-22 12:34:56', '2013-11-22 12:34:57');
INSERT INTO deployment(id_version, id_server, deployment, undeployment) VALUES (4, 2, '2013-11-22 12:34:57', '2013-11-22 12:34:58');
INSERT INTO deployment(id_version, id_server, deployment, undeployment) VALUES (5, 2, '2013-11-22 12:34:58', null);

INSERT INTO maven_repository(id, name, baseUrl) VALUES (1, 'Repo 1', 'http://maven1/');
INSERT INTO maven_repository(id, name, baseUrl) VALUES (2, 'Repo 2', 'http://maven2/');
INSERT INTO maven_repository(id, name, baseUrl) VALUES (3, 'Repo 3', 'http://maven3/');

INSERT INTO tag_transformer(id, name, replacementPattern, searchPattern) VALUES (1, 'No capitals', '[A-Z]+', '');
INSERT INTO tag_transformer(id, name, replacementPattern, searchPattern) VALUES (2, 'No numbers', '\d+', '');

INSERT INTO verification(id, group_id, artifact_id, version) VALUES (1, 'de.egore911', 'artifact-a', '1.0.0');
INSERT INTO verification(id, group_id, artifact_id, version) VALUES (2, 'de.egore911', 'artifact-a', '1.0.2');
INSERT INTO verification(id, group_id, artifact_id, version) VALUES (3, 'de.egore911', 'artifact-b', '1.0.1');

INSERT INTO used_artifact(id, verification_id, last_seen, group_id, artifact_id, version) VALUES (1, 1, '2013-11-22 12:34:58', 'de.egore911', 'artifact-c', '2.0.0');
INSERT INTO used_artifact(id, verification_id, last_seen, group_id, artifact_id, version) VALUES (2, 2, '2013-11-22 12:34:58', 'de.egore911', 'artifact-c', '2.0.1');
INSERT INTO used_artifact(id, verification_id, last_seen, group_id, artifact_id, version) VALUES (3, 3, '2013-11-22 12:34:58', 'de.egore911', 'artifact-c', '2.0.0');
