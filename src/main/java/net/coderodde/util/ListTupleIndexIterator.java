package net.coderodde.util;

/**
 * This class implements list tuples iterators. Given 
 * {@code requestedMaximumIndexValue} and {@code numberOfIndices}, set all 
 * {@code numberOfIndices} to zero. Than increments the first index until it has
 * value [@code requestedMaximumIndex}, after which, resets the index and sets
 * the second index to 1. This is continued until the second index becomes 
 * {@code requestedMaximumIndex}, after which the two indices are set to all
 * and the third index is incremented. This continues until all indices are set
 * to {@code requestedMaximumIndexValue}. I
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class ListTupleIndexIterator {

    private final int requestedMaximumIndexValue;
    private final int[] indices;
    private boolean iterationExhausted = false;
    
    public ListTupleIndexIterator(int numberOfIndices,
                                  int requestedMaximumIndex) {
        this.indices = new int[numberOfIndices];
        this.requestedMaximumIndexValue = requestedMaximumIndex;
    }
    
    public int[] getIndexArray() {
        return indices;
    }
    
    public boolean hasNext() {
        return !iterationExhausted;
    }
    
    public void generateNextTupleIndices() {
        int n = indices.length;
        int i = n - 1;
        
        while (i >= 0 && indices[i] == requestedMaximumIndexValue) {
            i--;
        }
        
        if (i < 0) {
            iterationExhausted = true;
            return;
        }
        
        indices[i]++;
        
        for (int j = i + 1; j < n; j++) {
            indices[j] = 0;
        }
    }
}
