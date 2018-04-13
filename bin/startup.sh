#! /bin/sh
#引入变量文件
. env.sh
#执行脚本
if [ ! -d "$root" ]; then
    exec `mkdir "$root"`
fi
cd $root
project=$root$projectName
if [ ! -d "$project" ]; then  
	exec `git clone "$gitUrl"`
fi
cd $project

echo "git checkout branch origin/master"
git checkout master
echo "git fetch"
git fetch
echo "git pull"
git pull

echo "compile and skip the test units"
mvn clean package -Dmaven.test.skip=true
#编译生成war包地址
warSource=$root$projectName"/target/"$projectName".war"
echo $warSource

#由于服务器开启热部署,此处不需要重启服务器
#关闭tomcat服务器
# shutDown=$tomcatBin"shutdown.sh"
# echo "close the tomcat"
# bash $shutDown
#占停10秒
#for i in {1..10}
#do
#    echo $i"s"
#    sleep 1s
#done
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

cp $warSource $warPath

#开启tomcat服务器
# startUp=$tomcatBin"startup.sh"
# echo "start the tomcat"
# bash $startUp
