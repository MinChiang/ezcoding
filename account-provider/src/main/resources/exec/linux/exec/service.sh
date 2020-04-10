#!/bin/bash
# -------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2018-12-29 16:25
# Filename      : service.sh
# Description   : 综合服务脚本，用于启动、停止、重启应用
# -------------------------------------------------------

# 确定当前脚本执行路径
DEPLOY_PATH=$(cd "$(dirname "$0")/..";pwd)
SHELL_PATH=${DEPLOY_PATH}/bin

if [[ "$#" -ne 1 ]]; then
	echo "缺少执行参数，参数命令：start | stop | restart"
	exit 1
fi

case "$1" in
	"start")
		echo "----------------------------启动应用----------------------------"
		sh ${SHELL_PATH}/startup.sh
		;;
	"stop")
		echo "----------------------------停止应用----------------------------"
		sh ${SHELL_PATH}/shutdown.sh
		;;
	"restart")
		echo "----------------------------重启应用----------------------------"
        sh ${SHELL_PATH}/shutdown.sh
        sh ${SHELL_PATH}/startup.sh
		;;
	*)
		echo "未知参数$1，参数命令：start | stop | restart"
		;;
esac