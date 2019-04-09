/**
Скрипт создает пользователя для подключения приложения на локальной тестовой бд.
Скрипт нужно выпольнить от пользователя с правами dbo
 */
USE [master]
GO
/**
Удаляем пользователя если есть.
*/
IF exists (SELECT * FROM sys.server_principals WHERE NAME='upo')
DROP LOGIN [upo]
GO

/**
Создаем пользователя upo
 */
CREATE LOGIN [upo] WITH PASSWORD=N'{óòÃµ¥ìÕ¾CÍ@³ë¸v¶HV', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[русский], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO