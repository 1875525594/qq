package com.gdm.Clientservice;


import com.gdm.QQCommon.Message;
import com.gdm.QQCommon.MessageType;
import sun.awt.geom.AreaOp;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**根据客户接收到的各种Message类型的对象，进行判断该执行哪项功能
 * 1，在线用户列表的类型
 * 2，私聊
 * 3，群发消息
 * 4，文件
 * 5，推送新闻
 * */
public class ClientConnectServerThread extends Thread {
    //线程的socket
    private Socket socket;
    //构造器
    public ClientConnectServerThread(Socket socket){
        this.socket=socket;
    }

    //线程
    @Override
    public void run() {
        //线程通信
        while (true){
            try {

                System.out.println("客户端，等待服务器数据");
                //获取服务器的消息
                ObjectInputStream ois
                        = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message对象，会一直阻塞
                //接收message对象
                Message message = (Message)ois.readObject();



                //判断message的类型，
                // 如果是服务器返回”在线用户列表的类型“
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    //取出在线用户列表，Content()：一个带有多个空格的多个id字符串
                    String[] userLine = message.getContent().split(" ");//split()将此字符串拆分为给定相匹配。
                    System.out.println("\n=============在线用户列表=================");
                    for (int i = 0; i <userLine.length ; i++) {
                        System.out.println("用户："+userLine[i]);
                    }
                }

                //如果是私聊的普通消息
                else if (message.getMesType().equals(MessageType.MESSAGE_COMMON)){
                    System.out.println("\n"+message.getSender()+"对："+message.getGetter()+"讲"+message.getContent());
                }

                //群发消息
                else if(message.getMesType().equals(MessageType.MESSAGE_COMMON_ALL)){
                    System.out.println("\n"+message.getSender()+"对大家讲"+message.getContent());
                }

                //文件消息
                else if(message.getMesType().equals(MessageType.MESSAGE_FILE)){
                    System.out.println("\n"+message.getSender()+"给"+message.getContent()+"发文件从"+message.getScr()+"到"+message.getDest());

                    //接收者，接收文件
                    //取出message的字节数组，通过流写入磁盘
                    FileOutputStream fileOutputStream
                            = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileByte());
                    fileOutputStream.close();
                    System.out.println("文件保存成功。。。。");
                }
                //推送新闻
                else if (message.getMesType().equals(MessageType.MESSAGE_NEWS)){
                    System.out.println("\n"+message.getSender()+"对："+message.getGetter()+"讲"+message.getContent());
                }
                //其他
                else {
                    System.out.println("000");
                }

            }//try
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//run



    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
