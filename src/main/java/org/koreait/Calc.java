package org.koreait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {

    public static int run(String exp) {
        //contains를 써서 불린으로 if문 해줄 수도 있음
        String[] bits = exp.split(" ");
        List<Integer> myInts = new ArrayList<>();
        List<String> myCalcs = new ArrayList<>();

        for (int i = 0; i < bits.length; i++) {
            if (i % 2 == 0) {
                myInts.add(Integer.parseInt(bits[i]));
            } else {
                myCalcs.add(bits[i]);
            }
        }

        //System.out.println(myInts);
        //System.out.println(myCalcs);

        int sum = myInts.get(0);
        int calcsIndex = 0;

        for (int i = 1; i < myInts.size(); i++) {
            if (myCalcs.get(calcsIndex).equals("+")) {
                sum += myInts.get(i);
                calcsIndex++;
            } else if (myCalcs.get(calcsIndex).equals("-")) {
                sum -= myInts.get(i);
                calcsIndex++;
//            } else if (myCalcs.get(calcsIndex).equals("*")) {
//                sum *= myInts.get(i);
//                calcsIndex++;
//            } else if (myCalcs.get(calcsIndex).equals("/")) {
//                try {
//                    sum /= myInts.get(i);
//                } catch (ArithmeticException e) {
//                    System.out.println("0으로 나눌 수 없음");
//                }
            } else throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다.");
        }

        return sum;

        //3항에 + - 섞인 것까지 처리
//        if (bits.length == 3) {
//            int a = myInts.get(0);
//            int b = myInts.get(1);
//            if (myCalcs.get(0).equals("+")) {
//                return plus(a, b);
//            } else if (myCalcs.get(0).equals("-")) {
//                return minus(a, b);
//            }
//        } else if (bits.length == 5) {
//            int a = myInts.get(0);
//            int b = myInts.get(1);
//            int c = myInts.get(2);
//            int sum = 0;
//            if (myCalcs.get(0).equals("+")) {
//                sum = plus(a, b);
//            } else if (myCalcs.get(0).equals("-")) {
//                sum = minus(a, b);
//            }
//            if (myCalcs.get(1).equals("+")) {
//                return plus(sum, c);
//            } else if (myCalcs.get(1).equals("-")) {
//                return minus(sum, c);
//            }
//        }


    }

    static int plus(int a, int b) {
        return a + b;
    }

    static int minus(int a, int b) {
        return a - b;
    }

}
