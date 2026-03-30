@echo off
REM Script de build para FulldevCoins
REM Use: build.bat (Windows)

echo Limpando builds anteriores...
call mvn clean

echo Compilando o projeto...
call mvn compile

echo Empacotando JAR...
call mvn package

if exist "target\Fulldev-coins-1.0-SNAPSHOT.jar" (
    echo ✅ Build realizado com sucesso!
    echo 📦 JAR gerado: target\Fulldev-coins-1.0-SNAPSHOT.jar
    echo.
    echo 🚀 Para instalar, copie o JAR para plugins\ do seu servidor Spigot
) else (
    echo ❌ Erro na compilacao!
    exit /b 1
)
pause
