#!/bin/bash
# -------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2018-12-29 16:25
# Filename      : shutdown.sh
# Description   : 停止脚本
# -------------------------------------------------------

# 确定当前脚本执行路径
DEPLOY_PATH=$(cd "$(dirname "$0")/..";pwd)
SHELL_PATH=${DEPLOY_PATH}/bin

if [[ ! -f "${SHELL_PATH}/pid" ]]; then
	echo "应用未启动"
	exit 1
fi

PID=`cat ${SHELL_PATH}/pid`
kill -9 ${PID} >> /dev/null 2>&1
if [[ $? -eq 0 ]]; then
	echo "应用${PID}停止成功"
else
	echo "应用${PID}停止失败"
fi
rm ${SHELL_PATH}/pid