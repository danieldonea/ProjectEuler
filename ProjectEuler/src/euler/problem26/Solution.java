package euler.problem26;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * euler.problem26.Solution without using Fermat's little theorem.
 */

//repetition on 2 to 1000. should do a backward call for maxlength
//AND max-length recursion < current recursion
public class Solution {

    /* regex patterm for finding a recurrence at the end of a string*/
    static String decimalRepeatPattern = "(\\d+)\\1$";
    static Pattern pattern = Pattern.compile(decimalRepeatPattern);

    public static void main(String[] args){
        int maxNumber = 0;
        int maxLength = 0;

        for (int i = 2; i<1000; i = i + 1){
            final String repetition = repetitionCycle(1, i, "");
            System.out.println( i + " | " + repetition);

            if (repetition.length() >= maxLength){
                maxLength = repetition.length();
                maxNumber = i;
            }
        }

        System.out.println(maxNumber);
    }

    /**+
     * Calculate the longest repetition cycle for nominator/denominator.
     * Initial call would be 1/denominator;
     *
     * @param nominator mandatory.
     * @param denominator mandatory.
     * @param calculated the value of the currently calculated value.
     * @return the calulated repetition cycle.
     */
    public static String repetitionCycle(int nominator, int denominator, String calculated){
        do{
            //always start with the a 0 at the end
            int cat = nominator * 10 / denominator;
            int rest = nominator * 10 % denominator;

            if (nominator != 0 && cat == 0 ){
                nominator *= 10;
                calculated += "0";
                continue;
            }
            calculated += cat;

            //new matcher used for all the calculated values
            Matcher m = pattern.matcher(calculated);

            if (m.find()){
                //result is stored in the position 1
                String repeatingGroup = m.group(1);

                String testCycle = getTestCycle(denominator, rest, repeatingGroup);
                //testCycle = testCycle.substring(0, testCycle.length() - 2 );
                while (testCycle.length() < repeatingGroup.length()*2)
                    testCycle = "0" + testCycle;
                repeatingGroup = repeatingGroup + repeatingGroup;
                if (repeatingGroup.equals(testCycle)) {
                    return m.group(1);
                }
            }

            if (nominator == 0){
                return "0";
            }
            nominator = rest;
            //return repetitionCycle(rest, denominator, calculated);
        } while (1 == 1);
    }

    /**
     * Test if the cycle repeats. After a repeatingGroup is found, check if the repetition occurs another time.
     * This is done to avoid repetitions as 32113
     *
     * @param denominator
     * @param rest
     * @param repeatingGroup
     * @return
     */
    private static String getTestCycle(int denominator, int rest, String repeatingGroup) {
        final BigInteger ten = new BigInteger("10");
        final BigInteger tenToTheLength = ten.pow(repeatingGroup.length()*2);
        return tenToTheLength.multiply(new BigInteger(Integer.toString(rest)))
                .divide(new BigInteger(Integer.toString(denominator))).toString();
    }
}
