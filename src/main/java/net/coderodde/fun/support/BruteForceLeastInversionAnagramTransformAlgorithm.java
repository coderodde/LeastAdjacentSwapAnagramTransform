package net.coderodde.fun.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import net.coderodde.fun.LeastInversionsAnagramTransformAlgorithm;
import static net.coderodde.fun.LeastInversionsAnagramTransformAlgorithm.areAnagrams;
import net.coderodde.util.ListTupleIterable;
import net.coderodde.util.PermutationIterable;

/**
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 5, 2019)
 */
public final class BruteForceLeastInversionAnagramTransformAlgorithm 
implements LeastInversionsAnagramTransformAlgorithm {

    @Override
    public List<InversionIndexPair> compute(String string1, String string2) {
        checkInputStrings(string1, string2);
        return computeImpl(string1, string2);
    }
    
    private static void checkInputStrings(String string1, String string2) {
        Objects.requireNonNull(string1, "The first input string is null.");
        Objects.requireNonNull(string2, "The second input string is null.");
        checkStringsHaveSameLength(string1, string2);
        checkStringsAreAnagrams(string1, string2);
    }
    
    private static void checkStringsHaveSameLength(String string1,
                                                String string2) {
        if (string1.length() != string2.length()) {
            throw new IllegalArgumentException(
                    "The two input streams have different lengths: " + 
                    string1.length() + " vs. " + string2.length());
        }
    }
    
    private static void checkStringsAreAnagrams(String string1,
                                                String string2) {
        if (!areAnagrams(string1, string2)) {
            throw new IllegalArgumentException(
                    "The two input strings are not anagrams.");
        }
    }
    
    private static List<InversionIndexPair> computeImpl(String string1, 
                                                        String string2) {
        for (int inversions = 1; true; inversions++) {
            List<InversionIndexPair> solution = computeImpl(string1, 
                                                            string2,
                                                            inversions);
            
            if (solution != null) {
                return solution;
            }
        }
    }
    
    private static List<InversionIndexPair> computeImpl(String string1, 
                                                        String string2,
                                                        int inversions) {
        List<InversionIndexPair> inversionList = 
                createSolutionCandidate(inversions);
        
        char[] chars1 = string1.toCharArray();
        char[] chars2 = string2.toCharArray();
        char[] workArray = chars1.clone();
        
        Iterator<List<InversionIndexPair>> fancyIterator = 
                new ListTupleIterable<>(inversionList).iterator();
        
        while (!Arrays.equals(chars1, workArray) && fancyIterator.hasNext()) {
            List<InversionIndexPair> list = fancyIterator.next();
            produceInversionListPermutations(list);
        }
    }
    
    private static List<InversionIndexPair> 
            produceInversionListPermutations(
                    char[] sourceCharacters,
                    char[] targetCharacters,
                    char[] workCharacters,
                    List<InversionIndexPair> inversionPairs) {
        Iterator<List<InversionIndexPair>> iterator =
                new PermutationIterable<>(inversionPairs).iterator();
        
        while (iterator.hasNext()) {
            List<InversionIndexPair> colustionCandidate = iterator.next();
            applyToWorkArray(workCharacters, 
                             sourceCharacters, 
                             solutionCandidate);
            
            if (Arrays.equals(targetCharacters, workCharacters)) {
                
            }
        }
        
        List<InversionIndexPair> solution;
    }
    
    private static List<Inverion applyToWorkArray(char[] workCharacters,
                                                  char[] sourceCharacters,
                                                  List<Inverion)
            
    private static List<InversionIndexPair> createSolutionCandidate(
            int inversions) {
        return new ArrayList<>(
                IntStream.range(0, inversions).map((int i) -> {
                    return new InversionIndexPair(0, 1);
                }));
    }
}
