@REM  -------------------------------------------------------
@REM  Author        : MinChiang
@REM  Email         : 448725235@qq.com
@REM  Last modified : 2019-08-23 09:46
@REM  Description   : ??JVM????
@REM  -------------------------------------------------------

@echo off
@chcp 65001 > nul
setlocal EnableDelayedExpansion

::set JVM_PARAMS=-Xmx128m -Xms128m -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=55524,server=y,suspend=n
set JVM_PARAMS=-server -Xms128m -Xmx256m