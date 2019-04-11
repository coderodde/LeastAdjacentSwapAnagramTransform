package net.coderodde.fun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This interface defines the API and basic infrastructure for algorithms 
 * returning a shortest list of adjacent swaps required to transform one anagram 
 * into another.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public interface LeastAdjacentSwapAnagramTransformAlgorithm {

    /**
     * Finds a shortest sequence of adjacent swaps, that transforms 
     * {@code string1} into {@code string2}.
     * 
     * @param string1 the source string.
     * @param string2 the target string.
     * @return the list of adjacent swaps transforming the source array into the 
     *         target array.
     */
    public List<AdjacentSwapDescriptor> compute(String string1, String string2);

    /**
     * Runs preliminary checks on the two input strings.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     */
    static void checkInputStrings(String string1, String string2) {
        Objects.requireNonNull(string1, "The first input string is null.");
        Objects.requireNonNull(string2, "The second input string is null.");
        checkStringsHaveSameLength(string1, string2);
        checkStringsAreAnagrams(string1, string2);
    }

    /**
     * Checks that the two input strings are of the same length.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     */
    static void checkStringsHaveSameLength(String string1, String string2) {
        if (string1.length() != string2.length()) {
            throw new IllegalArgumentException(
                    "The two input streams have different lengths: " + 
                    string1.length() + " vs. " + string2.length());
        }
    }
    
    /**
     * Checks that the two input strings are of anagrams. In other worlds, 
     * checks that one string can be transformed into the second string only by
     * rearranging the letters in one of them.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     */
    static void checkStringsAreAnagrams(String string1, String string2) {
        if (!areAnagrams(string1, string2)) {
            throw new IllegalArgumentException(
                    "The two input strings are not anagrams.");
        }
    }
    
    /**
     * Checks that the two input strings are anagrams.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     * @return {@code true} if the two input strings are anagrams. {@code false}
     *         otherwise.
     */
    static boolean areAnagrams(String string1, String string2) {
        Map<Character, Integer> characterCountMap1 = new HashMap<>();
        Map<Character, Integer> characterCountMap2 = new HashMap<>();

        for (char c : string1.toCharArray()) {
            characterCountMap1.
                    put(c, characterCountMap1.getOrDefault(c, 0) + 1);
        }

        for (char c : string2.toCharArray()) {
            characterCountMap2.
                    put(c, characterCountMap2.getOrDefault(c, 0) + 1);
        }

        return characterCountMap1.equals(characterCountMap2);
    }
    
    static void swap(char[] characterArray,
                     int index1,
                     int index2) {
        char tmp = characterArray[index1];
        characterArray[index1] = characterArray[index2];
        characterArray[index2] = tmp;
    }
    
    static void swap(char[] characterArray, 
                     AdjacentSwapDescriptor adjacencyAdjacentSwapDescriptor) {
        int index1 = adjacencyAdjacentSwapDescriptor.startingIndex;
        int index2 = index1 + 1;
        char tmp = characterArray[index1];
        characterArray[index1] = characterArray[index2];
        characterArray[index2] = tmp;
    }
}
