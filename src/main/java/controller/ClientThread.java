package controller;

import util.JedisUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientThread extends Thread {

    int i = 0;

    public ClientThread(int i) {
        this.i = i;
    }

    public void run() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        JedisUtil.setString("foo", time);
        String foo = JedisUtil.getString("foo");
        System.out.println("【输出>>>>】foo:" + foo + " 第："+i+"个线程" +"当前时间："+ System.currentTimeMillis());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            ClientThread t = new ClientThread(i);
            t.start();
        }
    }
}
