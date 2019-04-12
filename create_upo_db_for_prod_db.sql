USE [master]
GO

/**
Если бд нет создаем ее
*/
IF not exists (SELECT * FROM sys.databases WHERE NAME = 'upo-prod')
begin 
CREATE DATABASE [upo-prod]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'upo', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\upo-prod.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'upo_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\upo-prod_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
end 
GO

/**
*Создаем схему в БД
*/
USE [upo-prod]
GO

CREATE SCHEMA [upo]
GO


/**
* Создаем пользователя
*/
USE [upo-prod]
GO

CREATE USER [upo] FOR LOGIN [upo] WITH DEFAULT_SCHEMA  = [upo]
GO

ALTER ROLE [db_datareader] ADD MEMBER upo;
GO

ALTER ROLE [db_datawriter] ADD MEMBER upo;
GO

ALTER ROLE [db_owner] ADD MEMBER upo;
GO
