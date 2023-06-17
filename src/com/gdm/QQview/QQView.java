package com.gdm.QQview;
import com.gdm.Clientservice.ClientService;
import com.gdm.QQCommon.Message;

import java.io.IOException;
import java.util.Scanner;

public class QQView {
    private boolean loop=true;
    private String key="";
    private String key0="";
    Scanner scanner = new Scanner(System.in);
    
    //用于验证ID，密码,以及使用各种方法
    private ClientService clientService = new ClientService();

    //main
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        new QQView().mainMenu();
        System.out.println("客户端退出系统");
    }




    private void mainMenu() throws IOException, ClassNotFoundException {

        while (loop) {
            System.out.println("=============欢迎网络通信系统==============");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t2 退出系统");
            System.out.print("请输入你的选择");
            //
            key = scanner.next();
            //
            switch (key) {
                case "1":
                    //while (loop){//重复验证
                    System.out.println("请输出用户ID：");
                    String UserId = scanner.next();
                    System.out.println("请输出用户密码：");
                    String Password = scanner.next();

                    //如果输入正确进入二级页面
                    if (clientService.check(UserId, Password)) {
                        System.out.println("========欢迎用户" + UserId + "============");
                        //进入二级页面1 登录系统
                        while (loop) {
                            System.out.println("========网络通信系统二级页面用户：" + UserId + "============");
                            System.out.println("\t\t1 显示在线用户列表");
                            System.out.println("\t\t2 群发消息");
                            System.out.println("\t\t3 私聊消息");
                            System.out.println("\t\t4 发送文件");
                            System.out.println("\t\t9 退出系统");
                            System.out.print("请输入你的选择");
                            //
                            key = scanner.next();
                            //
                            switch (key) {

                                case "1"://显示在线用户列表
                                    //System.out.println("在线用户:");
                                    clientService.onlineFriendList();
                                    break;
                                case "2"://群聊
                                    System.out.println("请输入想对大家说的话：");
                                    String content2 = scanner.next();
                                    //群发
                                    clientService.sendMessageToAll(content2,UserId);
                                    break;
                                case "3":
                                    System.out.println("请选择私聊的用户号：");
                                    String getter = scanner.next();
                                    System.out.println("请输入聊天内容：");
                                    String content = scanner.next();
                                    //将消息发给服务端
                                    clientService.sendMessageToOne(content,UserId,getter);
                                    System.out.println("私聊消息:");
                                    break;
                                case "4"://发文件
                                    System.out.println("请把文件发给你需要的用户：");
                                    String getter4 = scanner.next();
                                    System.out.println("请输入发送文件的路径(例如：d:\\xx.jpg)：");
                                    String scr = scanner.next();
                                    System.out.println("请输入对方接收文件的路径(例如：d:\\xx.jpg)：");
                                    String dest = scanner.next();
                                    clientService.sendFileToAll(scr,dest,UserId,getter4);
                                    break;
                                case "9":
                                    //无异常退出
                                    clientService.logout();
                                    System.out.println("退出系统");
                                    System.out.println("退出系统:");
                                    loop = false;
                                    break;
                            }//二级选择
                        }//while
                    }//二级页面if
                    else {
                        System.out.println("===="+UserId+"==登录失败======");
                    }//二级页面
                    //case "1":
                    break;
                case "9":
                    //此时如果退出，不要熄灭线程，因为没有线程的产生
                    System.out.println("退出系统");
                    loop = false;
                    break;

            }
        }
    }//mainMenu()
}