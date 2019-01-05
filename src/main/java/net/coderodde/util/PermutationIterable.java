package net.coderodde.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class implements an iterable over a list. The underlying permuting 
 * algorithm is deterministic but does not produce the permutation in any 
 * interesting order. The actual list to permute does not change the original
 * order. What comes to actual permutation, for each a new empty list is
 * constructed so it is safe to modify each of them.
 * 
 * @author Rodde "rodde" Efremov
 * @version 1.6 (Jan 4, 2019)
 */
public class PermutationIterable<T> implements Iterable<List<T>> {

    /**
     * The actual list from which to compute the permutation. This will not 
     * change its order.
     */
    private final List<T> actualElements = new ArrayList<>();
    
    /**
     * Constructs this iterable object.
     * 
     * @param actualElements the list to permute.
     */
    public PermutationIterable(List<T> actualElements) {
        this.actualElements.addAll(actualElements);
    }
    
    @Override
    public Iterator<List<T>> iterator() {
        return actualElements.isEmpty() ?
                new EmptyIterator() : 
                new PermutationIterator(actualElements);
    }
    
    /**
     * A stub permutation iterator for empty input lists.
     */
    private final class EmptyIterator implements Iterator<List<T>> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public List<T> next() {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * The actual permutation iterator for non-empty input llsts.
     */
    private final class PermutationIterator implements Iterator<List<T>> {

        /**
         * The list to return upon next call to {@code next}. Set to an empty
         * list when there is no more permutations to compute.
         */
        private List<T> nextElements;
        
        /**
         * The list of actual elements to permute.
         */
        private final List<T> actualElements = new ArrayList<>();
        
        /**
         * The array of indices. Each new permutation is generated by setting
         * {@code i}th element in {@code nextElements} to 
         * {@code actualElements.get(indices[i]}.
         */
        private final int[] indices;
        
        private PermutationIterator(List<T> actualElements) {
            this.actualElements.addAll(actualElements);
            this.indices = createIndexArray(actualElements.size());
            this.nextElements = populateOutputArray();
        }
        
        @Override
        public boolean hasNext() {
            return !nextElements.isEmpty();
        }

        @Override
        public List<T> next() {
            List<T> tmp = this.nextElements;
            constructNextPermutation();
            return tmp;
        }
        
        /*
         * Loads {@code nextEleements} with the next permutation to remove.
         */
        private void constructNextPermutation() {
            int inversionStartIndex = findAscendingPairStartIndex();
            
            if (inversionStartIndex == -1) {
                this.nextElements = Collections.<T>emptyList();
                return;
            }
            
            int largestElementIndex =
                    findSmallestElementIndexLargerThanInputIndex(
                            inversionStartIndex + 1, 
                            indices[inversionStartIndex]);
            
            swap(indices, inversionStartIndex, largestElementIndex);
            reverse(indices, inversionStartIndex + 1, indices.length);
            this.nextElements = populateOutputArray();
        }
        
        private int[] createIndexArray(int size) {
            return IntStream.range(0, size).toArray();
        }
        
        /*
         * Scans the index array from right to left stopping when an ascending
         * pair is encountered, returning the smaller index of the two. 
         */
        private int findAscendingPairStartIndex() {
            int i = actualElements.size() - 2;
            
            for (; i >= 0; i--) {
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
        
        /**
         * Swaps to array components.
         * 
         * @param array  the target array.
         * @param index1 the index of one array component.
         * @param index2 the index of another array component.
         */
        private void swap(int[] array, int index1, int index2) {
            int tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
        }
        
        /**
         * Reverses an integer array {@code array[startIndex], ...,
         * endIndex}.
         * 
         * @param array      the target array.
         * @param startIndex the starting, inclusive index.
         * @param endIndex   the ending, exclusive index.
         */
        private void reverse(int[] array, int startIndex, int endIndex) {
            for (int i = startIndex, j = endIndex - 1; i < j; i++, j--) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        
        /**
         * Creates a new permutation list according to current indices.
         */
        private List<T> populateOutputArray() {
            List<T> out = new ArrayList<>(indices.length);
            IntStream.of(indices)
                     .forEach((int i) -> { out.add(actualElements.get(i));});
            
            return out;
        }
    }
    
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("0", "1", "2", "3");
        int lineNumber = 1;
        
        for (List<String> permutation : new PermutationIterable<>(strings)) {
            
            System.out.printf("%2d: %s\n", lineNumber++, permutation);
        }
    }
}
