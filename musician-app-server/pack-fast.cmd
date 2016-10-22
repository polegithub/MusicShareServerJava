@echo off
call mvn clean package -DskipTests -e -U
pause