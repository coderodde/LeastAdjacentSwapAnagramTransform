package net.coderodde.fun.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.coderodde.fun.AdjacentSwapDescriptor;
import net.coderodde.util.ListTupleIndexIterator;
import net.coderodde.util.PermutationIterator;
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
    public List<AdjacentSwapDescriptor> compute(String string1, 
                                                String string2) {
        LeastAdjacentSwapAnagramTransformAlgorithm
                .checkInputStrings(string1, string2);

        if (string1.equals(string2)) {
            // Trivial case: input anagrams are equal.
            return new ArrayList<>();
        }

        SolutionDescriptor solutionDescriptor = computeImpl(string1,
                                                            string2);

        return toAdjacentSwapDescriptorList(solutionDescriptor);
    }

    /**
     * Converts internal representation of a solution to the one according to
     * API.
     * 
     * @param solutionDescriptor the solution descriptor.
     * @return solution.
     */
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

    /**
     * Holds internal representation of a solution. tupleIndices[i] holds the 
     * index of the i'th adjacent swap to apply to the source array in order to 
     * convert the source string to the target string. permutationIndices[i] 
     * specifies the order the indices from tupleIndices should be applies.
     */
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
        // inversion pairs, with largest value n - 1:
        ListTupleIndexIterator iterator = 
                new ListTupleIndexIterator(inversions, 
                                           sourceStringChars.length - 2);

        int[] indices = iterator.getIndexArray();

        do {
            SolutionDescriptor solutionDescriptor = 
                    applyIndicesToWorkArray(sourceStringChars,
                                            targetStringChars,
                                            bufferStringChars,
                                            indices);
            if (solutionDescriptor != null) {
                return solutionDescriptor;
            }

            iterator.generateNextTupleIndices();
        } while (iterator.hasNext());

        return null;
    }

    /**
     * Permutes all the entries in the {@code indices} and for each index 
     * permutation applies the sequence and checks to see if they are sufficient
     * for transforming the source array to the target array.
     * 
     * @param sourceCharArray the source character array.
     * @param targetCharArray the target character array.
     * @param bufferCharArray the buffer character array.
     * @param indices the adjacent swap indices.
     * @return a solution descriptor.
     */
    private static SolutionDescriptor 
         applyIndicesToWorkArray(char[] sourceCharArray,
                                 char[] targetCharArray,
                                 char[] bufferCharArray,
                                 int[] indices) {
        copy(sourceCharArray, bufferCharArray);

        PermutationIterator permutationIterable = 
                new PermutationIterator(indices.length);

        int[] permutationIndices = permutationIterable.getIndexArray();

        while (permutationIterable.hasNext()) {
            copy(sourceCharArray, 
                 bufferCharArray);

            // For each inversion pair permutation, apply it and see whether we
            // got to the source character array:
            for (int i : permutationIndices) {
                int inversionIndex = indices[i];

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

    /**
     * Throws entire {@code sourceArray} to [@code targetArray}.
     * @param sourceArray the source array.
     * @param targetArray the target array.
     */
    private static void copy(char[] sourceArray, char[] targetArray) {
        System.arraycopy(sourceArray, 
                         0, 
                         targetArray, 
                         0, 
                         sourceArray.length);
    }
}
