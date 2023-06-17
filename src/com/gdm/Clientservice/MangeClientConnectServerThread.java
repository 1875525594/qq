package com.gdm.Clientservice;

import java.util.HashMap;

public class MangeClientConnectServerThread {
    //线程加入集合，key：ID，value：线程,  static 可以方便直接把hashMap放到本类方法中
    private static HashMap<String, ClientConnectServerThread> hashMap= new HashMap<>();

    //将某线程加入到集合
    public static void add(String userId, ClientConnectServerThread clientConnectServerThread){
        hashMap.put(userId,clientConnectServerThread);
    }


    //ID可以得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hashMap.get(userId);
    }
}
