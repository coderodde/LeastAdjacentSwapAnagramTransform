package net.coderodde.fun.support;

import java.util.Arrays;
import java.util.List;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm.AdjacentSwapDescriptor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodde
 */
public class BruteForceLeastAdjacentSwapAnagramTransformAlgorithmTest {
    
    private static final BruteForceLeastAdjacentSwapAnagramTransformAlgorithm
            ALGORITHM =
            new BruteForceLeastAdjacentSwapAnagramTransformAlgorithm();
    
    public BruteForceLeastAdjacentSwapAnagramTransformAlgorithmTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualStrings() {
        List<AdjacentSwapDescriptor> result = ALGORITHM.compute("ABC", 
                                                             "ABC");
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCompute() {
        String sourceString = "ABC";
        String targetString = "CAB";
        
        List<AdjacentSwapDescriptor> result = 
                ALGORITHM.compute(sourceString, targetString);
        
        assertEquals(2, result.size());
        apply(sourceString, targetString, result);
        assertEquals(result.get(0), new AdjacentSwapDescriptor(1));
        assertEquals(result.get(1), new AdjacentSwapDescriptor(0));
        
        sourceString = "DBAC";
        targetString = "ABCD";
        
        result = ALGORITHM.compute(sourceString, targetString);
        
        System.out.println(result);
    }
    
    private void apply(String sourceArray, 
                       String targetArray,
                       List<AdjacentSwapDescriptor> inversionDescriptorList) {
        char[] buffer = sourceArray.toCharArray();
        char[] target = targetArray.toCharArray();
        
        for (AdjacentSwapDescriptor inversionDescriptor :
                inversionDescriptorList) {
            swap(buffer, inversionDescriptor);
        }
        
        assertTrue(Arrays.equals(buffer, target));
    }
    
    private static void swap(char[] bufferChars, 
                             AdjacentSwapDescriptor inversionDescriptor) {
        int index1 = inversionDescriptor.startingIndex;
        int index2 = index1 + 1;
        
        char tmp = bufferChars[index1];
        bufferChars[index1] = bufferChars[index2];
        bufferChars[index2] = tmp;
    }
}
