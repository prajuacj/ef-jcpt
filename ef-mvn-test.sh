#!/bin/sh
COMPILE_DIR=/opt/jcpt/code
APP_NAME=caifubao-mvn-test
#set java home
export JAVA_HOME=/usr/local/java/jdk1.8.0_144
MVN_HOME=/usr/local/maven
#GIT_BRANCH=test-mc20180727
GIT_BRANCH=master
GIT_FOLDER="${COMPILE_DIR}/.git"

#--------svn更新--------
svnupdate()
 {
  export LANG=zh_CN.UTF-8
  rm -rf $COMPILE_DIR/
# /usr/bin/svn export --username yuhh --password hhuayu http://10.50.10.205/svn/p2p/project/base-platform/source/caifubao-jcpt $COMPILE_DIR --force
echo $GIT_FOLDER;
if [ ! -d "$GIT_FOLDER" ];then
	echo "clone代码"
	git clone --branch=$GIT_BRANCH --depth=1 https://github.com/prajuacj/xa-eb.git $COMPILE_DIR
else
	echo "pull代码"
	git --git-dir=$GIT_FOLDER pull origin $GIT_BRANCH
fi
git --git-dir=$GIT_FOLDER checkout $GIT_BRANCH
   if [ $? -eq 0 ]
     then
     echo "svn update is ok!"
   else
    echo "svn update is faild!"
    exit
   fi
}

#----------mvn打包-------
mvnpack()
 {
	svnupdate
    cd $COMPILE_DIR/  
    $MVN_HOME/bin/mvn clean package install -Dmaven.test.skip=true -P test

   echo "mvn clean package is ok"
}

mvnpack
