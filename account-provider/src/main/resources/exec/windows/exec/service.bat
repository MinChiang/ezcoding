@REM  -------------------------------------------------------
@REM  Author        : MinChiang
@REM  Email         : 448725235@qq.com
@REM  Last modified : 2019-08-23 09:46
@REM  Description   : ???????????????????
@REM  -------------------------------------------------------

@echo off
@chcp 65001 > nul
setlocal EnableDelayedExpansion

:: ??????????
cd %~dp0\..
for /f "delims=" %%t in ( 'CHDIR' ) do set DEPLOY_PATH=%%t
set BAT_PATH=%DEPLOY_PATH%\bin
echo BAT_PATH=%BAT_PATH%

set COMMAND=%1
if not defined COMMAND (
    echo ????????????start ^| stop ^| restart
    pause
    exit 1
)

if "%COMMAND%"=="start" (
    echo ----------------------------????----------------------------
    call %BAT_PATH%\startup.bat
) else if "%COMMAND%"=="stop" (
    echo ----------------------------????----------------------------
    call %BAT_PATH%\shutdown.bat
) else if "%COMMAND%"=="restart" (
    echo ----------------------------????----------------------------
    call %BAT_PATH%\shutdown.bat
    call %BAT_PATH%\startup.bat
) else (
    echo ????%COMMAND%??????start ^| stop ^| restart
    pause
    exit 1
)

pause