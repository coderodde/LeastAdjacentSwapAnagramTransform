package net.coderodde.fun.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import net.coderodde.fun.AdjacentSwapDescriptor;
import net.coderodde.fun.LeastAdjacentSwapAnagramTransformAlgorithm;
import net.coderodde.fun.support.BruteForceLeastAdjacentSwapAnagramTransformAlgorithm;
import net.coderodde.fun.support.BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm;

/**
 * This class implements a simple demonstration program.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 17, 2019)
 */
public final class Demo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeastAdjacentSwapAnagramTransformAlgorithm bruteForceAlgorithm = 
                new BruteForceLeastAdjacentSwapAnagramTransformAlgorithm();
        
        LeastAdjacentSwapAnagramTransformAlgorithm bubbleForceAlgorithm = 
                new BubbleSortLeastAdjacentSwapAnagramTransformAlgorithm();
        
        while (scanner.hasNext()) {
            String words = scanner.nextLine();
            
            if (words.trim().equals("bye")) {
                return;
            }
            
            String[] splitWords = words.split(" ");
            
            if (splitWords.length != 2) {
                System.out.println(
                        "Oops! Two words expected. Got " + splitWords.length);
                continue;
            }
            
            String sourceString = splitWords[0];
            String targetString = splitWords[1];
            
            System.out.println(Arrays.toString(splitWords));
            
            List<AdjacentSwapDescriptor> list1;
            List<AdjacentSwapDescriptor> list2;
            long startTime = System.currentTimeMillis();
            
            try {
                list1 = bruteForceAlgorithm.compute(sourceString,
                                                    targetString);
            } catch (IllegalArgumentException ex) {
                list1 = null;
                System.out.println("Oops! " + ex.getMessage());
            }
            
            long endTime = System.currentTimeMillis();
                        
            printAdjscentSwapDescriptorList(list1);
            System.out.println((endTime - startTime) + " milliseconds.");
            
            startTime = System.currentTimeMillis();
            list2 = bubbleForceAlgorithm.compute(sourceString, targetString);
            endTime = System.currentTimeMillis();
            
            printAdjscentSwapDescriptorList(list2);
            System.out.println((endTime - startTime) + " milliseconds.");
        }
    }
    
    private static final void printAdjscentSwapDescriptorList(
        List<AdjacentSwapDescriptor> adjacentSwapDescriptorList) {
        int fieldWidth = ("" + adjacentSwapDescriptorList.size()).length();
        int lineNumber = 1;
        
        for (AdjacentSwapDescriptor adjacentSwapDescriptor : 
                adjacentSwapDescriptorList) {
            System.out.printf("%" + fieldWidth + "d: %s\n",
                              lineNumber++,
                              adjacentSwapDescriptor);
        }
    }
}
