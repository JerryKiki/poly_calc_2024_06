package org.teachersCase;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calc {
    public static int run(String exp) {

        if (exp.contains("(") && exp.contains(")")) {
            //괄호 안쪽을 재귀함수써서 새로운 exp 저장
            int startIndex = exp.indexOf("(");
            int endIndex = exp.lastIndexOf(")");
            String insideParenthesis = exp.substring(startIndex + 1, endIndex);
            String withParenthesis = exp.substring(startIndex, endIndex + 1);
            String replacementExp = Integer.toString(run(insideParenthesis));
            exp = exp.replace(withParenthesis, replacementExp);
        }

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
}
