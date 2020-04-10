#!/bin/bash

# ---------------------------------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2019-03-06 19:26
# Filename      : serviceBackup.sh
# Description   : 备份多个目录中的数据
# ---------------------------------------------------------------------------------

# 确定当前脚本执行路径
workDir=$(cd "$(dirname "$0")";pwd)

# 源目录保留5天的数据
srcBackupDate=5
# 目标目录保留30天的数据
targetBackupDate=30
# 待备份的文件后缀
backupSuffix='gz'
# 需要备份的微服务名称
services=(eureka gateway account facility mail)

# 开始备份
for service in ${services[@]};
do
    echo "开始备份微服务${service}的日志"
    source ${workDir}/backup.sh /opt/ezcoding/${service}/logs ${backupSuffix} /opt/backup/${service} ${srcBackupDate} ${targetBackupDate}
done
