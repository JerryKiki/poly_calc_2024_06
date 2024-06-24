package org.koreait;

import java.util.Arrays;

public class Calc {

    public static int run(String exp) {
        //contains를 써서 불린으로 if문 해줄 수도 있음
        String[] bits = exp.split(" ");
        System.out.println(Arrays.toString(bits));
        int a = Integer.parseInt(bits[0]);
        int b = Integer.parseInt(bits[2]);
        int answer = 0;

        if (bits[1].equals("+")) {
            return plus(a, b);
        } else if (bits[1].equals("-")) {
            return minus(a, b);
        }

        throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다.");
    }

    static int plus(int a, int b) {
        return a + b;
    }

    static int minus(int a, int b) {
        return a - b;
    }

}
