package org.koreait;

import java.util.*;
import java.util.stream.IntStream;

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

        //부호 인덱스 1번이면 넘버 1, 2번끼리
        //부호 인덱스 0번이면 넘버 0, 1번끼리

        //System.out.println(myInts.size());

        //곱셈부터 처리해서 +, -만 있는 arraylist로 재정립

        while (myCalcs.contains("*")) { //부호 중 *이 있는동안 반복, * 중 앞에서부터 계산하게 된다
            int multiplyIndex = myCalcs.indexOf("*"); //*의 인덱스값을 찾아서 저장
            int multiInt1 = myInts.get(multiplyIndex); //규칙성에 따라 myInts에서 곱해질 항1을 저장
            int multiInt2 = myInts.get(multiplyIndex + 1); //규칙성에 따라 myInts에서 곱해질 항2를 저장
            myInts.set(multiplyIndex, multiInt1 * multiInt2); //곱해서 myInts의 앞쪽 인덱스에 집어넣고,
            myInts.remove(multiplyIndex + 1); //뒤의 값은 이미 사용했으므로 지운다
            myCalcs.remove(multiplyIndex); //*도 이미 사용했으므로 지운다
        }

        //재정립된 arraylist들로 +, - 계산

        int sum = myInts.get(0); //새로 재정립된 arraylist의 첫번째 값 (+, -가 없다면 *만으로 정립된 0번째 값을 리턴하도록)
        int calcsIndex = 0;

        for (int i = 1; i < myInts.size(); i++) {
            if (myCalcs.get(calcsIndex).equals("+")) {
                sum += myInts.get(i);
                calcsIndex++;
            } else if (myCalcs.get(calcsIndex).equals("-")) {
                sum -= myInts.get(i);
                calcsIndex++;
            } else throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다.");
        }

        return sum;
    }
}
