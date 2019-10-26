package com.management.topdf.demo.cglib;

/**
 * @Author xiqiuwei
 * @Date Created in 22:14 2019/9/8
 * @Description
 * @Modified By:
 */
public class CglibTest {
    public static void main(String[] args) {
        XiQiuWei x = new XiQiuWei();
        XiQiuWei xiQiuWei = (XiQiuWei)new Santuer().getInstance(x);
        xiQiuWei.findGirlFriend();
    }
}
