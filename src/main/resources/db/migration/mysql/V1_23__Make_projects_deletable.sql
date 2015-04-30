ALTER TABLE project ADD deleted TINYINT(1) NOT NULL DEFAULT 0;
UPDATE project SET deleted = 0;