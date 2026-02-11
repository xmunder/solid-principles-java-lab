package com.example.solid.isp;

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Bot implements Workable {
    @Override
    public void work() {
        System.out.println("Trabajando...");
    }
}

class Developer implements Workable, Eatable {
    @Override
    public void work() {
        System.out.println("Escribiendo código...");
    }

    @Override
    public void eat() {
        System.out.println("Comiendo...");
    }
}

public class GoodExample {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.work();

        Developer developer = new Developer();
        developer.work();
        developer.eat();
    }
}
