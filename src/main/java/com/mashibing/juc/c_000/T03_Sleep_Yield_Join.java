package com.mashibing.juc.c_000;

import java.util.concurrent.TimeUnit;

/**
 * 线程方法
 */
public class T03_Sleep_Yield_Join {
    public static void main(String[] args) {
//        testSleep();
        testYield();
//        testJoin();
    }
static void testSleep(){
        new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("A"+i);
                try {
//                    Thread.sleep(500);
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

}

/**
 * Yield,就是当前线程正在执行的时候停止下来进入等待队列，回到等待队列里在系统的调度算法里头呢还是依然有可能把你刚回去的这个线程拿回来继续执行，
 * 当然，更大的可能性是把原来等待的那些拿出一个来执行，所以yield的意思是我让出一下CPU，后面你们能不能抢到那我不管
 * */
static void testYield(){
        new Thread(()->{
            for (int i=0;i<100;i++){
                System.out.println("A"+i);
                if (i%10==0) Thread.yield();
            }
        }).start();

        new Thread(()->{}).start();
}

static void testJoin(){
    Thread t1=new Thread(()->{
        for (int i=0;i<10;i++){
            System.out.println("A"+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    Thread t2=new Thread(()->{
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i=0;i<10;i++){
            System.out.println("B"+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    t1.start();
    t2.start();
}

}
