@echo off
REM =====================================================================
REM  Script de compilation et de lancement de l'application
REM  Réapprovisionnement des Goodies
REM  Projet : esm.reapproStocksGoodies
REM =====================================================================

echo ============================================================
echo   Compilation de l'application Réapprovisionnement
echo ============================================================

REM Créer le dossier de sortie
if not exist "target" mkdir target

REM Compiler tous les fichiers Java du package reapproStocks
echo Compilation en cours...
javac -d target src\main\java\reapproStocks\*.java 2>&1
if %errorlevel% neq 0 (
    echo Erreur de compilation !
    pause
    exit /b 1
)

echo Compilation terminée avec succès.

echo ============================================================
echo   Lancement de l'application
echo ============================================================
java -cp target reapproStocks.Main

pause
