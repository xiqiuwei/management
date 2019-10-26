package com.management.topdf.demo.entity;

/**
 * @Author xiqiuwei
 * @Date Created in 23:24 2019/9/17
 * @Description
 * @Modified By:
 */

class Animal {
    public void play () {
        System.out.println("玩");
    }
}

class a extends Person {
    @Override
    public void eat() {
        System.out.println("吃饭");
    }
}

class b extends  Person {
    @Override
    public void eat() {
        System.out.println("吃东西");
    }
}

class c extends Animal {
    @Override
    public void play() {
        System.out.println("打篮球");
    }
}


class d extends Animal {
    @Override
    public void play() {
        System.out.println("打羽毛球");
    }
}

public class Test {
    public static void main(String[] args) {
      /*  Person p1 = new a();
        p1.eat();
        p1 = new b();
        p1.eat();*/
        Animal a1 = new c();
        a1.play();
        a1 = new d();
        a1.play();
    }
}
