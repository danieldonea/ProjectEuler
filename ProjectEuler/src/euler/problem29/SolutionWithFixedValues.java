package euler.problem29;

import java.lang.management.ThreadInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Fastest solution based on observation of the pattern.
 *  1. Prime numbers or numbers of the format (a^x * b^y * c^z * etc; where x != y or y != z or z != y or etc)
 *      will never repeat in a sequence. result is n - 1.
 *  2. when x == y == z
 *      a. x = 2 the value is always (n+1)/2
 *      b. x = 3 the value is (n+1)/2 or (n+1)/2 + 1; depending on the n
 *      c. x = 4 the value is always (n+1)/2 - 9. for n = 100. general rule can be calculated as fixed
 *      d. x = 5 the value is always (n+1)/2 + 1. for n = 100. general rule can be calculated as fixed
 *      e. x = 6 the value is always (n+1)/2 - 13. for n = 100. general rule can be calculated as fixed
 *
 *  Results are 4x faster with reduced memory overhead.
 *  Best case: speed O(n) and memory O(1)
 *  Worst case: speed O(25*n) ~ O(n) and memory O(1)
 */
public class SolutionWithFixedValues {

    public static final int N_MINUS_ONE = 99;
    /** A list of all the prime numbers up to 100 */
    static List<Integer> primes =
            Arrays.asList(2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97);

    public static void main(String[] args) {
        long startMilis = System.currentTimeMillis();
        int distinctNumbers = 0; // actual solution to the problem
        for(int i = 2; i < 101; i++){
            System.out.print("For int: " + i);
            int initialDistinctNumbers = distinctNumbers;
            if (primes.contains(i)){ // if the number is a prime, it will never repeat the previous
                distinctNumbers = distinctNumbers + N_MINUS_ONE;
                System.out.println(" Added: " + (distinctNumbers - initialDistinctNumbers));
                continue;
            }

            distinctNumbers = distinctNumbers + isPowerOfAnotherNumber(i);

            System.out.println(" Added: " + (distinctNumbers - initialDistinctNumbers));
        }
        long endMillis = System.currentTimeMillis();
        System.out.println(" result:>" + distinctNumbers);
        System.out.println(endMillis - startMilis);
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    public static int isPowerOfAnotherNumber(int number) {
        Map<Integer, Integer> primeMap = new HashMap();

        int lastPrimePower = 0;
        for (int prime : primes) {
            int modalityTestValue = number % prime;
            if (modalityTestValue == 0) {
                int occurrenceNumber = 1;
                number = number / prime;
                while (number % prime == 0) {
                    number = number / prime;
                    occurrenceNumber++;
                }

                if (lastPrimePower != occurrenceNumber && lastPrimePower != 0) {
                    // distinct powers give distinct results. they will never repeat
                    return N_MINUS_ONE;
                } else {
                    lastPrimePower = occurrenceNumber;
                }
                primeMap.put(prime, occurrenceNumber);
            }
        }

        int powerValues = primeMap.values().iterator().next();
        return getNumberOfOccurences(powerValues);

    }

    private static int getNumberOfOccurences(int powerValues) {
        // the result can be calculated from progression of numbers
        switch (powerValues) {
            case 1:
                return N_MINUS_ONE; // just to the power of one - no repetition
            case 2:
                return 50; // (n+1)/2
            case 3:
                return 50; // (n+1)/2
            case 4:
                return 41; // (n+1)/2 - 9 (9 repetitons appear between 2 and 3)
            case 5:
                return 51; // (n+1)/2 + 1
            case 6:
                return 37; // (n+1)/2 - 13 (13 repetitons appear between 2, 3 and 5)
            default:
                return 0; // was not taken into account as it was not necessary
        }
    }

}
