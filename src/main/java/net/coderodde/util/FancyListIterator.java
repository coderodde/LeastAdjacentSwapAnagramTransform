package net.coderodde.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements the Fancy list iterator.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class FancyListIterator<T> implements Iterator<List<T>> {

    private final int[] indices;
    private int indicesInUse;
    private final List<T> actualElements = new ArrayList<>();
    private List<T> nextElements;
    
    FancyListIterator(List<T> actualElements) {
        this.actualElements.addAll(actualElements);
        this.indices = new int[actualElements.size()];
        this.indicesInUse = 1;
        this.nextElements = Arrays.asList(actualElements.get(0));
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
        loadNextElements();
        return tmp;
    }
    
    private void loadNextElements() {
        int i = indicesInUse - 1;
        int n = actualElements.size();
        
        while (i >= 0 && indices[i] == n - 1) { 
            i--; 
        }
        
        if (i == -1) {
            if (indicesInUse == n) {
                // Cannot produce fancy lists anymore.
                nextElements = Collections.emptyList();
                return;
            } else {
                // Increment indicesInUse.
                indicesInUse++;
                for (int j = 0; j < indicesInUse; j++) {
                    indices[j] = 0;
                }
            }
        } else {
            indices[i]++;
            
            for (int j = i + 1; j < indicesInUse; j++) {
                indices[j] = 0;
            }
        }
        
        nextElements = new ArrayList<>(indicesInUse);
        
        for (int k = 0; k < indicesInUse; k++) {
            nextElements.add(actualElements.get(indices[k]));
        }
    }
}
