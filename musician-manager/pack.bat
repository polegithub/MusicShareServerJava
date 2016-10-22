@echo off
cd ..\infrastructure\
call mvn clean install -DskipTests -e
cd ..\musician-core\
call mvn clean install -DskipTests -e
cd ..\musician-repository\
call mvn clean install -DskipTests -e
cd ..\musician-service\
call mvn clean install -DskipTests -e
cd ..\musician-manager\
call mvn clean package -DskipTests -e
pause