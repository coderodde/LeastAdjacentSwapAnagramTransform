package net.coderodde.util;

import java.util.Arrays;

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
    
    public ListTupleIndexIterator(int numberOfIndices,
                                  int requestedMaximumIndex) {
        this.indices = new int[numberOfIndices];
        this.requestedMaximumIndex = requestedMaximumIndex;
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
    
    public static void main(String[] args) {
        ListTupleIndexIterator iter = new ListTupleIndexIterator(3, 4);
        int lineNumber = 1;
        
        while (iter.hasNext()) {
            System.out.println(
                    lineNumber++ + ": " + Arrays.toString(iter.getIndexArray()));
            iter.generateNextTupleIndices();
        }
    }
}
