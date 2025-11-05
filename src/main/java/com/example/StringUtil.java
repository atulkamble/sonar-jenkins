package com.example;

/**
 * Utility class for string operations
 */
public class StringUtil {
    
    /**
     * Check if a string is null or empty
     * @param str string to check
     * @return true if string is null or empty, false otherwise
     */
    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Reverse a string
     * @param str string to reverse
     * @return reversed string
     */
    public String reverse(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
     * Check if a string is palindrome
     * @param str string to check
     * @return true if palindrome, false otherwise
     */
    public boolean isPalindrome(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        String cleaned = str.replaceAll("\\s+", "").toLowerCase();
        return cleaned.equals(reverse(cleaned));
    }
    
    /**
     * Count vowels in a string
     * @param str string to count vowels in
     * @return number of vowels
     */
    public int countVowels(String str) {
        if (isNullOrEmpty(str)) {
            return 0;
        }
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : str.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Capitalize first letter of each word
     * @param str string to capitalize
     * @return capitalized string
     */
    public String capitalizeWords(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
}
