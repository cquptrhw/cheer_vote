#!/usr/bin/env bash
#引入变量文件
. env.sh

#部署war包地址
warPath=$webapps$projectName".war"
if [ -e "$warPath" ]; then
	rm $warPath
fi
#部署项目文件夹
filePath=$webapps$projectName
if [ -d "$filePath" ]; then
	rm -rf $filePath
fi

echo "delete successfully"
