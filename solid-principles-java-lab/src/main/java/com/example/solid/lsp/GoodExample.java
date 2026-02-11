package com.example.solid.lsp;

interface Refuelable  {
    void refuel();
}

interface Drivable  {
    void drive();
}

class Car implements Refuelable, Drivable {
    @Override
    public void refuel() {
        System.out.println("Tanqueando...");
    }

    @Override
    public void drive() {
        System.out.println("Conduciendo...");
    }
}

class ElectricCar implements Drivable {
    @Override
    public void drive() {
        System.out.println("Conduciendo un coche eléctrico...");
    }
}

public class GoodExample {
    public static void main(String[] args) {
        Car car = new Car();
        car.refuel();
        car.drive();

        ElectricCar electricCar = new ElectricCar();
        electricCar.drive();
    }
}
