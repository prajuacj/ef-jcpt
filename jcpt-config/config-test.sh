APP_DIR=$(cd `dirname $0`; pwd)
COMPILE_DIR=/opt/jcpt/code
APP_NAME=jcpt-config
APP_VER=0.0.1-SNAPSHOT
APP_MAIN_CLASS=com.ef.jcpt.config.ConfigServerApplication

#set java home
export JAVA_HOME=/usr/local/java/jdk1.8.0_144

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx512m -Xms512m -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms256m -Xmx256m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

usage() {
    echo "Usage: sh "$APP_NAME".sh [start|stop|check|deploy|kill]"
    exit 1
}


kills(){
    tpid=`ps -ef|grep $APP_MAIN_CLASS|grep -v grep|grep -v kill|awk '{print $2}'`
    if [[ $tpid ]]; then
        echo 'Kill Process!'
        kill -9 $tpid
    fi
}

start(){
    rm -f $APP_DIR/tpid
    nohup $JAVA_HOME/bin/java $JAVA_MEM_OPTS -cp $APP_DIR/jar/.:$APP_DIR/jar/lib/* "$APP_MAIN_CLASS" > $APP_DIR/logs/"$APP_NAME".log &
    echo $! > $APP_DIR/tpid
    var1=1
    while [ $var1 -lt 10 ]
    do
     success=`grep 'APP Start ON SpringBoot Success' $APP_DIR/logs/"$APP_NAME".log`
     if [[ $success ]]; then
       echo $APP_NAME "APP Start ON SpringBoot Success"
       break
     fi
     fail=`grep 'APP Start ON SpringBoot Failed' $APP_DIR/logs/"$APP_NAME".log`
     if [[ $fail ]]; then
       echo $APP_NAME "APP Start ON SpringBoot Failed"
       break
     fi

    done    
}

stop(){
        tpid1=`ps -ef|grep $APP_MAIN_CLASS|grep -v grep|grep -v kill|awk '{print $2}'`
    echo tpid1-$tpid1
        if [[ $tpid1 ]]; then
        echo 'Stop Process...'
        kill -15 $tpid1
    fi
    sleep 5
    tpid2=`ps -ef|grep $APP_MAIN_CLASS|grep -v grep|grep -v kill|awk '{print $2}'`
        echo tpid2-$tpid2
    if [[ $tpid2 ]]; then
        echo 'Kill Process!'
        kill -9 $tpid2
    else
        echo 'Stop Success!'
    fi

}

check(){
    tpid=`ps -ef|grep $APP_MAIN_CLASS|grep -v grep|grep -v kill|awk '{print $2}'`
    if [[ $tpid ]]; then
        echo 'App is running.'
    else
        echo 'App is NOT running.'
    fi
}

#----------code部署-------

deploy() {
    kills
    cd $APP_DIR/
    rm -rf $APP_DIR/$APP_NAME-$APP_VER.jar
    rm -rf $APP_DIR/jar/*
    
    cp $COMPILE_DIR/$APP_NAME/target/$APP_NAME-$APP_VER.jar $APP_DIR/
    sleep 3
	unzip $APP_DIR/$APP_NAME-$APP_VER.jar -d $APP_DIR/jar/

    echo "code deploy is ok"
}

case "$1" in
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    "check")
        check
        ;;
    "kill")
        kills
        ;;
    "deploy")
        deploy
        ;;
    *)
        usage
        ;;
esac


