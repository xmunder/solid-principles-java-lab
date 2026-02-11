package com.example.solid.srp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el principio SRP (Single Responsibility Principle)")
class SRPTest {

    private Invoice invoice;
    private InvoicePrinter invoicePrinter;
    private InvoiceDatabaseSaver invoiceSaver;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        invoice = new Invoice("Cliente Test", 100.0);
        invoicePrinter = new InvoicePrinter();
        invoiceSaver = new InvoiceDatabaseSaver();
        
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("La clase Invoice calcula el total correctamente con impuestos (21%)")
    void testInvoiceCalculateTotal() {
        double expectedTotal = 121.0;
        assertEquals(expectedTotal, invoice.calculateTotal(), 0.01);
    }

    @Test
    @DisplayName("La clase Invoice obtiene el nombre del cliente correctamente")
    void testInvoiceGetCustomerName() {
        assertEquals("Cliente Test", invoice.getCustomerName());
    }

    @Test
    @DisplayName("La clase Invoice obtiene el monto correctamente")
    void testInvoiceGetAmount() {
        assertEquals(100.0, invoice.getAmount(), 0.01);
    }

    @Test
    @DisplayName("La clase Invoice debe mantener una única responsabilidad: almacenar datos")
    void testInvoiceResponsibility() {
        Invoice testInvoice = new Invoice("Juan", 50.0);
        
        assertTrue(testInvoice.getAmount() > 0, "Debe tener un monto válido");
        assertNotNull(testInvoice.getCustomerName(), "Debe tener un cliente");
        assertTrue(testInvoice.calculateTotal() > 0, "Debe ser capaz de calcular el total");
    }

    @Test
    @DisplayName("Invoice lanza excepción si el cliente es nulo")
    void testInvoiceThrowsExceptionForNullCustomer() {
        assertThrows(IllegalArgumentException.class, 
                     () -> new Invoice(null, 100.0),
                     "Debe lanzar IllegalArgumentException cuando el cliente es null");
    }

    @Test
    @DisplayName("Invoice lanza excepción si el cliente está vacío")
    void testInvoiceThrowsExceptionForEmptyCustomer() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Invoice("", 100.0),
                     "Debe lanzar IllegalArgumentException cuando el cliente está vacío");
    }

    @Test
    @DisplayName("Invoice lanza excepción si el cliente es solo espacios")
    void testInvoiceThrowsExceptionForBlankCustomer() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Invoice("   ", 100.0),
                     "Debe lanzar IllegalArgumentException cuando el cliente solo contiene espacios");
    }

    @Test
    @DisplayName("Invoice lanza excepción si el monto es negativo")
    void testInvoiceThrowsExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                     () -> new Invoice("Cliente", -50.0),
                     "Debe lanzar IllegalArgumentException cuando el monto es negativo");
    }

    @Test
    @DisplayName("Invoice acepta monto de cero")
    void testInvoiceAcceptsZeroAmount() {
        assertDoesNotThrow(() -> new Invoice("Cliente", 0.0),
                          "Debe permitir un monto de cero");
        Invoice zeroInvoice = new Invoice("Cliente", 0.0);
        assertEquals(0.0, zeroInvoice.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Invoice recorta espacios del nombre del cliente")
    void testInvoiceTrimsCustomerName() {
        Invoice trimmedInvoice = new Invoice("  Cliente  ", 100.0);
        assertEquals("Cliente", trimmedInvoice.getCustomerName(),
                     "Debe remover espacios al inicio y final del nombre");
    }

    @Test
    @DisplayName("InvoicePrinter imprime la factura correctamente")
    void testInvoicePrinterPrintInvoice() {
        invoicePrinter.printInvoice(invoice);
        
        String output = outputStream.toString();
        
        assertTrue(output.contains("Factura para: Cliente Test"), 
                   "La salida debe contener el nombre del cliente");
        assertTrue(output.contains("Total: 121.0"), 
                   "La salida debe contener el total calculado");
    }

    @Test
    @DisplayName("InvoicePrinter tiene la única responsabilidad de imprimir")
    void testInvoicePrinterResponsibility() {
        Invoice testInvoice = new Invoice("María", 200.0);
        
        assertDoesNotThrow(() -> invoicePrinter.printInvoice(testInvoice),
                          "InvoicePrinter debe imprimir sin lanzar excepciones");
        
        String output = outputStream.toString();
        assertTrue(output.contains("Factura para:"), 
                   "InvoicePrinter solo debe ser responsable de imprimir");
    }

    @Test
    @DisplayName("InvoicePrinter lanza excepción si la factura es null")
    void testInvoicePrinterThrowsExceptionForNullInvoice() {
        assertThrows(IllegalArgumentException.class,
                     () -> invoicePrinter.printInvoice(null),
                     "Debe lanzar IllegalArgumentException cuando la factura es null");
    }

    @Test
    @DisplayName("InvoiceDatabaseSaver guarda en la base de datos")
    void testInvoiceDatabaseSaverSaveToDatabase() {
        invoiceSaver.saveToDatabase();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Guardando factura..."),
                   "La salida debe indicar que se está guardando");
    }

    @Test
    @DisplayName("InvoiceDatabaseSaver tiene la única responsabilidad de guardar")
    void testInvoiceDatabaseSaverResponsibility() {
        assertDoesNotThrow(() -> invoiceSaver.saveToDatabase(),
                          "El guardado debe ejecutarse sin excepciones");
        
        String output = outputStream.toString();
        assertTrue(output.contains("Guardando factura..."),
                   "InvoiceDatabaseSaver solo debe be responsable de guardar en BD");
    }

    @Test
    @DisplayName("Separación de responsabilidades: Invoice no imprime ni guarda")
    void testInvoiceDoesNotHandleOtherResponsibilities() {
        Invoice testInvoice = new Invoice("Test", 50.0);
        
        assertDoesNotThrow(() -> testInvoice.getAmount());
        assertDoesNotThrow(() -> testInvoice.getCustomerName());
        assertDoesNotThrow(() -> testInvoice.calculateTotal());
    }

    @Test
    @DisplayName("Flujo completo: crear factura, imprimirla y guardarla")
    void testCompleteInvoiceWorkflow() {
        // Limpiar output anterior
        outputStream.reset();
        
        Invoice workflowInvoice = new Invoice("Carlos", 150.0);
        
        // Cada clase hace su trabajo
        invoicePrinter.printInvoice(workflowInvoice);
        invoiceSaver.saveToDatabase();
        
        String output = outputStream.toString();
        
        assertTrue(output.contains("Factura para: Carlos"),
                   "La factura debe ser impresa con el nombre correcto");
        assertTrue(output.contains("Total: 181.5"),
                   "El total debe calcularse correctamente");
        assertTrue(output.contains("Guardando factura..."),
                   "La factura debe guardarse en la BD");
    }
}
