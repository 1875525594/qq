package com.gdm.QQCommon;

public  interface MessageType {
     String MESSAGE_LOGIN_SUCCEED ="1";//表示登录成功
     String MESSAGE_LOGIN_FAIL ="2";//表示登录失败
     String MESSAGE_COMMON ="3";//表示普通消息包
     String MESSAGE_GET_ONLINE_FRIEND ="4";//客户要求返回在线用户列表
     String MESSAGE_RET_ONLINE_FRIEND ="5";//服务器返回在线用户列表
     String MESSAGE_CLIENT_EXIT ="6";//客户端要求退出
     String MESSAGE_COMMON_ALL ="7";//群发消息
     String MESSAGE_FILE ="8";//文件消息
     String MESSAGE_NEWS ="9";//文件消息
}


