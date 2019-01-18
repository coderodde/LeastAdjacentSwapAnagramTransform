package net.coderodde.util;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This class permutes an integer index array.
 * 
 * @author Rodde "rodde" Efremov
 * @version 1.6 (Jan 4, 2019)
 */
public final class PermutationIterable {

    private final int[] indices;
    private boolean iterationExhausted;
    
    public PermutationIterable(int numberoOfObjectsToPermute) {
        this.indices = IntStream.range(0, numberoOfObjectsToPermute).toArray();
        this.iterationExhausted = false;
    }
    
    public boolean hasNext() {
        return !iterationExhausted;
    }
    
    public int[] getIndexArray() {
        return indices;
    }
    
    public void generateNextPermutation() {
        int inversionStartIndex = findAscendingPairStartIndex();
        
        if (inversionStartIndex == -1) {
            iterationExhausted = true;
            return;
        }
        
        int largestElementIndex = 
                findSmallestElementIndexLargerThanInputIndex(
                    inversionStartIndex + 1,
                    indices[inversionStartIndex]);
        
        swap(indices,
             inversionStartIndex, 
             largestElementIndex);
        
        reverse(indices,
                inversionStartIndex + 1,
                indices.length);
    }
    
    /**
     * Seeks for the starting index of the rightmost ascending pair in the 
     * index array.
     * 
     * @return the starting index of the rightmost ascending pair.
     */
    private final int findAscendingPairStartIndex() {
        for (int i = indices.length - 2; i >= 0; i--) {
            if (indices[i] < indices[i + 1]) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Returns the index of the smallest integer no smaller or equal to 
     * {@code lowerBound}.
     * 
     * @param lowerBoundIndex the smallest relevant index into the array
     *                        prefix.
     * @param lowerBound
     * @return 
     */
    private int findSmallestElementIndexLargerThanInputIndex(
            int lowerBoundIndex,
            int lowerBound) {
        int smallestFitElement = Integer.MAX_VALUE;
        int smallestFitElementIndex = -1;

        for (int i = lowerBoundIndex;
                 i < indices.length;
                 i++) {
            int currentElement = indices[i];

            if (currentElement > lowerBound
                    && currentElement < smallestFitElement) {
                smallestFitElement = currentElement;
                smallestFitElementIndex = i;
            }
        }

        return smallestFitElementIndex;
    }
    
    private static final void swap(int[] array, 
                                   int index1,
                                   int index2) {
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
    
    private static final void reverse(int[] array,
                                      int fromIndex,
                                      int toIndex) {
        for (int i = fromIndex, j = toIndex - 1; i < j; i++, j--) {
            swap(array, i, j);
        }
    }
}
