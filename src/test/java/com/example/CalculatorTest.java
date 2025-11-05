package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Calculator
 */
public class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Test addition of two positive numbers")
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(10, calculator.add(7, 3));
    }
    
    @Test
    @DisplayName("Test addition with negative numbers")
    public void testAddWithNegative() {
        assertEquals(-1, calculator.add(-3, 2));
        assertEquals(-5, calculator.add(-2, -3));
    }
    
    @Test
    @DisplayName("Test subtraction")
    public void testSubtract() {
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-1, calculator.subtract(2, 3));
    }
    
    @Test
    @DisplayName("Test multiplication")
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(-6, calculator.multiply(-2, 3));
        assertEquals(0, calculator.multiply(0, 5));
    }
    
    @Test
    @DisplayName("Test division")
    public void testDivide() {
        assertEquals(2.0, calculator.divide(6, 3), 0.001);
        assertEquals(2.5, calculator.divide(5, 2), 0.001);
    }
    
    @Test
    @DisplayName("Test division by zero throws exception")
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(5, 0);
        });
    }
    
    @Test
    @DisplayName("Test power calculation")
    public void testPower() {
        assertEquals(8.0, calculator.power(2, 3), 0.001);
        assertEquals(1.0, calculator.power(5, 0), 0.001);
        assertEquals(0.25, calculator.power(2, -2), 0.001);
    }
    
    @Test
    @DisplayName("Test square root")
    public void testSqrt() {
        assertEquals(4.0, calculator.sqrt(16), 0.001);
        assertEquals(5.0, calculator.sqrt(25), 0.001);
        assertEquals(0.0, calculator.sqrt(0), 0.001);
    }
    
    @Test
    @DisplayName("Test square root of negative number throws exception")
    public void testSqrtNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.sqrt(-1);
        });
    }
}
