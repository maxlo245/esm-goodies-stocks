@echo off
REM =====================================================================
REM  Script de compilation et de lancement de l'application
REM  Gestion de Stocks des Athlètes
REM  Projet : esm.gestionStocksAthletes
REM =====================================================================

echo ============================================================
echo   Compilation de l'application Gestion de Stocks
echo ============================================================

REM Créer le dossier de sortie
if not exist "target" mkdir target

REM Compiler tous les fichiers Java du package gestionStocks
echo Compilation en cours...
javac -d target src\main\java\gestionStocks\*.java 2>&1
if %errorlevel% neq 0 (
    echo Erreur de compilation !
    pause
    exit /b 1
)

echo Compilation terminée avec succès.

echo ============================================================
echo   Lancement de l'application
echo ============================================================
java -cp target gestionStocks.Main

pause
