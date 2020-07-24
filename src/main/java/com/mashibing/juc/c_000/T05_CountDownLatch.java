package com.mashibing.juc.c_000;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

public class T05_CountDownLatch {
    volatile List lists=new ArrayList();
    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }
    static Thread t1=null,t2=null;
    public static void main(String[] args) {
        T05_CountDownLatch c=new T05_CountDownLatch();
        CountDownLatch latch=new CountDownLatch(1);
        new Thread(()->{
            System.out.println("t2启动");
            if (c.size()!=5){
                LockSupport.park();
            }
            System.out.println("t2结束");
            LockSupport.unpark(t1);
        },"t2 启动").start();

        new Thread(()->{
            System.out.println("t1 启动");
            for (int i=0;i<10;i++){
                c.add(new Object());
                System.out.println("add "+i);
                if (c.size()==5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
//                    //暂停t1线程
//                    latch.countDown();
                }
            }
        },"t1 启动").start();

    }


}
