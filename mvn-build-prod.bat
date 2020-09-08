rem Сборка приложения с профилем production
rem Рабочая версия приложения подключается к БД MS SQL.
rem Файл настроек подключения к БД application-production.yml
mvn clean package -DskipTests=true -P production