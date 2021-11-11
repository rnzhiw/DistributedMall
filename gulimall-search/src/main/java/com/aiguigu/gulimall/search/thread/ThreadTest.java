package com.aiguigu.gulimall.search.thread;

import java.util.concurrent.*;

public class ThreadTest {

    // 当前系统中池只有一两个，每个异步任务，提交给线程池让他自己去执行就行
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main..........start............");
//        CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程为：" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("运行结果为：" + i);
//        },executor);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程为：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果为：" + i);
            return i;
        }, executor).whenComplete((res, execption) -> {
            System.out.println("异步任务完成了。。。。。。。。。" + res + "异常是。。。。。。" + execption);

        });
        Integer integer = future.get();
        System.out.println("main..........end............" + integer);
    }

    public  void Thread(String[] args) {

        System.out.println("main..........start............");

//        Thread01 thread01 = new Thread01();
//        thread01.start();

//        Runable01 runable01 = new Runable01();
//        new Thread(runable01).start();

//        FutureTask<Integer> futureTask = new FutureTask<>(new callable01());
//        new Thread(futureTask).start();

//        service.execute(new Runable01());



        System.out.println("main..........end............");

    }

    public static class Thread01 extends Thread {

        @Override
        public void run() {
            System.out.println("当前线程为：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果为：" + i);
        }
    }

    public static class Runable01 implements Runnable {

        @Override
        public void run() {
            System.out.println("当前线程为：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果为：" + i);
        }
    }

    public static class callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程为：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果为：" + i);

            return i;
        }
    }
}
