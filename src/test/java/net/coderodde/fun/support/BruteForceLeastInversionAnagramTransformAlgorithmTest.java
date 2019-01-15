package net.coderodde.fun.support;

import java.util.List;
import net.coderodde.fun.LeastInversionsAnagramTransformAlgorithm;
import net.coderodde.fun.LeastInversionsAnagramTransformAlgorithm.InversionDescriptor;
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
public class BruteForceLeastInversionAnagramTransformAlgorithmTest {
    
    public BruteForceLeastInversionAnagramTransformAlgorithmTest() {
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
        List<InversionDescriptor> result  = 
                new BruteForceLeastInversionAnagramTransformAlgorithm()
                        .compute("ABC", 
                                 "ABC");
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCompute() {
        
    }
    
}
