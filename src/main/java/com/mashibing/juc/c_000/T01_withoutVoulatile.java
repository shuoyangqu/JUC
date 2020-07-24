package com.mashibing.juc.c_000;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class T01_withoutVoulatile {
    //添加voilatile，使t2能够得到通知
   volatile List list=new ArrayList();
    public void add(Object o){
        list.add(o);
    }
    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        T01_withoutVoulatile c=new T01_withoutVoulatile();
        final Object lock=new Object();

        new Thread(()->{
            synchronized (lock){
                System.out.println("t2启动");
                    if (c.size()!=5){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t2结束");
            }
        lock.notify();
        },"t2").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            System.out.println("t1启动");
            synchronized (lock){
                for (int i=0;i<10;i++){
                    c.add(new Object());
                    System.out.println("add"+i);
                    if (c.size()==5){
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();

    }

}
