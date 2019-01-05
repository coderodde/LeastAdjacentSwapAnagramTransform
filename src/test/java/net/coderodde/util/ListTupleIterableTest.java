package net.coderodde.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ListTupleIterableTest {

    @Test
    public void test() {
        System.out.println(
                "--- Start testing in " + this.getClass().getName() + " ---");
        
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<List<Integer>> iter = new ListTupleIterator<>(list);
        
        int lineNumber = 1;
        
        while (iter.hasNext()) {
            List<Integer> l = iter.next();
            System.out.printf("%3d: %s\n", lineNumber++, l);
        }
        
        // 2 elements test.
        list = Arrays.asList(1, 2);
        iter = new ListTupleIterator<>(list);
        
        assertTrue(iter.hasNext());
        assertEquals(Arrays.asList(1, 1), iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(Arrays.asList(1, 2), iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(Arrays.asList(2, 1), iter.next());
        
        assertTrue(iter.hasNext());
        assertEquals(Arrays.asList(2, 2), iter.next());
        
        assertFalse(iter.hasNext());
        
        //// 1 element test.
        list = Arrays.asList(1);
        iter = new ListTupleIterator<>(list);
        
        assertTrue(iter.hasNext());
        assertEquals(Arrays.asList(1), iter.next());
        assertFalse(iter.hasNext());
        
        System.out.println(
                "--- Done testing in " + this.getClass().getName() + " ---");
    }    
}
