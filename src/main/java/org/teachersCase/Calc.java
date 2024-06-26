package org.teachersCase;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {
    public static int run(String exp) {
        // (20 + 20) + 20
        // 양옆 쓸데없는 공백 제거
        exp = exp.trim();
        // 전체를 감싸는 괄호 제거
        exp = stripOuterBrackets(exp);

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToMulti && needToPlus;

        if (needToSplit) {
            int bracketsCount = 0;
            int idxOfFirstBracket = exp.indexOf("(");
            int splitPointIndex = -1;

            for (int i = idxOfFirstBracket; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    bracketsCount++;
                } else if (exp.charAt(i) == ')') {
                    bracketsCount--;
                }
                if (bracketsCount == 0) {
                    splitPointIndex = i;
                    break;
                }
            }
            String bracketExp = exp.substring(idxOfFirstBracket, splitPointIndex + 1); //괄호부터 괄호까지 자르는 건 해결
            //String secondExp = exp.substring(splitPointIndex + 4); //뒤에만 처리됨... 앞에 있을때도 처리해줘야함

            int bracketRs = Calc.run(bracketExp);

            exp = exp.replace(bracketExp, Integer.toString(bracketRs));

            //t24해결 내 방식
            //exp = exp.replace(firstExp, Integer.toString(Calc.run(firstExp)));
            //exp = exp.replace(secondExp, Integer.toString(Calc.run(secondExp)));

            //기존 exp 처리 replace 방식
            //char operator = exp.charAt(splitPointIndex + 2);
            //exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);

            return Calc.run(exp);

        } else if (needToCompound) {
            exp = exp.replaceAll("- ", "+ -");
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

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

    private static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;

        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if (outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
}
