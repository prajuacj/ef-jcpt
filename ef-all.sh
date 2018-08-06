SERVICE_NAME=ef-all
CMD=usage
pwd_list="/data/jcpt/config"
pwd_list=$pwd_list" /data/jcpt/service/service-tradecenter /data/jcpt/service/service-user"
pwd_list=$pwd_list" /data/jcpt/wi/wi-tradecenter /data/jcpt/wi/wi-user"

usage() {
    echo "service list,please confirm"
    for xpwd in $pwd_list
    do 
      echo $xpwd
      echo ${xpwd##*/}"-test.sh"
    done
    echo "Usage: sh "$SERVICE_NAME".sh [start|stop|check|deploy|kill]"
    exit 1
}
changeV(){
  for xpwd in $pwd_list
    do 
      cd $xpwd
      cp ${xpwd##*/}"-test.sh" ${xpwd##*/}"-test.sh.bak"
      sed -i s/${1}/${2}/ ${xpwd##*/}"-test.sh"
      echo s/${1}/${2}/ ${xpwd##*/}"-test.sh"
    done 
    exit 1
}

do_cmd(){
for xpwd in $pwd_list
    do
      #echo $xpwd
      #echo ${xpwd##*/}
      echo $CMD ${xpwd##*/} "start"
      cd $xpwd
      sh ${xpwd##*/}-test.sh $CMD
      echo $CMD ${xpwd##*/} "finished-----------------------------"
      sleep 2
    done 
#echo $CMD "caifubao-config start"
 #cd /data/jcpt/config/caifubao-config
 #sh caifubao-config-test.sh $CMD
 #echo $CMD "caifubao-config finished"
}


case "$1" in
    "start")
        CMD=start
        do_cmd
        ;;
    "stop")
        CMD=stop
        do_cmd
        ;;
    "check")
        CMD=check
        do_cmd
        ;;
    "kill")
       CMD=kill
        do_cmd
        ;;
    "deploy")
        CMD=kill
        do_cmd
        CMD=deploy
        do_cmd
        ;;
    "changeV")
        CMD=changeV
        changeV $2 $3
        ;;
    *)
        CMD=usage
        usage
        ;;
esac

