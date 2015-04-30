ALTER TABLE project ADD deleted BIT NOT NULL DEFAULT 0
GO

UPDATE project SET deleted = 0
GO