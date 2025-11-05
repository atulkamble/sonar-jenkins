package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for StringUtil
 */
public class StringUtilTest {
    
    private StringUtil stringUtil;
    
    @BeforeEach
    public void setUp() {
        stringUtil = new StringUtil();
    }
    
    @Test
    @DisplayName("Test isNullOrEmpty with null string")
    public void testIsNullOrEmptyWithNull() {
        assertTrue(stringUtil.isNullOrEmpty(null));
    }
    
    @Test
    @DisplayName("Test isNullOrEmpty with empty string")
    public void testIsNullOrEmptyWithEmpty() {
        assertTrue(stringUtil.isNullOrEmpty(""));
    }
    
    @Test
    @DisplayName("Test isNullOrEmpty with non-empty string")
    public void testIsNullOrEmptyWithNonEmpty() {
        assertFalse(stringUtil.isNullOrEmpty("Hello"));
    }
    
    @Test
    @DisplayName("Test reverse string")
    public void testReverse() {
        assertEquals("olleH", stringUtil.reverse("Hello"));
        assertEquals("12345", stringUtil.reverse("54321"));
    }
    
    @Test
    @DisplayName("Test reverse with null/empty")
    public void testReverseNullOrEmpty() {
        assertNull(stringUtil.reverse(null));
        assertEquals("", stringUtil.reverse(""));
    }
    
    @Test
    @DisplayName("Test isPalindrome with valid palindromes")
    public void testIsPalindrome() {
        assertTrue(stringUtil.isPalindrome("radar"));
        assertTrue(stringUtil.isPalindrome("A man a plan a canal Panama"));
        assertTrue(stringUtil.isPalindrome("racecar"));
    }
    
    @Test
    @DisplayName("Test isPalindrome with non-palindromes")
    public void testIsNotPalindrome() {
        assertFalse(stringUtil.isPalindrome("hello"));
        assertFalse(stringUtil.isPalindrome("world"));
    }
    
    @Test
    @DisplayName("Test isPalindrome with null/empty")
    public void testIsPalindromeNullOrEmpty() {
        assertFalse(stringUtil.isPalindrome(null));
        assertFalse(stringUtil.isPalindrome(""));
    }
    
    @Test
    @DisplayName("Test count vowels")
    public void testCountVowels() {
        assertEquals(2, stringUtil.countVowels("Hello"));
        assertEquals(3, stringUtil.countVowels("beautiful"));
        assertEquals(0, stringUtil.countVowels("xyz"));
    }
    
    @Test
    @DisplayName("Test count vowels with null/empty")
    public void testCountVowelsNullOrEmpty() {
        assertEquals(0, stringUtil.countVowels(null));
        assertEquals(0, stringUtil.countVowels(""));
    }
    
    @Test
    @DisplayName("Test capitalize words")
    public void testCapitalizeWords() {
        assertEquals("Hello World", stringUtil.capitalizeWords("hello world"));
        assertEquals("Java Programming", stringUtil.capitalizeWords("JAVA PROGRAMMING"));
        assertEquals("Test Case", stringUtil.capitalizeWords("test case"));
    }
    
    @Test
    @DisplayName("Test capitalize words with null/empty")
    public void testCapitalizeWordsNullOrEmpty() {
        assertNull(stringUtil.capitalizeWords(null));
        assertEquals("", stringUtil.capitalizeWords(""));
    }
}
