rem Сборка приложения с профилем local
rem Тестовая версия приложения подключается к БД MS SQL.
rem Настроеки подключения к БД хранятся в application.yml под профилем local
mvn clean package -DskipTests=true -P local