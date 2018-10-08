package euler.problem29;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Basic solution using BigInteger and set unique identity property.
 * Best/Worst case: speed O(n^2) and memory O(n^2)
 */
public class SolutionBasic {

    static Set<BigInteger> solution = new HashSet<>(); // result 9183

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        for (int base = 2; base < 101; base++){
            BigInteger curentBase = new BigInteger(String.valueOf(base));
            BigInteger currentResult = new BigInteger(String.valueOf(base));
            System.out.print("For int: " + base);
            int currentSolution = solution.size();
            for (int power = 2; power < 101; power++){ // can replace with while
                currentResult = currentResult.multiply(curentBase);
                solution.add(currentResult);
            }
            int addedSolution = solution.size();
            System.out.println("Added: " + (addedSolution - currentSolution));
        }
        long endMillis = System.currentTimeMillis();
        System.out.println("result>:"+ solution.size());
        System.out.println(endMillis - startMillis);
        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }
}
