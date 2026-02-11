package com.example.solid.ocp;

interface DiscountStrategy {
    double calculateDiscount(double price);

    default void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                "El precio no puede ser negativo. Precio: " + price
            );
        }
    }
}

class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        validatePrice(price);
        return price * 0.1;
    }
}

class VipCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        validatePrice(price);
        return price * 0.2;
    }
}

class DiscountCalculator {
    private DiscountStrategy discountStrategy;

    DiscountCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    double calculate(double price) {
        return discountStrategy.calculateDiscount(price);
    }

    double totalAfterDiscount(double price) {
        return price - calculate(price);
    }
}

public class GoodExample {
    public static void main(String[] args) {
        DiscountCalculator regularCustomerCalculator = new DiscountCalculator(new RegularCustomerDiscount());
        double regularDiscount = regularCustomerCalculator.calculate(10);
        System.out.println("Descuento Cliente Regular: " + regularDiscount);
        System.out.println("Total después de descuento Cliente Regular: " + regularCustomerCalculator.totalAfterDiscount(10));

        DiscountCalculator vipCustomerCalculator = new DiscountCalculator(new VipCustomerDiscount());
        double vipDiscount = vipCustomerCalculator.calculate(10);
        System.out.println("Descuento Cliente VIP: " + vipDiscount);
        System.out.println("Total después de descuento Cliente VIP: " + vipCustomerCalculator.totalAfterDiscount(10));
    }
}
