package com.example.solid.dip;

interface Database {
    void saveOrder();
}

class MySQLDatabase implements Database {
    @Override
    public void saveOrder() {
        System.out.println("Guardando pedido en MySQL...");
    }
}

class OrderProcessor {
    private Database database;

    public OrderProcessor(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("La base de datos no puede ser null");
        }
        this.database = database;
    }

    public void processOrder() {
        System.out.println("Procesando pedido...");
        database.saveOrder();
    }
}

public class GoodExample {
    public static void main(String[] args) {
        Database database = new MySQLDatabase();
        OrderProcessor orderProcessor = new OrderProcessor(database);
        orderProcessor.processOrder();
    }

}
