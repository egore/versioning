EXEC sp_rename 'user', 'user_';
GO

ALTER TABLE user RENAME TO user_;
