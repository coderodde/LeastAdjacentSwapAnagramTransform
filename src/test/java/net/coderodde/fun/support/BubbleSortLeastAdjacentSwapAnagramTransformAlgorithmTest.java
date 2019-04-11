package net.coderodde.fun.support;

import java.util.List;
import net.coderodde.fun.AdjacentSwapDescriptor;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * This unit test class tests the correctness of a Bubble Sort -finder.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Apr 11, 2019)
 */
public class BubbleSortLeastAdjacentSwapAnagramTransformAlgorithmTest {
    
    private LeastAdjacentSwapAnagramTransformAlgorithm algorithm;
    private LeastAdjacentSwapAnagramTransformAlgorithm bruteForceAlgorithm;
    
    @Before
    public void beforeClass() {
        algorithm = new BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm();
        bruteForceAlgorithm =
                new BruteForceLeastAdjacentSwapAnagramTransformAlgorithm();
    }
    
    @Test
    public void test() {
        String source = "abc";
        String target = "cab";
        
        List<AdjacentSwapDescriptor> expectedResult = 
                bruteForceAlgorithm.compute(source, target);
        
        List<AdjacentSwapDescriptor> result = algorithm.compute(source, target);
        
        System.out.println("1: " + expectedResult);
        System.out.println("2: " + result);
        assertEquals(expectedResult, result);
    }
}
