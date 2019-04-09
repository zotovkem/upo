﻿USE [master]
GO

/**
Если бд нет создаем ее
*/
IF not exists (SELECT * FROM sys.databases WHERE NAME='upo')
begin 
CREATE DATABASE [upo]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'upo', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\upo.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'upo_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\upo_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
end 
GO
/**
*Настройки БД по умолчанию
*/
/*USE [master]
GO

ALTER DATABASE [upo] SET COMPATIBILITY_LEVEL = 110
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [upo].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [upo] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [upo] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [upo] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [upo] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [upo] SET ARITHABORT OFF 
GO

ALTER DATABASE [upo] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [upo] SET AUTO_CREATE_STATISTICS ON 
GO

ALTER DATABASE [upo] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [upo] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [upo] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [upo] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [upo] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [upo] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [upo] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [upo] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [upo] SET  DISABLE_BROKER 
GO

ALTER DATABASE [upo] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [upo] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [upo] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [upo] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [upo] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [upo] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [upo] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [upo] SET RECOVERY SIMPLE 
GO

ALTER DATABASE [upo] SET  MULTI_USER 
GO

ALTER DATABASE [upo] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [upo] SET DB_CHAINING OFF 
GO

ALTER DATABASE [upo] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [upo] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [upo] SET  READ_WRITE 
GO*/

/**
*Создаем схему в БД
*/
USE [upo]
GO

CREATE SCHEMA [upo]
GO


/**
*
*/
USE [upo]
GO

CREATE USER [upo] FOR LOGIN [upo]
GO

ALTER ROLE [db_datareader] ADD MEMBER upo;
GO

ALTER ROLE db_datawriter ADD MEMBER upo;
GO




