package com.example.solid.lsp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el principio LSP (Liskov Substitution Principle)")
class LSPTest {

    private Car car;
    private ElectricCar electricCar;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        car = new Car();
        electricCar = new ElectricCar();
        
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Car implementa Refuelable correctamente")
    void testCarCanRefuel() {
        car.refuel();
        String output = outputStream.toString();
        assertTrue(output.contains("Tanqueando"), "Car debe poder reabastecerse");
    }

    @Test
    @DisplayName("Car implementa Drivable correctamente")
    void testCarCanDrive() {
        outputStream.reset();
        car.drive();
        String output = outputStream.toString();
        assertTrue(output.contains("Conduciendo"), "Car debe poder conducirse");
    }

    @Test
    @DisplayName("ElectricCar implementa Drivable correctamente")
    void testElectricCarCanDrive() {
        electricCar.drive();
        String output = outputStream.toString();
        assertTrue(output.contains("eléctrico"), "ElectricCar debe especificar que es eléctrico");
    }

    @Test
    @DisplayName("ElectricCar es un Drivable válido (sustitución)")
    void testElectricCarIsValidDrivable() {
        // LSP: ElectricCar puede usarse donde se espera Drivable
        Drivable vehicle = electricCar;
        assertDoesNotThrow(() -> vehicle.drive(), "ElectricCar debe funcionar como Drivable");
    }

    @Test
    @DisplayName("Car es un Drivable válido (sustitución)")
    void testCarIsValidDrivable() {
        Drivable vehicle = car;
        assertDoesNotThrow(() -> vehicle.drive(), "Car debe funcionar como Drivable");
    }

    @Test
    @DisplayName("ElectricCar NO implementa Refuelable (respeta LSP)")
    void testElectricCarDoesNotImplementRefuelable() {
        assertFalse(electricCar instanceof Refuelable, 
                   "ElectricCar no debe implementar Refuelable");
    }

    @Test
    @DisplayName("Car implementa Refuelable (contrato cumplido)")
    void testCarImplementsRefuelable() {
        assertTrue(car instanceof Refuelable, 
                  "Car debe implementar Refuelable");
    }

    @Test
    @DisplayName("Los subtipos no violan el contrato de Drivable")
    void testSubstitutabilityOfDrivable() {
        Drivable[] vehicles = {car, electricCar};
        
        for (Drivable vehicle : vehicles) {
            assertDoesNotThrow(() -> vehicle.drive(), 
                             "Todos los Drivable deben poder conducirse");
        }
    }
}
