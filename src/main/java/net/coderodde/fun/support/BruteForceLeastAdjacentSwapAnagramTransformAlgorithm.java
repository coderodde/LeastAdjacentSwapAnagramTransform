package net.coderodde.fun.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm.areAnagrams;
import net.coderodde.util.ListTupleIndexIterator;
import net.coderodde.util.PermutationIterable;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm;

/**
 * This class implements a brute-force algorithm for computing shortest 
 * inversions list transforming one input string into another.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class BruteForceLeastAdjacentSwapAnagramTransformAlgorithm 
implements LeastAdjacentSwapAnagramTransformAlgorithm {

    /**
     * Computes and returns a shortest sequence of inversion required to 
     * transform one string into another.
     * 
     * @param string1 the first string.
     * @param string2 the second string.
     * @return a sequence (list) of inversions.
     */
    @Override
    public List<AdjacentSwapDescriptor> compute(String string1, String string2) {
        checkInputStrings(string1, string2);
        
        if (string1.equals(string2)) {
            return new ArrayList<>();
        }
        
        SolutionDescriptor solutionDescriptor = computeImpl(string1,
                                                            string2);
        
        return toAdjacentSwapDescriptorList(solutionDescriptor);
    }
    
    private static final List<AdjacentSwapDescriptor> 
        toAdjacentSwapDescriptorList(SolutionDescriptor solutionDescriptor) {
        List<AdjacentSwapDescriptor> list =
                new ArrayList<>(solutionDescriptor.permutationIndices.length);
        
        for (int index : solutionDescriptor.permutationIndices) {
            list.add(
                new AdjacentSwapDescriptor(
                        solutionDescriptor.tupleIndices[index]));
        }
        
        return list;
    }
    
    /**
     * Converts an internal representation of inversion indices to a list of
     * inversion descriptors.
     * 
     * @param inversionStartingIndices
     * @return 
     */
    private static List<AdjacentSwapDescriptor>
         toInversionDescriptorList(int[] inversionStartingIndices) {
         List<AdjacentSwapDescriptor> result = 
                 new ArrayList<>(inversionStartingIndices.length);
         
         for (int inversionStartingIndex : inversionStartingIndices) {
             result.add(new AdjacentSwapDescriptor(inversionStartingIndex));
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
    private static SolutionDescriptor computeImpl(String string1,   // DBAC -> DABC -> ADBC -> ABDC -> ABCD
                                                  String string2) { // (1, 2) -> (0, 1) -> (1, 2) -> (2, 3)
        char[] sourceStringChars = string1.toCharArray();
        char[] targetStringChars = string2.toCharArray();
        char[] bufferStringChars = new char[sourceStringChars.length];
        
        for (int inversions = 1; true; inversions++) {
            SolutionDescriptor solutionDescriptor = 
                    computeImpl(sourceStringChars, 
                                targetStringChars,
                                bufferStringChars,
                                inversions);
            
            if (solutionDescriptor != null) {
                return solutionDescriptor;
            }
        }
    }
    
    private static final class SolutionDescriptor {
        final int[] tupleIndices;
        final int[] permutationIndices;
        
        SolutionDescriptor(int[] tupleIndices, 
                           int[] permutationIndices) {
            this.tupleIndices = tupleIndices;
            this.permutationIndices = permutationIndices;
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
    private static SolutionDescriptor computeImpl(char[] sourceStringChars, 
                                                  char[] targetStringChars,
                                                  char[] bufferStringChars,
                                                  int inversions) {
        // string1.length() - 1, i.e., there are at most n - 1 distinct 
        // inversion pairs.
        ListTupleIndexIterator iterator = 
                new ListTupleIndexIterator(inversions, 
                                           sourceStringChars.length - 2);
                
        int[] indices = iterator.getIndexArray();
        
        // DBAC -> BDAC -> BADC -> ABDC -> ABCD
        // (0, 1) -> (1, 2) -> (0, 1) -> (2, 3)
        int num = 0;
        do {
            SolutionDescriptor solutionDescriptor = 
                    applyIndicesToWorkArray(sourceStringChars,
                                            targetStringChars,
                                            bufferStringChars,
                                            indices);
            System.out.println(num);
            num++;
            
            if (solutionDescriptor != null) {
                return solutionDescriptor;
            }
            
            iterator.generateNextTupleIndices();
        } while (iterator.hasNext());
        
        return null;
    }
    
    private static SolutionDescriptor 
         applyIndicesToWorkArray(char[] sourceCharArray,
                                 char[] targetCharArray,
                                 char[] bufferCharArray,
                                 int[] indices) {
        copy(sourceCharArray, bufferCharArray);
       
        PermutationIterable permutationIterable = 
                new PermutationIterable(indices.length);
        
        int[] permutationIndices = permutationIterable.getIndexArray();
        
        while (permutationIterable.hasNext()) {
            copy(sourceCharArray, 
                 bufferCharArray);
            
            if (permutationIndices.length == 4) {
                if (Arrays.equals(permutationIndices, new int[]{ 0, 2, 1, 3 }) ||
                        Arrays.equals(permutationIndices, new int[] { 1, 2, 0, 3 })) {
                            if (Arrays.equals(indices, new int[] { 0, 0, 1, 2 })) {
                                System.out.println("Yeah: " + Arrays.toString(permutationIndices));
                            }
                }
            }
            
            // For each inversion pair permutation, apply it and see whether we
            // got to the source character array:
            for (int i : permutationIndices) {
                int inversionIndex = indices[i];
                if (inversionIndex > 2) {
                    System.out.println("Oh fuck yeah!");
                }
                swap(bufferCharArray,
                     inversionIndex,
                     inversionIndex + 1);
            }
            
            if (Arrays.equals(bufferCharArray, targetCharArray)) {
                return new SolutionDescriptor(indices, permutationIndices);
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
    
    private static void copy(char[] sourceArray, char[] targetArray) {
        System.arraycopy(sourceArray, 
                         0, 
                         targetArray, 
                         0, 
                         sourceArray.length);
    }
}
