package net.coderodde.util;

/**
 * This class implements list tuples iterators.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class ListTupleIndexIterator {

    private final int requestedMaximumIndex;
    private final int[] indices;
    private boolean iterationExhausted = false;
    
    public ListTupleIndexIterator(int requestedMaximumIndex) {
        this.requestedMaximumIndex = requestedMaximumIndex;
        this.indices = new int[requestedMaximumIndex];
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
        
        while (i >= 0 && indices[i] == requestedMaximumIndex) {
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
