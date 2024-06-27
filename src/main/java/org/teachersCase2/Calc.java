package org.teachersCase2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    //public static boolean debug = false;
    public static boolean debug = true;
    public static int runCallCount = 0;

    public static int run(String exp) {
        runCallCount++;
        exp = exp.trim(); // 양 옆의 쓸데없는 공백 제거
        // 괄호 제거
        exp = stripOuterBrackets(exp);

        //만약 괄호 벗겼는데 문제생기면 다시 씌워줌
        if (exp.indexOf(")") < exp.indexOf("(")) {
            exp = "(" + exp + ")";
        }

        // 만약에 -( 패턴이라면, 내가 갖고있는 코드는 해석할 수 없으므로 해석할 수 있는 형태로 수정
        if (isCaseMinusBracket(exp)) {
            if (exp.indexOf(")") == exp.length() - 1) {
                exp = "(" + exp.substring(exp.indexOf("(")) + " * -1)";
            } else
                exp = "(" + exp.substring(exp.indexOf("("), exp.indexOf(")") + 1) + " * -1)" + exp.substring(exp.indexOf(")") + 1);
        }

        if (debug) {
            System.out.printf("exp(%d) : %s\n", runCallCount, exp);
        }
        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }
        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToMulti && needToPlus;
        if (needToSplit) {
            int splitPointIndex = findSplitPointIndex(exp);
            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex + 1);
            char operator = exp.charAt(splitPointIndex);
            exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);
            return Calc.run(exp);
        } else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");
            String newExp = Arrays.stream(bits).mapToInt(Calc::run).mapToObj(e -> e + "").collect(Collectors.joining(" + "));
            return run(newExp);
        }
        if (needToPlus) {
            exp = exp.replaceAll("- ", "+ -");
            String[] bits = exp.split(" \\+ ");
            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }
            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");
            int sum = 1;
            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }
            return sum;
        }
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아니야");
    }

    private static boolean isCaseMinusBracket(String exp) {
        // -( 로 시작하는지?
        if (exp.startsWith("-(") == false) return false;

        // 괄호로 감싸져 있는지?
        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            }
            if (bracketsCount == 0) {
                if (exp.length() - 1 == i) return true;
            }
        }

        return false;
    }

    private static int findSplitPointIndex(String exp) {
        int indexPlus = findSplitPointIndexBy(exp, '+');
        int indexMinus = findSplitPointIndexBy(exp, '-');

        int index = -1;

        if (indexPlus == -1 && indexMinus >= 0) return indexMinus;
        else if (indexPlus >= 0 && indexMinus == -1) return indexPlus;
        else if (indexPlus >= 0 && indexMinus >= 0) {
            index = Math.min(indexPlus, indexMinus);
        }

        if (index >= 0) return index;
        return findSplitPointIndexBy(exp, '*');
    }

    private static int findSplitPointIndexBy(String exp, char findChar) {

        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            } else if (c == findChar) {
                if (exp.charAt(i + 1) == ' ' && bracketsCount == 0) return i;
            }
        }
        return -1;
    }

    private static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;
        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }
        if (outerBracketsCount == 0) return exp;
        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
}