package com.management.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CountDownLatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sun.misc.PostVMInitHook.run;

/**
 * @Author xiqiuwei
 * @Date Created in 12:57 2019/10/26
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("base")
public class CountDownLatchController {
    // 模拟10个线程
    private static final int THREADCOUNT = 10;

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();//创建一个线程池
        final CountDownLatch mainThread = new CountDownLatch(1);// 主线程
        final CountDownLatch childThread = new CountDownLatch(THREADCOUNT);// 子线程
        for (int i = 0; i < THREADCOUNT; i++) {
            Runnable runnable = () -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正准备接受命令");
                    mainThread.await();// 子线程等待主线程唤醒
                    System.out.println("线程" + Thread.currentThread().getName() + "已接受命令");
                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println("线程" + Thread.currentThread().getName() + "回应命令处理结果");
                    childThread.countDown();// 这个时候子线程-3 childThread为0后继续往下执行主线程                     
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);//为线程池添加任务  
        }
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("主线程" + Thread.currentThread().getName() + "即将发布命令");
            mainThread.countDown();//主线程唤醒子线程，mainThread -1为0时子线程开启  
            System.out.println("主线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            childThread.await();// 等待子线程来唤醒
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();//任务结束，停止线程池的所有线程  
    }

    @GetMapping("/index")
    public ModelAndView testMethod(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        return model;
    }
}
