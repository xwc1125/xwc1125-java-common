#!/bin/bash
#定时检测

pos_num=`nmap 远程IP地址 -p 端口号|sed -n "6p"|grep open|wc -l`
yfk_num=`nmap 远程IP地址 -p 端口号|sed -n "6p"|grep open|wc -l`

if [ $pos_num -lt 1 ]
then echo “收单系统端口故障,请处理”|mail -s "系统邮件,请勿回复" xxx@qq.com
fi

if [ $yfk_num -lt 1 ]
then echo “预付卡系统端口故障,请处理”|mail -s "系统邮件,请勿回复" xxx@qq.com
fi

#通过nmap检测远程ip的端口是否通,如果通则不进行任何操作,如果不同则发送邮件
#做到定时任务crontab即可
#PS:如果没有mail命令则yum安装mailx