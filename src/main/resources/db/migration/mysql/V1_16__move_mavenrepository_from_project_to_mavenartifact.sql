ALTER TABLE maven_artifact
  ADD COLUMN id_mavenrepository int(11) NULL,
  ADD KEY FK_83qoras0dcr5wjl15t7rqhfhe (id_mavenrepository),
  ADD CONSTRAINT FK_83qoras0dcr5wjl15t7rqhfhe FOREIGN KEY (id_mavenrepository) REFERENCES maven_repository (id);

UPDATE maven_artifact SET id_mavenrepository = (
SELECT project.id_mavenrepository FROM action_extraction
  INNER JOIN project ON action_extraction.id_project = project.id
  WHERE maven_artifact.id_action_extraction = action_extraction.id)
  WHERE id_action_extraction IS NOT NULL;

UPDATE maven_artifact SET id_mavenrepository = (
SELECT project.id_mavenrepository FROM action_copy
  INNER JOIN project ON action_copy.id_project = project.id
  WHERE maven_artifact.id_action_copy = action_copy.id)
  WHERE id_action_copy IS NOT NULL;

ALTER TABLE maven_artifact
  CHANGE COLUMN id_mavenrepository id_mavenrepository int(11) NOT NULL;