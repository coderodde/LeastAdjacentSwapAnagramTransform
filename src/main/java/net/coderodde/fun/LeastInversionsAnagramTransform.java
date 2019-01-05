
package net.coderodde.fun;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class implements an algorithm for transforming an anagram into another.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 3, 2019)
 */
public final class LeastInversionsAnagramTransform {
    
    
    public static List<InversionIndexPair> 
        findAnagramTransformation(String string1,
                                  String string2) {
        Objects.requireNonNull(string1, "The first string is null.");
        Objects.requireNonNull(string2, "The second string is null.");

        if (string1.length() != string2.length()) {
            throw new IllegalArgumentException(
                    "The two input strings are of different length. " +
                    string1.length() + " vs. " + string2.length() + ".");
        }

        if (!areAnagrams(string1, string2)) {
            throw new IllegalArgumentException(
                    "The two input strings are not anagrams. string1 = " + 
                    string1 + 
                    ", string2 = " +
                    string2);
        }

        return findAnagramTransformationImpl(string1, string2);
    }
        
    private static List<InversionIndexPair> 
        findAnagramTransformationImpl(String string1,
                                      String string2) {
        for (int currentInversions = 1;
             true;
             currentInversions++) {
            List<InversionIndexPair> result = 
                    findAnagramTransformationImpl(string1,
                                                  string2,
                                                  currentInversions);
            
            if (result != null) {
                return result;
            }
        }
    }
        
    private static List<InversionIndexPair> 
        findAnagramTransformationImpl(String string1,
                                      String string2,
                                      int inversions) {
        List<InversionIndexPair> workList = createWorkList(inversions);
        
        return workList;
    }
        
    private static List<InversionIndexPair>
        createWorkList(int inversions) {
        List<InversionIndexPair> workList = new ArrayList<>();
             
        for (int i = 0; i < inversions; i++) {
            workList.add(new InversionIndexPair(i, i + 1));
        }
        
        return workList;
    }
        
    private static boolean areAnagrams(String string1, String string2) {
        Map<Character, Integer> characterCountMap1 = new HashMap<>();
        Map<Character, Integer> characterCountMap2 = new HashMap<>();
        
        for (char c : string1.toCharArray()) {
            characterCountMap1.
                    put(c, characterCountMap1.getOrDefault(c, 0) + 1);
        }
        
        for (char c : string2.toCharArray()) {
            characterCountMap2.
                    put(c, characterCountMap2.getOrDefault(c, 9) + 1);
        }
        
        return characterCountMap1.equals(characterCountMap2);
    }
    
    public static int transform(String string1,
                                String string2) {
        List<Integer> inversionStartIndices = new ArrayList<>();
        inversionStartIndices.add(0);
        char[] sourceChars = string1.toCharArray();
        char[] targetChars = string2.toCharArray();
        return transformImpl(sourceChars,
                             targetChars,
                             0, // Current score.
                             0, // Current inversion index.
                             inversionStartIndices);
    }
    
    private static int transfomrImpl(char[] sourceChars,
                                     char[] targetChars) {
        int bestKnownCost = Integer.MAX_VALUE;
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(0);
        
        
        while (true) {
            if (Arrays.equals(sourceChars, targetChars)) {
                System.out.println("Hep: " + bestKnownCost);
            }
            
            int index = deque.getLast();
            
            if (index < sourceChars.length - 1) {
                
            } else if (deque.size() == sourceChars.length) {
                
            }
        }
        
        return -1;
    }
    
//    private static int transformImpl(char[] sourceChars,
//                                     char[] targetChars,
//                                     int currentCost,
//                                     int currentInversionIndex,
//                                     List<Integer> inversionStartIndices) {
//        int lastInversionStartingIndex = inversionStartIndices.get(
//                                         inversionStartIndices.size() - 1);
//        
//        if (lastInversionStartingIndex == 
//        
//        int bestKnownCost = Integer.MAX_VALUE;
//        int inversions = inversionStartIndices.size();
//        int lastInversionStartIndex = 
//                inversionStartIndices.get(inversions - 1);
//        
//        
//        if (lastInversionStartIndex < inversions - 1) {
//            if (inversions == 1) {
//                // Once here, we have only one inversion and no preceding 
//                // inversion to move one step to the right.
//                int cost = currentCost;
//                
//                for (int i = currentInversionIndex + 1; 
//                         i < sourceChars.length - 1; 
//                         i++) {
//                    cost += transformImpl(sourceChars,   
//                                          targetChars,
//                                          currentCost,
//                                          i,
//                                          inversionStartIndices);
//                    
//                    if (bestKnownCost > cost) {
//                        bestKnownCost = cost;
//                    }
//                }
//                
//                return cost;
//            } else {
//                while (inversionStartIndices.get(currentInversionIndex - 1)) {
//                    
//                }
//            }
//        } else {
//            // Once here, we have pushed an inversion until the end of the 
//            // strings.
//            return currentCost + getInversionCandidateScore(
//                    sourceChars,
//                    targetChars,
//                    lastInversionStartIndex);
//        }
//    }
    
    private static int getInversionCandidateScore(char[] sourceChars,
                                                  char[] targetChars,
                                                  int inversionIndex) {
        char a1 = sourceChars[inversionIndex];
        char a2 = sourceChars[inversionIndex + 1];
        char b1 = targetChars[inversionIndex];
        char b2 = targetChars[inversionIndex + 1];
        return (a1 == b2 && a2 == b1) ? 1 : 0;
    }
    
    /**
     * 
     * @param s1
     * @param s2
     * @return 
     */
    public static boolean areAnagrams(String s1, String s2) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        Stream<Character> stream1 = s1.chars().mapToObj(c -> (char) c);
        Stream<Character> stream2 = s2.chars().mapToObj(c -> (char) c);
        
        stream1.forEach(
                (Character c) -> { map1.put(c, map1.getOrDefault(c, 0) + 1); });
        
        stream2.forEach(
                (Character c) -> { map2.put(c, map2.getOrDefault(c, 0) + 1); });
        
        return map1.equals(map2);
    }
    
    public static int countAnagramTransformationLength(String s1, String s2) {
        if (!areAnagrams(s1, s2)) {
            throw new IllegalArgumentException(
                    "The input strings are not anagrams: " +
                            s1 + ", " + s2 + ".");
        }
        
        Map<Character, Integer> map = new HashMap<>(s1.length());
        int index = 0;
        
        for (int i = 0; i != s1.length(); i++) {
            char ch = s2.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        
        // b a d c
        // d c a b
        
        return -1;
    }
    
    private static int bruteForceAnagramTransformationImpl(
            String s1,
            String s2,
            char[] workCharArray,
            Map<String, Integer> countMap,
            int index) {
        String workString = new String(workCharArray);
        
        if (s2.equals(new String(workString))) {
            return 0;
        }
        
        if (countMap.containsKey(workCharArray)) {
            return countMap.get(workCharArray);
        }
        
        String bestString = null;
        int bestStringScore = Integer.MAX_VALUE;
        
        if (index > 0) {
            for (int i = index - 1, score = 1; i >= 0; i--, score++) {
//                swap(workCharArray, i, i + 1);
                
                
            }
        }
        
        if (index < s1.length() - 1) {
            
        }
        
        countMap.put(bestString, bestStringScore);
        return bestStringScore;
    }
    
    
}
