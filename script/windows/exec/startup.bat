@REM  -------------------------------------------------------
@REM  Author        : MinChiang
@REM  Email         : 448725235@qq.com
@REM  Last modified : 2019-08-23 09:46
@REM  Description   : ????
@REM  -------------------------------------------------------

@echo off
@chcp 65001 > nul
setlocal EnableDelayedExpansion

::set CURRENT_TIME=%time:~0,2%%time:~3,2%%time:~6,2%
::set UNIQUE_NAME=startup%CURRENT_TIME%.bat
::title=%UNIQUE_NAME%

if not defined JAVA_HOME (
    echo ????????java?????????
    pause
    exit 1
)

:: ??????????
cd %~dp0\..
for /f "delims=" %%t in ( 'CHDIR' ) do set DEPLOY_PATH=%%t
set BAT_PATH=%DEPLOY_PATH%\bin
echo BAT_PATH=%BAT_PATH%

:: ?????jar?
if not exist %DEPLOY_PATH%\lib (
	echo %DEPLOY_PATH%\lib??????
	pause
	exit 1
)
for /f "delims=" %%t in ('DIR %DEPLOY_PATH%\lib\*.jar /AA /B ^| FIND /C "jar"') do set FILE_QUANTITY=%%t
if %FILE_QUANTITY% neq 1 (
	echo %DEPLOY_PATH%\lib????????????
	pause
	exit 1
)
for /f "delims=" %%t in ('DIR %DEPLOY_PATH%\lib\*.jar /AA /B') do set JAR_NAME=%%t
echo JAR_NAME=%JAR_NAME%

:: ??????????
if exist %BAT_PATH%\pid (
    set /P PID=<%BAT_PATH%\pid
    echo pid=!PID!
    TASKLIST /NH /FI "PID EQ !PID!" | FIND "!PID!" > nul
    if !errorlevel! equ 0 (
        echo ?????????????
        pause
        exit 1
    ) else (
        :: ??pid???????????
        del %BAT_PATH%\pid
    )
)

:: ????????????
call %BAT_PATH%\setEnv.bat
echo JVM_PARAMS=%JVM_PARAMS%

:: ??????
set LOG_PATH=%DEPLOY_PATH%\logs
echo LOG_PATH=%LOG_PATH%

:: ??????????
set APP_OPTION=-Duser.dir=%DEPLOY_PATH% -Dlog.path=%LOG_PATH%
echo APP_OPTION=%APP_OPTION%

:: ?????pid
::for /f "tokens=2 USEBACKQ" %%f in (`TASKLIST /NH /FI "WINDOWTITLE EQ %UNIQUE_NAME%"`) do set PID=%%f
::echo %PID% > %BAT_PATH%\pid
::java %JVM_PARAMS%  %APP_OPTION% -jar %DEPLOY_PATH%\lib\%JAR_NAME% > %BAT_PATH%\serverStartup.log 2>&1
start java %JVM_PARAMS%  %APP_OPTION% -jar %DEPLOY_PATH%\lib\%JAR_NAME%
for /f "tokens=1" %%t in ('jps ^| find "%JAR_NAME%"') do set PID=%%t
echo %PID% > %BAT_PATH%\pid