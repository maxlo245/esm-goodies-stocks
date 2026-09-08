@echo off
REM =====================================================================
REM  Script de compilation et de lancement des tests unitaires
REM  Projet : esm.gestionStocksAthletes
REM =====================================================================

echo ============================================================
echo   Compilation et exécution des tests - Gestion de Stocks
echo ============================================================

if not exist "target\test" mkdir target\test

REM Télécharger JUnit (si non présent)
if not exist "target\lib" mkdir target\lib
if not exist "target\lib\junit-platform-console-standalone-1.10.2.jar" (
    echo Téléchargement de JUnit...
    curl -L -o target\lib\junit-platform-console-standalone-1.10.2.jar ^
        https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar 2>nul
)

REM Compiler les classes principales
javac -d target src\main\java\gestionStocks\*.java 2>&1
if %errorlevel% neq 0 (
    echo Erreur de compilation des classes !
    pause
    exit /b 1
)

REM Compiler les tests
javac -cp target;target\lib\junit-platform-console-standalone-1.10.2.jar -d target\test src\test\java\gestionStocks\*.java 2>&1
if %errorlevel% neq 0 (
    echo Erreur de compilation des tests !
    pause
    exit /b 1
)

REM Lancer les tests
java -jar target\lib\junit-platform-console-standalone-1.10.2.jar --class-path target;target\test --scan-class-path target\test 2>&1

pause
