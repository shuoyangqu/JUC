package com.mashibing.juc.c_000;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程连接池
 */

public class T_05_00_Pool {
    static class Task implements Runnable{
        private int i;
        public Task(int i){
            this.i=i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"-------Task"+i);

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task[i="+i+"]";
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor tpc=new ThreadPoolExecutor(2,4,60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i=0;i<8;i++){
            tpc.execute(new Task(1));
        }
        System.out.println(tpc.getQueue());
        tpc.execute(new Task(100));
        System.out.println(tpc.getQueue());
        tpc.shutdown();
    }

}
