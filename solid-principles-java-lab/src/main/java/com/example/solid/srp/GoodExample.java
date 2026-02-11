package com.example.solid.srp;

class Invoice {
    private String customer;
    private double amount;

    Invoice(String customer, double amount) {
        if (customer == null || customer.trim().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede ser nulo o vacío");
        }
        this.customer = customer.trim();

        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        this.amount = amount;
    }

    double getAmount() {
        return amount;
    }

    String getCustomerName() {
        return customer;
    }

    double calculateTotal() {
        return amount * 1.21;
    }
}

class InvoicePrinter {
    void printInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("La factura no puede ser null");
        }
        System.out.println("Factura para: " + invoice.getCustomerName());
        System.out.println("Total: " + invoice.calculateTotal());
    }
}

class InvoiceDatabaseSaver {
    void saveToDatabase() {
        System.out.println("Guardando factura...");
    }
}

public class GoodExample {
    public static void main(String[] args) {
        Invoice invoice = new Invoice("Cristian", 20.0);
        InvoicePrinter printer = new InvoicePrinter();
        printer.printInvoice(invoice);
        InvoiceDatabaseSaver invoiceSaver = new InvoiceDatabaseSaver();
        invoiceSaver.saveToDatabase();
    }
}