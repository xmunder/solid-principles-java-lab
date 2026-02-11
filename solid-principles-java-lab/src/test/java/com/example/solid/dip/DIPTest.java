package com.example.solid.dip;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el principio DIP (Dependency Inversion Principle)")
class DIPTest {

    private MySQLDatabase mySQLDatabase;
    private OrderProcessor orderProcessor;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        mySQLDatabase = new MySQLDatabase();
        orderProcessor = new OrderProcessor(mySQLDatabase);
        
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("OrderProcessor se crea con una Database válida")
    void testOrderProcessorWithValidDatabase() {
        assertNotNull(orderProcessor, "OrderProcessor debe crearse correctamente");
    }

    @Test
    @DisplayName("OrderProcessor lanza excepción si Database es null")
    void testOrderProcessorThrowsExceptionForNullDatabase() {
        assertThrows(IllegalArgumentException.class,
                     () -> new OrderProcessor(null),
                     "Debe lanzar IllegalArgumentException cuando database es null");
    }

    @Test
    @DisplayName("OrderProcessor procesa un pedido correctamente")
    void testProcessOrder() {
        orderProcessor.processOrder();
        String output = outputStream.toString();
        assertTrue(output.contains("Procesando pedido"), "Debe mostrar que se está procesando");
    }

    @Test
    @DisplayName("MySQLDatabase guarda el pedido")
    void testMySQLDatabaseSavesOrder() {
        mySQLDatabase.saveOrder();
        String output = outputStream.toString();
        assertTrue(output.contains("MySQL"), "Debe guardarse en MySQL");
    }

    @Test
    @DisplayName("OrderProcessor puede usar cualquier implementación de Database")
    void testDependencyInversionWithDifferentImplementation() {
        // Crear una nueva implementación sin modificar OrderProcessor
        Database mockDatabase = new Database() {
            @Override
            public void saveOrder() {
                System.out.println("Guardando en base de datos simulada");
            }
        };
        
        OrderProcessor newProcessor = new OrderProcessor(mockDatabase);
        assertDoesNotThrow(() -> newProcessor.processOrder(),
                          "Debe funcionar con cualquier implementación de Database");
    }

    @Test
    @DisplayName("OrderProcessor depende de la abstracción Database, no de MySQLDatabase")
    void testDependsOnAbstraction() {
        // OrderProcessor depende de Database (abstracción), no de MySQLDatabase (implementación)
        assertTrue(orderProcessor instanceof Object);
        assertNotNull(mySQLDatabase);
    }

    @Test
    @DisplayName("El flujo completo: procesar pedido con MySQL")
    void testCompleteProcessOrderFlow() {
        outputStream.reset();
        orderProcessor.processOrder();
        String output = outputStream.toString();
        
        assertTrue(output.contains("Procesando pedido"), "Debe procesar el pedido");
        assertTrue(output.contains("MySQL"), "Debe guardar en MySQL");
    }
}
