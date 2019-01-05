package net.coderodde.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class implements the Fancy list iterator.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class ListTupleIterator<T> implements Iterator<List<T>> {

//    private static final class SimpleIntArray {
//        private int[] array;
//        private int endIndex;
//        
//        SimpleIntArray() {
//            array = new int[10];
//        }
//        
//        void add() {
//            ensureCapacity(endIndex);
//            array[endIndex++] = 0;
//        }
//        
//        int get(int index) {
//            return array[index];
//        }
//        
//        private void ensureCapacity(int requestedCapacity) {
//            if (requestedCapacity > array.length) {
//                array = Arrays.copyOf(array, 2 * endIndex);
//            }
//        }
//    }
    
//    private final SimpleIntArray indices;
    private final int[] indices;
    private final List<T> actualElements = new ArrayList<>();
    private List<T> nextElements;
    
    ListTupleIterator(List<T> actualElements) {
        this.actualElements.addAll(actualElements);
        this.indices = new int[actualElements.size()];
        this.nextElements = new ArrayList<>();
        
        for (int i = 0; i < this.indices.length; i++) {
            this.nextElements.add(this.actualElements.get(0));
        }
    }
    
    @Override
    public boolean hasNext() {
        return !nextElements.isEmpty();
    }

    @Override
    public List<T> next() {
        if (nextElements.isEmpty()) {
            throw new IllegalStateException("Nothing to iterate over.");
        }
        
        List<T> tmp = nextElements;
        generateNextTuple();
        return tmp;
    }
    
    private void generateNextTuple() {
        int n = indices.length;
        int i = n - 1;
        
        while (i >= 0 && indices[i] == n - 1) {
            i--;
        }
        
        if (i < 0) {
            this.nextElements = Collections.<T>emptyList();
            return;
        }
        
        indices[i]++;
        
        for (int j = i + 1; j < n; j++) {
            indices[j] = 0;
        }
        
//        if (indices[i] == n) {
//            for (int j = i + 1; j < n; j++) {
//                indices[j] = 0;
//            }
//        }
        
        this.nextElements = new ArrayList<>(n);
        
        for (int j = 0; j < n; j++) {
            this.nextElements.add(this.actualElements.get(indices[j]));
        }
    }
    
//    private void loadNextElements() {
//        int i = indicesInUse - 1;
//        int n = actualElements.size();
//        
//        while (i >= 0 && indices[i] == n - 1) {
//            i--; 
//        }
//        
//        if (i == -1) {
//            if (indicesInUse == n) {
//                // Cannot produce fancy lists anymore.
//                nextElements = Collections.emptyList();
//                return;
//            } else {
//                // Increment indicesInUse.
//                indicesInUse++;
//                
//                for (int j = 0; j < indicesInUse; j++) {
//                    indices[j] = 0;
//                }
//            }
//        } else {
//            indices[i]++;
//            
//            for (int j = i + 1; j < indicesInUse; j++) {
//                indices[j] = 0;
//            }
//        }
//        
//        nextElements = new ArrayList<>(indicesInUse);
//        
//        for (int k = 0; k < indicesInUse; k++) {
//            nextElements.add(actualElements.get(indices[k]));
//        }
//    }
}
