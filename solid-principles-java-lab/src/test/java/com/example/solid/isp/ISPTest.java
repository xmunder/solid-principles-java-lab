package com.example.solid.isp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para el principio ISP (Interface Segregation Principle)")
class ISPTest {

    private Bot bot;
    private Developer developer;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        bot = new Bot();
        developer = new Developer();
        
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Bot implementa Workable")
    void testBotImplementsWorkable() {
        assertTrue(bot instanceof Workable, "Bot debe implementar Workable");
    }

    @Test
    @DisplayName("Bot puede trabajar")
    void testBotCanWork() {
        bot.work();
        String output = outputStream.toString();
        assertTrue(output.contains("Trabajando"), "Bot debe poder trabajar");
    }

    @Test
    @DisplayName("Bot NO implementa Eatable (segregación de interfaces)")
    void testBotDoesNotImplementEatable() {
        assertFalse(bot instanceof Eatable, "Bot NO debe implementar Eatable");
    }

    @Test
    @DisplayName("Developer implementa Workable")
    void testDeveloperImplementsWorkable() {
        assertTrue(developer instanceof Workable, "Developer debe implementar Workable");
    }

    @Test
    @DisplayName("Developer implementa Eatable")
    void testDeveloperImplementsEatable() {
        assertTrue(developer instanceof Eatable, "Developer debe implementar Eatable");
    }

    @Test
    @DisplayName("Developer puede trabajar")
    void testDeveloperCanWork() {
        developer.work();
        String output = outputStream.toString();
        assertTrue(output.contains("código"), "Developer debe escribir código");
    }

    @Test
    @DisplayName("Developer puede comer")
    void testDeveloperCanEat() {
        outputStream.reset();
        developer.eat();
        String output = outputStream.toString();
        assertTrue(output.contains("Comiendo"), "Developer debe poder comer");
    }

    @Test
    @DisplayName("Las interfaces son segregadas: Bot solo usa Workable")
    void testInterfaceSegregation() {
        // ISP: Bot no está obligado a implementar Eatable
        // Cada clase implementa solo lo que necesita
        assertTrue(bot instanceof Workable && !(bot instanceof Eatable));
        assertTrue(developer instanceof Workable && developer instanceof Eatable);
    }
}
