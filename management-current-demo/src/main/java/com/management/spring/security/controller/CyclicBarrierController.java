package com.management.spring.security.controller;

import java.util.concurrent.*;

/**
 * @Author xiqiuwei
 * @Date Created in 23:10 2019/11/18
 * @Description
 * @Modified By:
 */
public class CyclicBarrierController {
    public static void main(String[] args) {
        CountDownLatch mainThread = new CountDownLatch(1);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            try {
                mainThread.await();
                System.out.println("主线程执行完成进入子线程111111");
                try {
                    System.out.println("子线程1执行任务");
                    // 模拟子线程1的业务逻辑执行时间
                    Thread.sleep(1000);
                    // 制造异常破坏屏障
                    int i = 1/0;
                }catch(Exception e){
                    System.out.println("子线程1抛出异常");
                    System.out.println("子线程1回滚操作");
                    // 初始化循环屏障所有等待的子线程抛BrokenBarrierException异常
                    cyclicBarrier.reset();
                }
                cyclicBarrier.await();
                System.out.println("子线程1执行成功============================");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(()->{
            try {
                mainThread.await();
                System.out.println("主线程执行完成进入子线程222222");
                System.out.println("子线程2执行任务");
                cyclicBarrier.await();
                System.out.println("子线程2执行成功============================");
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("子线程2回滚操作");
                e.printStackTrace();
            }
        });
        // 模拟主线程逻辑执行时间
        try {
            Thread.sleep(5000);
            System.out.println("执行主线程==============================");
            mainThread.countDown();
            } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
