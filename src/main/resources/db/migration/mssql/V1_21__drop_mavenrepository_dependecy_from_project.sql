DROP INDEX FK_e50f4sg1vtd5udecjy0b9bo1h ON project
GO

DECLARE @sql NVARCHAR(MAX)
WHILE 1=1
BEGIN
    SELECT TOP 1 @sql = N'ALTER TABLE project DROP CONSTRAINT [' + dc.NAME + N']'
    FROM sys.default_constraints dc
    JOIN sys.columns c
        ON c.default_object_id = dc.object_id
    WHERE
        dc.parent_object_id = OBJECT_ID('project')
    AND c.name = N'id_mavenrepository'
    IF @@ROWCOUNT = 0 BREAK
    EXEC (@sql)
END
GO

ALTER TABLE project DROP CONSTRAINT FK_e50f4sg1vtd5udecjy0b9bo1h
GO

ALTER TABLE project DROP COLUMN id_mavenrepository
GO