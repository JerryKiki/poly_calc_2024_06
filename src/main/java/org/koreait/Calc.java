package org.koreait;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calc {

    public static int run(String exp) {

        //괄호가 있으면
        while (exp.contains("(") && exp.contains(")")) {
            //괄호 따로 떼어내서 감지
            String[] expArr = exp.split("");
            String[] parenthesis = Arrays.stream(expArr)
                    .filter(e -> Objects.equals(e, "(") || Objects.equals(e, ")"))
                    .toArray(String[]::new);
            //괄호 안쪽을 재귀함수써서 새로운 exp 저장
            if (!parenthesis[0].equals(parenthesis[1])) {
                //첫번째와 두번째가 다른 게 나오면 다음 걸 찾고
                int startIndex = exp.indexOf("(");
                int endIndex = exp.indexOf(")");
                String insideParenthesis = exp.substring(startIndex + 1, endIndex);
                String withParenthesis = exp.substring(startIndex, endIndex + 1);
                String replacementExp = Integer.toString(run(insideParenthesis));
                exp = exp.replace(withParenthesis, replacementExp);
            } else {
                //첫번째와 두번째가 같은 게 나오면 원래대로
                int startIndex = exp.indexOf("(");
                int endIndex = exp.lastIndexOf(")");
                String insideParenthesis = exp.substring(startIndex + 1, endIndex);
                String withParenthesis = exp.substring(startIndex, endIndex + 1);
                String replacementExp = Integer.toString(run(insideParenthesis));
                exp = exp.replace(withParenthesis, replacementExp);
            }
        }

        //괄호를 처리한 스트링을 공백으로 split
        String[] bits = exp.split(" ");
        List<Integer> myInts = new ArrayList<>(); //숫자만 담을 ArrayList
        List<String> myCalcs = new ArrayList<>(); //부호만 담을 ArrayList

        //ArrayList에 숫자와 부호를 구분해서 저장
        for (int i = 0; i < bits.length; i++) {
            if (i % 2 == 0) {
                myInts.add(Integer.parseInt(bits[i])); //0을 포함한 짝수번째에는 int로 변환해 숫자리스트에 저장
            } else {
                myCalcs.add(bits[i]); //홀수번째에는 String 그대로 부호리스트에 저장
            }
        }

        //인덱스 규칙성 발견
        //부호 인덱스 1번이면 넘버 인덱스 1, 2번끼리
        //부호 인덱스 0번이면 넘버 인덱스 0, 1번끼리
        //부호인덱스 * 부호인덱스+1 해주면 됨
        //곱셈부터 처리해서 +, -만 있는 arraylist로 재정립

        while (myCalcs.contains("*") || myCalcs.contains("/")) { //부호 중 *이나 /가 있는동안 반복, 앞에서부터 계산하게 된다
            int multiplyIndex = checkMultiply(myCalcs); //*의 인덱스값을 찾아서 저장, 없으면 -1이됨
            int divideIndex = checkDivisor(myCalcs); // /의 인덱스값을 찾아서 저장, 없으면 -1이됨
            if (multiplyIndex != -1 && divideIndex != -1) {
                if (multiplyIndex < divideIndex) {//곱셈 인덱스가 더 앞이면 곱셈을 함
                    myInts = myIntsAfterMultiply(myInts, multiplyIndex);
                    myCalcs.remove(multiplyIndex); //*도 이미 사용했으므로 지운다
                } else { //나눗셈 인덱스가 더 앞이면 나눗셈을 함
                    myInts = myIntsAfterDivide(myInts, divideIndex);
                    myCalcs.remove(divideIndex);
                }
            } else if (multiplyIndex != -1) {
                myInts = myIntsAfterMultiply(myInts, multiplyIndex);
                myCalcs.remove(multiplyIndex); //*도 이미 사용했으므로 지운다
            } else if (divideIndex != -1) {
                myInts = myIntsAfterDivide(myInts, divideIndex);
                myCalcs.remove(divideIndex);
            }
        }

        //재정립된 arraylist들로 +, - 계산
        int sum = plusAndMinus(myInts, myCalcs);

        return sum;
    }

    public static int checkMultiply(List<String> myCalcs) {
        if (myCalcs.contains("*")) {
            return myCalcs.indexOf("*");
        }
        return -1;
    }

    public static int checkDivisor(List<String> myCalcs) {
        if (myCalcs.contains("/")) {
            return myCalcs.indexOf("/");
        }
        return -1;
    }

    public static List<Integer> myIntsAfterMultiply(List<Integer> myInts, int multiplyIndex) {
        int multiInt1 = myInts.get(multiplyIndex); //규칙성에 따라 myInts에서 곱해질 항1을 저장
        int multiInt2 = myInts.get(multiplyIndex + 1); //규칙성에 따라 myInts에서 곱해질 항2를 저장
        myInts.set(multiplyIndex, multiInt1 * multiInt2); //곱해서 myInts의 앞쪽 인덱스에 집어넣고,
        myInts.remove(multiplyIndex + 1); //뒤의 값은 이미 사용했으므로 지운다

        return myInts;
    }

    public static List<Integer> myIntsAfterDivide(List<Integer> myInts, int divideIndex) {
        int divisorInt1 = myInts.get(divideIndex);
        int divisorInt2 = myInts.get(divideIndex + 1);
        myInts.set(divideIndex, divisorInt1 / divisorInt2);
        myInts.remove(divideIndex + 1);

        return myInts;
    }

    public static int plusAndMinus(List<Integer> myInts, List<String> myCalcs) {
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
