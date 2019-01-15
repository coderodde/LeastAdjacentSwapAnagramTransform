package net.coderodde.fun.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.coderodde.fun.LeastInversionsAnagramTransformAlgorithm;
import static net.coderodde.fun.
       LeastInversionsAnagramTransformAlgorithm.areAnagrams;
import net.coderodde.util.ListTupleIndexIterator;
import net.coderodde.util.PermutationIterable;

/**
 * This class implements a brute-force algorithm for computing shortest 
 * inversions list transforming one input string into another.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class BruteForceLeastInversionAnagramTransformAlgorithm 
implements LeastInversionsAnagramTransformAlgorithm {

    /**
     * Computes and returns a shortest sequence of inversion required to 
     * transform one string into another.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     * @return a sequence (list) of inversions.
     */
    @Override
    public List<InversionDescriptor> compute(String string1, String string2) {
        checkInputStrings(string1, string2);
        
        if (string1.equals(string2)) {
            return new ArrayList<>();
        }
        
        int[] rawIndices = computeImpl(string1,
                                       string2);
        
        List<InversionDescriptor> result = 
                toInversionDescriptorList(rawIndices);
        
        Collections.reverse(result);
        return result;
    }
    
    /**
     * Converts an internal representation of inversion indices to a list of
     * inversion descriptors.
     * 
     * @param inversionStartingIndices
     * @return 
     */
    private static List<InversionDescriptor>
         toInversionDescriptorList(int[] inversionStartingIndices) {
         List<InversionDescriptor> result = 
                 new ArrayList<>(inversionStartingIndices.length);
         
         for (int inversionStartingIndex : inversionStartingIndices) {
             result.add(new InversionDescriptor(inversionStartingIndex));
         }
         
         return result;
    }
    
    /**
     * Runs preliminary checks on the two input strings.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     */
    private static void checkInputStrings(String string1, String string2) {
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
    private static void checkStringsHaveSameLength(String string1,
                                                   String string2) {
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
    private static void checkStringsAreAnagrams(String string1,
                                                String string2) {
        if (!areAnagrams(string1, string2)) {
            throw new IllegalArgumentException(
                    "The two input strings are not anagrams.");
        }
    }
    
    /**
     * Runs the topmost search of the algorithm.
     * 
     * @param string1 the source string.
     * @param string2 the target string.
     * @return the smallest list of inversion descriptors required to transform
     *         one string into another.
     */
    private static int[] computeImpl(String string1, 
                                     String string2) {
        char[] sourceStringChars = string1.toCharArray();
        char[] targetStringChars = string2.toCharArray();
        char[] bufferStringChars = new char[sourceStringChars.length];
        
        for (int inversions = 1; true; inversions++) {
            int[] solution = computeImpl(sourceStringChars, 
                                         targetStringChars,
                                         bufferStringChars,
                                         inversions);
            if (solution != null) {
                return solution;
            }
        }
    }
    
    /**
     * Attempts to find an inversion descriptor list of length 
     * {@code inversions}. If no such exist, returns {@code null}.
     * 
     * @param sourceStringChars    the source string.
     * @param targetStringChars    the target string.
     * @param inversions the requested number of inversions in the result.
     * @return if not such list exist.
     */
    private static int[] computeImpl(char[] sourceStringChars, 
                                     char[] targetStringChars,
                                     char[] bufferStringChars,
                                     int inversions) {
        // string1.length() - 1, i.e., there are at most n - 1 distinct 
        // inversion pairs.
        ListTupleIndexIterator iterator = 
                new ListTupleIndexIterator(inversions, 
                                           sourceStringChars.length - 2);
                
        int[] indices = iterator.getIndexArray();
        
        do {
            int[] resultIndices = applyIndicesToWorkArray(sourceStringChars,
                                                          targetStringChars,
                                                          bufferStringChars,
                                                          indices);
            if (resultIndices != null) {
                return resultIndices;
            }
            
            iterator.generateNextTupleIndices();
        } while (iterator.hasNext());
        
        return null;
    }
    
    private static int[] applyIndicesToWorkArray(char[] sourceCharArray,
                                                 char[] targetCharArray,
                                                 char[] bufferCharArray,
                                                 int[] indices) {
        copySourceArrayToWorkArray(sourceCharArray,
                                   bufferCharArray);
        
        PermutationIterable permutationIterable = 
                new PermutationIterable(indices.length);
        
        int[] permutationIndices = permutationIterable.getIndexArray();
        
        while (permutationIterable.hasNext()) {
            System.arraycopy(targetCharArray,
                             0, 
                             bufferCharArray, 
                             0, 
                             targetCharArray.length);
            
            // For each inversion pair permutation, apply it and see whether we
            // got to the source character array:
            for (int i : permutationIndices) {
                int inversionIndex = indices[i];
                swap(bufferCharArray,
                     inversionIndex,
                     inversionIndex + 1);
            }
            
            if (Arrays.equals(bufferCharArray, sourceCharArray)) {
                return permutationIndices;
            }
            
            permutationIterable.generateNextPermutation();
        }
        
        return null;
    }
    
    private static void swap(char[] array,
                             int index1,
                             int index2) {
        char tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
    
    private static void copySourceArrayToWorkArray(char[] sourceArray,
                                                   char[] workArray) {
            System.arraycopy(sourceArray,
                             0,
                             workArray, 
                             0, 
                             workArray.length);
    }
}
