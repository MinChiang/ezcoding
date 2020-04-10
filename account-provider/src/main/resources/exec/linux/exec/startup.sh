#!/bin/bash
# -------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2018-12-29 16:25
# Filename      : startup.sh
# Description   : 启动脚本
# -------------------------------------------------------

# 寻找可执行的java程序文件
if [[ -x "$JAVA_HOME/bin/java" ]]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=`which java`
fi

if [[ ! -x "$JAVA" ]]; then
    echo "无法找到可执行的java或没有配置环境变量"
    exit 1
fi

# 确定当前脚本执行路径
DEPLOY_PATH=$(cd "$(dirname "$0")/..";pwd)
echo DEPLOY_PATH=${DEPLOY_PATH}
SHELL_PATH=${DEPLOY_PATH}/bin

# 确定运行的jar包
if [[ ! -d "${DEPLOY_PATH}/lib" ]]; then
	echo "${DEPLOY_PATH}/lib文件夹不存在"
	exit 1
fi
if [[ `find ${DEPLOY_PATH}/lib -name *.jar | wc -l` -ne 1 ]]; then
	echo "${DEPLOY_PATH}/lib中必须有且只有一个启动包"
	exit 1
fi
JAR_NAME=`find ${DEPLOY_PATH}/lib -name *.jar`
echo JAR_NAME=${JAR_NAME}

# 检验应用是否已经启动
if [[ -f "${SHELL_PATH}/pid" ]]; then
	echo "应用已经在运行中，无法启动"
	exit 1
fi

# 执行设置环境变量的子脚本
source ./setEnv.sh
echo JVM_PARAMS=${JVM_PARAMS}

# 日志文件路径
LOG_PATH=${DEPLOY_PATH}/logs
echo LOG_PATH=${LOG_PATH}

# 更改程序所在目录路径
APP_OPTION="-Duser.dir=${DEPLOY_PATH} -Dlog.path=${LOG_PATH}"
#export APP_OPTION

# 启动并记录pid
>${SHELL_PATH}/serverStartup.log
nohup ${JAVA_HOME}/bin/java ${JVM_PARAMS} ${APP_OPTION} -jar ${JAR_NAME} > ${SHELL_PATH}/serverStartup.log 2>&1 & echo $! > pid
tail -f ${SHELL_PATH}/serverStartup.log