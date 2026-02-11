package com.example.solid.ocp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el principio OCP (Open/Closed Principle)")
class OCPTest {

    private DiscountCalculator regularCalculator;
    private DiscountCalculator vipCalculator;

    @BeforeEach
    void setUp() {
        regularCalculator = new DiscountCalculator(new RegularCustomerDiscount());
        vipCalculator = new DiscountCalculator(new VipCustomerDiscount());
    }

    @Test
    @DisplayName("RegularCustomerDiscount calcula 10% de descuento correctamente")
    void testRegularCustomerDiscountPercentage() {
        double price = 100.0;
        double expectedDiscount = 10.0;
        assertEquals(expectedDiscount, regularCalculator.calculate(price), 0.01);
    }

    @Test
    @DisplayName("VipCustomerDiscount calcula 20% de descuento correctamente")
    void testVipCustomerDiscountPercentage() {
        double price = 100.0;
        double expectedDiscount = 20.0;
        assertEquals(expectedDiscount, vipCalculator.calculate(price), 0.01);
    }

    @Test
    @DisplayName("DiscountCalculator calcula el total después del descuento de cliente regular")
    void testRegularCustomerTotalAfterDiscount() {
        double price = 100.0;
        double expectedTotal = 90.0;
        assertEquals(expectedTotal, regularCalculator.totalAfterDiscount(price), 0.01);
    }

    @Test
    @DisplayName("DiscountCalculator calcula el total después del descuento de cliente VIP")
    void testVipCustomerTotalAfterDiscount() {
        double price = 100.0;
        double expectedTotal = 80.0;
        assertEquals(expectedTotal, vipCalculator.totalAfterDiscount(price), 0.01);
    }

    @Test
    @DisplayName("El descuento se calcula correctamente con diferentes montos")
    void testDiscountCalculationWithVariousPrices() {
        double[] prices = {10.0, 50.0, 100.0, 500.0};
        
        for (double price : prices) {
            double regularDiscount = regularCalculator.calculate(price);
            double expectedRegularDiscount = price * 0.1;
            assertEquals(expectedRegularDiscount, regularDiscount, 0.01,
                        "El descuento regular debe ser el 10% del precio");
            
            double vipDiscount = vipCalculator.calculate(price);
            double expectedVipDiscount = price * 0.2;
            assertEquals(expectedVipDiscount, vipDiscount, 0.01,
                        "El descuento VIP debe ser el 20% del precio");
        }
    }

    @Test
    @DisplayName("El descuento en precio de cero es cero")
    void testDiscountOnZeroPrice() {
        double zeroPrice = 0.0;
        assertEquals(0.0, regularCalculator.calculate(zeroPrice), 0.01);
        assertEquals(0.0, vipCalculator.calculate(zeroPrice), 0.01);
    }

    @Test
    @DisplayName("Se lanza excepción cuando el precio es negativo en cliente regular")
    void testRegularCustomerThrowsExceptionForNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                     () -> regularCalculator.calculate(-10.0),
                     "Debe lanzar IllegalArgumentException para precios negativos");
    }

    @Test
    @DisplayName("Se lanza excepción cuando el precio es negativo en cliente VIP")
    void testVipCustomerThrowsExceptionForNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                     () -> vipCalculator.calculate(-10.0),
                     "Debe lanzar IllegalArgumentException para precios negativos");
    }

    @Test
    @DisplayName("La clase es cerrada para modificación: se puede extender sin cambiar código existente")
    void testExtensibilityWithNewStrategy() {
        // Crear una nueva estrategia sin modificar el código existente
        DiscountStrategy studentDiscount = new DiscountStrategy() {
            @Override
            public double calculateDiscount(double price) {
                validatePrice(price);
                return price * 0.15; // 15% descuento para estudiantes
            }
        };
        
        DiscountCalculator studentCalculator = new DiscountCalculator(studentDiscount);
        double price = 100.0;
        
        assertEquals(15.0, studentCalculator.calculate(price), 0.01,
                    "La nueva estrategia debe funcionarinicio sin modificar el código existente");
        assertEquals(85.0, studentCalculator.totalAfterDiscount(price), 0.01);
    }

    @Test
    @DisplayName("Verificar que diferentes estrategias den diferentes resultados")
    void testDifferentStrategiesProduceDifferentResults() {
        double price = 100.0;
        
        double regularDiscount = regularCalculator.calculate(price);
        double vipDiscount = vipCalculator.calculate(price);
        
        assertTrue(vipDiscount > regularDiscount,
                  "El descuento VIP debe ser mayor que el descuento regular");
        assertEquals(vipDiscount, regularDiscount * 2, 0.01,
                    "El descuento VIP debe ser el doble que el descuento regular");
    }

    @Test
    @DisplayName("RegularCustomerDiscount con class anónima funciona correctamente")
    void testAnonymousDiscountStrategy() {
        DiscountStrategy goldDiscount = new DiscountStrategy() {
            @Override
            public double calculateDiscount(double price) {
                validatePrice(price);
                return price * 0.25; // 25% descuento
            }
        };
        
        DiscountCalculator goldCalculator = new DiscountCalculator(goldDiscount);
        assertEquals(25.0, goldCalculator.calculate(100.0), 0.01);
    }

    @Test
    @DisplayName("Lambda expression como estrategia de descuento funciona")
    void testLambdaDiscountStrategy() {
        DiscountStrategy bronzeDiscount = (price) -> {
            if (price < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo");
            }
            return price * 0.05; // 5% descuento
        };
        
        DiscountCalculator bronzeCalculator = new DiscountCalculator(bronzeDiscount);
        assertEquals(5.0, bronzeCalculator.calculate(100.0), 0.01);
    }

    @Test
    @DisplayName("DiscountCalculator funciona con cualquier estrategia")
    void testDiscountCalculatorWithMultipleStrategies() {
        DiscountStrategy[] strategies = {
            new RegularCustomerDiscount(),
            new VipCustomerDiscount(),
            (price) -> {
                if (price < 0) throw new IllegalArgumentException("Precio negativo");
                return price * 0.15;
            }
        };
        
        double price = 100.0;
        double[] expectedDiscounts = {10.0, 20.0, 15.0};
        
        for (int i = 0; i < strategies.length; i++) {
            DiscountCalculator calculator = new DiscountCalculator(strategies[i]);
            assertEquals(expectedDiscounts[i], calculator.calculate(price), 0.01);
        }
    }

    @Test
    @DisplayName("Principio Open/Closed: abierto para extensión, cerrado para modificación")
    void testOpenClosedPrinciple() {
        // El sistema está cerrado para modificación: no necesitamos cambiar DiscountCalculator
        // El sistema está abierto para extensión: podemos agregar nuevas estrategias
        
        class SeniorDiscountStrategy implements DiscountStrategy {
            @Override
            public double calculateDiscount(double price) {
                validatePrice(price);
                return price * 0.30; // 30% descuento
            }
        }
        
        DiscountCalculator seniorCalculator = new DiscountCalculator(new SeniorDiscountStrategy());
        double price = 100.0;
        
        // Se puede usar sin modificar DiscountCalculator
        assertDoesNotThrow(() -> seniorCalculator.calculate(price));
        assertEquals(30.0, seniorCalculator.calculate(price), 0.01);
    }
}
