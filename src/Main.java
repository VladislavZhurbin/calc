import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] arg) {
        //calculate until the "exit" is intercepted on input
        Scanner inputConsole = new Scanner(System.in);
        String consoleInput = inputConsole.nextLine().replaceAll("\\s+", "");
        while (!consoleInput.equals("exit")) {
            System.out.println(calc(consoleInput));
            consoleInput = inputConsole.nextLine().replaceAll("\\s+", "");
        }
        inputConsole.close();
    }
    public static String calc(String input) {
        Pattern userInputArabianPattern = Pattern.compile("^([1-9]|10)([/*+-])([1-9]|10)$");
        Pattern userInputRomePattern = Pattern.compile("^(IX|IV|V?I{0,3}|X)([/*+-])(IX|IV|V?I{0,3}|X)$");
        Matcher userInputArabianMatcher = userInputArabianPattern.matcher(input);
        Matcher userInputRomeMatcher = userInputRomePattern.matcher(input);
        String operationResult = "";
        var indexOfOperator = indexOfOperator(input);
        if (userInputArabianMatcher.matches()) {
            int operandFirst = Integer.parseInt(input.substring(0, indexOfOperator));
            int operandSecond = Integer.parseInt(input.substring(indexOfOperator + 1));
            operationResult = String.valueOf(resultCalculation(input, operandFirst, operandSecond));
        } else if (userInputRomeMatcher.matches()) {
            int operandFirst = arabianDigitsToNumbers(input.substring(0, indexOfOperator));
            int operandSecond = arabianDigitsToNumbers(input.substring(indexOfOperator + 1));
            int operationRomanResult = resultCalculation(input, operandFirst, operandSecond);
            if (operationRomanResult < 1) {
                System.out.println("throws Exception");
            } else {
                operationResult = arabianToRoman(operationRomanResult);
            }
        } else {
            System.out.println("throws Exception");
        }
        return operationResult;
    }
    static int resultCalculation(String input, int operandFirst, int operandSecond) {
        int operationResult = 0;
        if (input.indexOf("+") > 0) {
            operationResult = operandFirst + operandSecond;
        } else if (input.indexOf("-") > 0) {
            operationResult = operandFirst - operandSecond;
        } else if (input.indexOf("/") > 0) {
            operationResult = operandFirst / operandSecond;
        } else if (input.indexOf("*") > 0) {
            operationResult = operandFirst * operandSecond;
        }
        return operationResult;
    }
    static int indexOfOperator(String input) {
        int index = 0;
        if (input.indexOf("+") > 0) {
            index = input.indexOf("+");
        } else if (input.indexOf("-") > 0) {
            index = input.indexOf("-");
        } else if (input.indexOf("/") > 0) {
            index = input.indexOf("/");
        } else if (input.indexOf("*") > 0) {
            index = input.indexOf("*");
        }
        return index;
    }
    static int arabianDigitsToNumbers(String inputStr) {
        String romePartlyConvertedToArabian = inputStr.replace("I", "1,").replace("V", "5,").replace("X", "10,");
        String[] stringArray = romePartlyConvertedToArabian.split(",");
        int[] intArray = new int[stringArray.length];
        for (var i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        var result = 0;
        for (var i = 0; i < intArray.length; i++) {
            int s1 = intArray[i];
            if (i + 1 < intArray.length) {
                int s2 = intArray[i + 1];
                if (s1 >= s2) {
                    result += s1;
                } else {
                    result -= s1;
                }
            } else {
                result += s1;
            }
        }
        return result;
    }
    static String arabianToRoman(int num) {
        var keys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        var values = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
        String romanResult = "";
        int i = 0;
        while (i < keys.length) {
            while (num >= values[i]) {
                var d = num / values[i];
                num = num % values[i];
                for (int j = 0; j < d; j++)
                    romanResult += keys[i];
            }
            i++;
        }
        return romanResult;
    }
}