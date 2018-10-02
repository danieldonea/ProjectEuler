package euler.problem26;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem26Solutio - solution from the website using big decimal
 */
public class Solution2 {

    public static void main(String[] args){
        MathContext mc = new MathContext(4000);
        Pattern pattern = Pattern.compile("[\\d]*?([\\d]{8,}?)(\\1{2,}?)" + "[\\d]*?");

        int max = 0;
        int d = 0;
        String max_string = "";
        String max_group = "";
        for (int i=2; i<1000; i++) {
            BigDecimal numerator = new BigDecimal("1", mc);
            BigDecimal denominator = new BigDecimal(String.valueOf(i), mc);
            String str = numerator.divide(denominator, mc).toString();
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                if (matcher.group(1).length() > max) {
                    max = matcher.group(1).length();
                    d = i;
                    max_string = str;
                    max_group = matcher.group(1);
                }
            }
        }
        System.out.println("max is: " + max + " at index: " + d);
        System.out.println("max_string: " + max_string);
        System.out.println("max_group: " + max_group);
    }
}
