package org.teachersCase;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calc {
    public static int run(String exp) {

        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

        boolean needToCompound = needToMulti && needToPlus;

        if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "") //객체가 아닌데 객체로 바꿔야 될때
                    .collect(Collectors.joining(" + ")); //" + "로 엮는다

            return run(newExp); //재귀함수의 기초
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

    private static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;

        while (exp.charAt(0) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if(outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
}
