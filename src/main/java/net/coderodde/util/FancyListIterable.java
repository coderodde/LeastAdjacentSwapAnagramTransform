package net.coderodde.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This class implements an iterable over a list that generates "combinations"
 * that may repeat a list element. For example, given the list {@code 1, 2, 3},
 * this iterable will return {@code (1), (2), (3), (1, 1), (1, 2), (1, 3),
 * (2, 1), (2, 2), (2, 3), ...}.
 * 
 * @author Rodio "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class FancyListIterable<T> implements Iterable<List<T>> {

    private final FancyListIterator<T> iterator;
    
    public FancyListIterable(List<T> actualElements) {
        Objects.requireNonNull(actualElements, "The actual elements are null.");
        
        
        
        this.iterator = new FancyListIterator<>(actualElements);
    }
    
    @Override
    public Iterator<List<T>> iterator() {
        return this.iterator;
    }
}
