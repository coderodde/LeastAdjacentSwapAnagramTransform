package net.coderodde.fun.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm.AdjacentSwapDescriptor;
import net.coderodde.fun.support.BruteForceLeastAdjacentSwapAnagramTransformAlgorithm;

/**
 * This class implements a simple demonstration program.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 17, 2019)
 */
public final class Demo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeastAdjacentSwapAnagramTransformAlgorithm algorithm = 
                new BruteForceLeastAdjacentSwapAnagramTransformAlgorithm();
        
        while (scanner.hasNext()) {
            String words = scanner.nextLine();
            String[] splitWords = words.split(" ");
            
            if (splitWords.length != 2) {
                System.out.println(
                        "Oops! Two words expected. Got " + splitWords.length);
                continue;
            }
            
            String sourceString = splitWords[0];
            String targetString = splitWords[1];
            
            System.out.println(Arrays.toString(splitWords));
            
            long startTime = System.currentTimeMillis();
            List<AdjacentSwapDescriptor> list = null;
            
            try {
                list = algorithm.compute(sourceString, targetString);
            } catch (IllegalArgumentException ex) {
                System.out.println("Oops! " + ex.getMessage());
            }
            long endTime = System.currentTimeMillis();
            
            System.out.println(list + "> " + (endTime - startTime) 
                                           + " milliseconds.");
        }
    }
}
