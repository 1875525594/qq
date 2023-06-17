package com.gdm.QQCommon;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID=1L;
    private String sender;//发送者
    private String getter;//接收者
    private String content;//消息内容
    private String senTime;//发送时间
    private String mesType;//消息类型

    //文件相关成员
    private byte[] fileByte;
    private int fileLen=0;//文件长度
    private String dest;//目的地址
    private String scr;//源地址

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getScr() {
        return scr;
    }

    public void setScr(String scr) {
        this.scr = scr;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenTime(String string) {
        return senTime;
    }

    public void setSenTime(String senTime) {
        this.senTime = senTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
