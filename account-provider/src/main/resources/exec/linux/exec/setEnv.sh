#!/bin/bash
# -------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2018-12-29 16:25
# Filename      : setEvn.sh
# Description   : 设置JVM变量脚本
# -------------------------------------------------------

#JVM_PARAMS="-Xmx128m -Xms128m -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=55524,server=y,suspend=n"
JVM_PARAMS="-server -Xms128m -Xmx256m"
export JVM_PARAMS