@REM  -------------------------------------------------------
@REM  Author        : MinChiang
@REM  Email         : 448725235@qq.com
@REM  Last modified : 2019-08-23 09:46
@REM  Description   : ????
@REM  -------------------------------------------------------

@echo off
@chcp 65001 > nul
setlocal EnableDelayedExpansion

:: ??????????
cd %~dp0\..
for /f "delims=" %%t in ( 'CHDIR' ) do set DEPLOY_PATH=%%t
set BAT_PATH=%DEPLOY_PATH%\bin
echo BAT_PATH=%BAT_PATH%

if not exist %BAT_PATH%\pid (
    echo ?????
    pause
    exit 1
)

set /P PID=<%BAT_PATH%\pid
TASKKILL /F /PID %PID% >nul 2>nul
if %errorlevel% equ 0 (
    echo ??%PID%????
) else (
    echo ??%PID%???
)
del %BAT_PATH%\pid
pause