package net.coderodde.util;

import java.util.Arrays;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ListTupleIterableTest {

    @Test
    public void test() {
        System.out.println(
                "--- Start testing in " + this.getClass().getName() + " ---");
        
        ListTupleIndexIterator iter = new ListTupleIndexIterator(3, 2);
        int[] indices = iter.getIndexArray();
        int lineNumber = 1;
        
        while (iter.hasNext()) {
            System.out.printf(
                    "%3d: %s\n", 
                    lineNumber++, 
                    Arrays.toString(indices));
            iter.generateNextTupleIndices();
        }
        
        // 2 elements test.
        iter = new ListTupleIndexIterator(2, 1);
        indices = iter.getIndexArray();
        
        assertTrue(iter.hasNext());
        assertTrue(Arrays.equals(new int[] { 0, 0 }, indices));
        
        iter.generateNextTupleIndices();
        assertTrue(Arrays.equals(new int[] { 0, 1 }, indices));
        
        iter.generateNextTupleIndices();
        assertTrue(Arrays.equals(new int[] { 1, 0 }, indices));
        
        iter.generateNextTupleIndices();
        assertTrue(Arrays.equals(new int[] { 1, 1 }, indices));
        
        iter.generateNextTupleIndices();
        assertFalse(iter.hasNext());
        
        //// 1 element test.
        iter = new ListTupleIndexIterator(1, 0);
        indices = iter.getIndexArray();
        
        assertTrue(iter.hasNext());
        assertTrue(Arrays.equals(new int[] { 0 }, indices));
        iter.generateNextTupleIndices();
        assertFalse(iter.hasNext());
        
        System.out.println(
                "--- Done testing in " + this.getClass().getName() + " ---");
    }
}
