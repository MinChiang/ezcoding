#!/bin/bash

# ---------------------------------------------------------------------------------
# Author        : MinChiang
# Email         : 448725235@qq.com
# Last modified : 2019-03-05 23:27
# Filename      : backup.sh
# Description   : 生产日志等文件备份脚本，配合crontab使用效果更佳
# 使用说明：
# srcPath	  backupSuffix   targetPath srcRestCount            targetRestCount
# 待备份目录 待备份的文件后缀 目标目录     待备份目录保留多少天的日志  目标目录保留多少天的日志
# 例子：./backup.sh ./logs gz ./backup 2 20
# ---------------------------------------------------------------------------------

cleanBackupDictionary(){
	# 获取待备份的目录中指定后缀的文件个数（需要过滤掉文件夹）
	satisfyCount=`find $1 -maxdepth 1 -name *.$2 -type f -mtime +$3 | wc -l`
	if [[ ${satisfyCount} -gt 0 ]]; then
		# 找出目录中满足要求的文件
		deleteFileList=`find $1 -maxdepth 1 -name *.$2 -type f -mtime +$3 | xargs ls -rt`
		echo -e "准备删除文件：\n${deleteFileList}"
		# 进行真正的删除操作
		rm -rf ${deleteFileList} $3
	fi
}

# 根据文件夹里面的文件个数备份文件
# 参数说明：
# 参数1：待备份的目录文件夹
# 参数2：需要备份文件的后缀名，如：log，注意不能写成*.log
# 参数3：目标备份目录位置
# 参数4：源目录文件的保留个数，推荐以7为最小单位
# 参数5：目标备份目录中备份文件的保留个数，推荐以30为最小单位
backupByFileCount(){
	# 获取待备份的目录中指定后缀的文件个数（需要过滤掉文件夹）
	currentFileCount=`find $1 -maxdepth 1 -name *.$2 -type f | wc -l`
	keepFileCount=$4
	if [[ ${currentFileCount} -gt ${keepFileCount} ]]; then
		# 准备删除的文件数量
		deleteFileCount=$((currentFileCount-keepFileCount))
		# 找出目录中最旧的文件列表
		deleteFileList=`find $1 -maxdepth 1 -name *.$2 -type f | xargs ls -rt | head -n ${deleteFileCount}`
		echo -e "准备删除文件：\n${deleteFileList}"
		# 进行软删除，即把对应的文件移动到备份的目录中
		mv ${deleteFileList} $3
		cleanBackupDictionary $3 $2 $5
	fi
}

# 根据文件夹中文件的修改时间备份文件
# 参数说明：
# 参数1：待备份的目录文件夹
# 参数2：需要备份文件的后缀名，如：log，注意不能写成*.log
# 参数3：目标备份目录位置
# 参数4：距今的天数
# 参数5：目标备份目录中备份文件距今的天数
backupByDateCount(){
	# 获取待备份的目录中指定后缀的文件个数（需要过滤掉文件夹）
	satisfyCount=`find $1 -maxdepth 1 -name *.$2 -type f -mtime +$4 | wc -l`
	if [[ ${satisfyCount} -gt 0 ]]; then
		# 找出目录中满足要求的文件
		deleteFileList=`find $1 -maxdepth 1 -name *.$2 -type f -mtime +$4 | xargs ls -rt`
		echo -e "准备删除文件：\n${deleteFileList}"
		# 进行软删除，即把对应的文件移动到备份的目录中
		mv ${deleteFileList} $3
		cleanBackupDictionary $3 $2 $5
	fi
}

checkDictionaryAndPermission(){
	if [[ ! -d $1 || ! -w $1 || ! -x $1 ]]; then
		echo "$1不是目录，或没有写入执行权限"
		exit 1
	fi
}

createDictionaryIfNotExist(){
	if [[ ! -e $1 ]]; then
		# 文件夹不存在，则创建对应的目录
		mkdir -p $1
		if [[ $? -ne 0 ]]; then
			echo "无法成功创建$1目录"
			exit 1
		fi
	else
		# 需要判断对应文件夹的权限
		checkDictionaryAndPermission $1
	fi
}

checkInteger(){
	expr $1 + 0 &>/dev/null
	if [[ $? -ne 0 ]]; then
		echo "输入参数必须为数字"
		exit 1
	fi
}

if [[ "$#" -lt 4 ]]; then
	echo "缺少执行参数，参数命令：srcPath backupSuffix targetPath srcRestCount [targetRestCount]"
	exit 1
fi

# 入参并且校验各个值的合法性
srcPath=$1
checkDictionaryAndPermission ${srcPath}

backupSuffix=$2

targetPath=$3
if [[ "$srcPath"x = "$targetPath"x ]]; then
	echo "待备份文件夹和文件夹不能相同"
	exit 1
fi
createDictionaryIfNotExist ${targetPath}

srcRestCount=$4
checkInteger ${srcRestCount}

targetRestCount=$5
if [[ -n "$targetRestCount" ]]; then
	checkInteger ${targetRestCount}
fi

# 执行函数
backupByDateCount ${srcPath} ${backupSuffix} ${targetPath} ${srcRestCount} ${targetRestCount}