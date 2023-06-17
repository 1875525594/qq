package com.gdm.Clientservice;


import com.gdm.QQCommon.Message;
import com.gdm.QQCommon.MessageType;
import com.gdm.QQCommon.User;
import com.sun.xml.internal.ws.api.config.management.policy.ManagedClientAssertion;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**1，验证ID，密码，check(String userId,String userWsd)
 * 2，要求服务器返回客户的在线消息onlineFriendList()
 * 3，退出客户端方法，发送message给服务器logout()
 * 4，发送私聊消息sendMessageToOne(String content,String senderId,String getter)
 * 5，群发消息sendMessageToAll(String content,String senderId)
 * 6，发送文件类型sendFileToAll(String src,String dest,String senderId,String getter)
 * */
public class ClientService {

    private User user = new User();
    private Socket socket;

    //认证成功，返回socket对象
    public boolean check(String userId,String userWsd)  {
        boolean b = false;
        user.setUserId(userId);//-->UserId
        user.setUserwsd(userWsd);//-->Password
        try {
            //连接9989的服务端，返回socket
            socket = new Socket(InetAddress.getLocalHost(), 9989);
            //客户对外输出user对象，为了验证客户ID，密码
            ObjectOutputStream oos =
                    new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);//写入通道

            //客户读取服务器输入message对象
          ObjectInputStream ois =
                    new ObjectInputStream(socket.getInputStream());
            Message message = (Message)ois.readObject();

            //根据返回message对象验证，客户是否身份合法
            if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                //启动ClientConnectServerThread构造器
                //登录成功，创建线程，维护该socket
                ClientConnectServerThread clientConnectServerThread
                        = new ClientConnectServerThread(socket);
                //启动线程
                clientConnectServerThread.start();

                //为了客户端的扩张,将线程加入到集合
                MangeClientConnectServerThread.add(userId,clientConnectServerThread);

                b=true;
            }
            else {
                //登录失败,关闭连接
                socket.close();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }//check


    //要求服务器返回客户的在线消息
    public void onlineFriendList(){
        //发送一个消息类型,类型是String MESSAGE_GET_ONLINE_FRIEND ="4"，客户要求返回在线列表;
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());

        //发送给服务器
        try {
            ////得到 socket对应的ObjectOutputStream
            //ObjectOutputStream oos
            //        = new ObjectOutputStream(
            //                MangeClientConnectServerThread.
            //                        getClientConnectServerThread(
            //                                user.getUserId()).
            //                                        getSocket().
            //                                            getOutputStream());
            //上面等价于
            //从管理线程中，根据ID找到对应线程
            ClientConnectServerThread clientConnectServerThread
                    =MangeClientConnectServerThread.getClientConnectServerThread(user.getUserId());
            //获取线程后，找到该线程的socket
            Socket socket = clientConnectServerThread.getSocket();
            //根据socket，找到对应的输出流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);//写入通道
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//onlineFriendList()


    //退出客户端方法，发送message给服务器
    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        //指定哪个客户端
        message.setSender(user.getUserId());


        try {
            //发送message
            //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //下面的写法和上面的一样，下面的好点
            ObjectOutputStream oos
                    = new ObjectOutputStream(MangeClientConnectServerThread
                    .getClientConnectServerThread(user.getUserId())
                    .getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getUserId()+"退出了");
            System.exit(0);//结束进程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//logout()


    //发送私聊消息
    public void sendMessageToOne(String content,String senderId,String getter){
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMMON);
        message.setSender(senderId);
        message.setGetter(getter);
        message.setContent(content);
        message.getSenTime(new Date().toString());
        System.out.println("'"+senderId+"'"+"对"+"'"+getter+"'"+"说"+content);

        //发送message给服务器
        try {
            ObjectOutputStream oos
                    = new ObjectOutputStream(MangeClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            //写入通道
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//sendMessageToOne

    //群发消息
    public void sendMessageToAll(String content,String senderId){
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMMON_ALL);
        message.setSender(senderId);
        //message.setGetter(getter);
        message.setContent(content);
        message.getSenTime(new Date().toString());
        System.out.println("'"+senderId+"'"+"对大家说"+content);
        //发送message给服务器
        try {
            ObjectOutputStream oos
                    = new ObjectOutputStream(MangeClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            //写入通道
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//群发消息

    //发送文件类型，：把message的成员设置好
    public void sendFileToAll(String src,String dest,String senderId,String getter){
        //读取文件到程序
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE);
        message.setSender(senderId);
        message.setGetter(getter);
        message.setScr(src);
        message.setDest(dest);
        //从磁盘获取文件资源，为的得到字节数组
        FileInputStream fileInputStream=null;
        //new File(src)通过将给定的路径名字符串转换为抽象路径名来创建新的File实例
        byte[] fileByte = new byte[(int)new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            //将src读到数组字节数组fileByte
            fileInputStream.read(fileByte);
            //将设置文件数组内容为fileByte
            message.setFileByte(fileByte);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n"+senderId+"给"+getter+"发送从"+src+"到对方的"+dest);
        try {
            //发送
            ObjectOutputStream oos
                    = new ObjectOutputStream(MangeClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            //写入通道
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//sendFileToAll()



}
