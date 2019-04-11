package net.coderodde.fun.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.coderodde.fun.AdjacentSwapDescriptor;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm;
import static net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm.swap;

/**
 * This class implements an algorithm for least adjacent swap anagram transform
 * algorithm that mimics bubble sort.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 30, 2019)
 */
public final class BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm 
implements LeastAdjacentSwapAnagramTransformAlgorithm {

    private List<AdjacentSwapDescriptor> result = new ArrayList<>();
    private List<AdjacentSwapDescriptor> bestResult  = new ArrayList<>();
    private char[] sourceCharacters;
    private char[] targetCharacters;
    private char[] auxiliaryCharacters;
    private int maximumDepth;
    private int bestDepth;
    private int bestLength;
    
    private BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm
        (String sourceString,
         String targetString) {
        this.sourceCharacters = sourceString.toCharArray();
        this.targetCharacters = targetString.toCharArray();
        this.auxiliaryCharacters = Arrays.copyOf(sourceCharacters, 
                                                 sourceCharacters.length);
        this.maximumDepth = computeMaximumDepth();
        this.bestDepth = Integer.MAX_VALUE;
        this.bestLength = Integer.MAX_VALUE;
    }
    
    /**
     * Constructs the algorithm access object.
     */
    public BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm() {}
        
    /**
     * {@inheritDoc } 
     */
    @Override
    public List<AdjacentSwapDescriptor> compute(String string1, 
                                                String string2) {
        LeastAdjacentSwapAnagramTransformAlgorithm.checkInputStrings(string1, 
                                                                     string2);
        if (string1.equals(string2)) {
            return new ArrayList<>();
        }
        
        BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm state = 
            new BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm(string1,
                                                                     string2);
        state.computeImpl(0);
        return state.bestResult;
    }
    
    private final void computeImpl(int depth) {
        if (depth == maximumDepth) {
            return;
        }
        
        for (int i = 0; i < auxiliaryCharacters.length - 1; i++) {
            AdjacentSwapDescriptor adjacentSwapDescriptor = 
                    new AdjacentSwapDescriptor(i);

            swap(auxiliaryCharacters, adjacentSwapDescriptor);
            result.add(adjacentSwapDescriptor);

            if (Arrays.equals(auxiliaryCharacters, 
                              targetCharacters) && 
                    bestLength > result.size()) {
                bestLength = result.size();
                bestResult.clear();
                bestResult.addAll(result);
            } else {
                // Recur:
                computeImpl(depth + 1);
            }
            
            // Recur:
//            computeImpl(depth + 1);

            // Restore the state of this call:
            result.remove(result.size() - 1);
            swap(auxiliaryCharacters, adjacentSwapDescriptor);
        }
    }
    
    private final int computeMaximumDepth() {
        int n = sourceCharacters.length;
        return (n - 1) * n / 2;
    }
}
