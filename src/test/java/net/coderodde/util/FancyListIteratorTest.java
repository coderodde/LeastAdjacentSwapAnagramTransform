package net.coderodde.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

public class FancyListIteratorTest {

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        FancyListIterable<Integer> fli = new FancyListIterable<>(list);;
        Iterator<List<Integer>> iter = new FancyListIterator<Integer>(list);
        int lineNumber = 1;
        
        while (iter.hasNext()) {
            List<Integer> l = iter.next();
            System.out.printf("%3d: %s\n", lineNumber++, l);
        }
    }    
}
