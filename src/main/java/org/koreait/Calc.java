package org.koreait;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calc {

    public static double run(String exp) {

        //양 옆에 쓸데없는 공백 제거(유저의 오타 방지)
        exp = exp.trim();

        //괄호가 있으면 아래에서 재귀함수로 모두 처리
        while (exp.contains("(") && exp.contains(")")) {
            //괄호들을 배열에 저장 -> 괄호가 나오는 순서를 알아보기 위함
            //앞에 두개만 저장하면 효율이 좋아질 것 같은데...
            String[] expArr = exp.split("");
            String[] parenthesis = Arrays.stream(expArr)
                    .filter(e -> Objects.equals(e, "(") || Objects.equals(e, ")"))
                    .toArray(String[]::new);

            int startIndex = exp.indexOf("("); //처음 나오는 여는 괄호 인덱스 저장
            int endIndex = exp.lastIndexOf(")"); //우선 마지막 닫는 괄호 인덱스 저장

            if (!parenthesis[0].equals(parenthesis[1])) { //하지만 만약에 첫번째 괄호와 두번째 괄호가 다르면,
                endIndex = exp.indexOf(")"); //바로 다음 닫는 괄호(==첫번째 닫는 괄호) 찾아서 다시 인덱스 저장해줌
            }

            //괄호 안쪽을 재귀함수써서 새로운 exp 저장
            String insideParenthesis = exp.substring(startIndex + 1, endIndex); //괄호 떼어내고 계산해야될 놈만 run에 보내주기 위함
            String withParenthesis = exp.substring(startIndex, endIndex + 1); //괄호까지 포함된, replace 대상인 String
            String replacementExp = Double.toString(run(insideParenthesis)); //계산된 놈을 String화 : 위 String 자리에 들어갈 놈
            //괄호 안에 또 괄호가 있거나, 뒤에 괄호가 남아있으면 이 위에서 재귀함수로 괄호 처리를 반복해주게 된다

            exp = exp.replace(withParenthesis, replacementExp); //괄호 처리가 완료된 새로운 exp
            // 이제 앞에서부터 순차적으로 계산되면 되므로 아래로 넘어갈 수 있다.
        }

        //괄호를 처리한 스트링을 공백으로 split
        String[] bits = exp.split(" ");
        List<Double> myDouble = new ArrayList<>(); //숫자만 담을 ArrayList
        List<String> myCalcs = new ArrayList<>(); //부호만 담을 ArrayList

        //ArrayList에 숫자와 부호를 구분해서 저장
        for (int i = 0; i < bits.length; i++) {
            if (i % 2 == 0) {
                myDouble.add(Double.parseDouble(bits[i])); //0을 포함한 짝수번째에는 int로 변환해 숫자리스트에 저장
            } else {
                myCalcs.add(bits[i]); //홀수번째에는 String 그대로 부호리스트에 저장
            }
        }

        //인덱스 규칙성 발견
        //부호 인덱스 1번이면 넘버 인덱스 1, 2번끼리
        //부호 인덱스 0번이면 넘버 인덱스 0, 1번끼리
        //부호인덱스 */ 부호인덱스 + 1 해주면 됨

        //괄호 처리 후 곱셈 나눗셈 앞에서부터 +, -보다 먼저 처리 후, +, -만 있는 arraylist로 재정립
        while (myCalcs.contains("*") || myCalcs.contains("/")) { //부호 중 *이나 /가 있는동안 반복, 앞에서부터 계산하게 된다
            int multiplyIndex = checkMultiply(myCalcs); // *의 첫번째 인덱스값을 찾아서 저장, 없으면 -1이됨
            int divideIndex = checkDivisor(myCalcs); // /의 첫번째 인덱스값을 찾아서 저장, 없으면 -1이됨
            if (multiplyIndex != -1 && divideIndex != -1) { //둘 다 해야될 때
                if (multiplyIndex < divideIndex) {//곱셈 인덱스가 더 앞이면 곱셈을 함
                    myDouble = myDoubleAfterMultiply(myDouble, multiplyIndex);
                    myCalcs.remove(multiplyIndex); // *은 이미 사용했으므로 지운다
                } else { //나눗셈 인덱스가 더 앞이면 나눗셈을 함
                    myDouble = myDoubleAfterDivide(myDouble, divideIndex);
                    myCalcs.remove(divideIndex); // /는 이미 사용했으므로 지운다
                }
            } else if (multiplyIndex != -1) { //곱셈만 하면 될 때
                myDouble = myDoubleAfterMultiply(myDouble, multiplyIndex);
                myCalcs.remove(multiplyIndex); // *은 이미 사용했으므로 지운다
            } else if (divideIndex != -1) { //나눗셈만 하면 될 때
                myDouble = myDoubleAfterDivide(myDouble, divideIndex);
                myCalcs.remove(divideIndex); // /는 이미 사용했으므로 지운다
            }
        }

        //재정립된 arraylist들로 +, - 계산
        double sum = plusAndMinus(myDouble, myCalcs);

        return Math.round(sum * 1000) / 1000.0; //나눗셈 때문에 실수인 경우도 있으므로, 소숫점 셋째자리까지 리턴
    }

    //괄호처리 후 곱셈 여부 확인해서 첫번째 곱셈기호 인덱스 반환
    public static int checkMultiply(List<String> myCalcs) {
        if (myCalcs.contains("*")) {
            return myCalcs.indexOf("*");
        }
        return -1;
    }

    //괄호처리 후 나눗셈 여부 확인해서 첫번째 나눗셈기호 인덱스 반환
    public static int checkDivisor(List<String> myCalcs) {
        if (myCalcs.contains("/")) {
            return myCalcs.indexOf("/");
        }
        return -1;
    }

    //곱셈값을 저장하고 이미 활용한 값을 지워서 숫자 리스트를 재반환해주는 함수
    public static List<Double> myDoubleAfterMultiply(List<Double> myDouble, int multiplyIndex) {
        double multiDouble1 = myDouble.get(multiplyIndex); //규칙성에 따라 myDouble에서 곱해질 항1을 저장
        double multiDouble2 = myDouble.get(multiplyIndex + 1); //규칙성에 따라 myDouble에서 곱해질 항2를 저장
        myDouble.set(multiplyIndex, multiDouble1 * multiDouble2); //곱해서 myDouble의 앞쪽 인덱스에 집어넣고,
        myDouble.remove(multiplyIndex + 1); //뒤의 값은 이미 사용했으므로 지운다

        return myDouble; //수정된 myDouble을 리턴
    }

    //나눗셈값을 저장하고 이미 활용한 값을 지워서 숫자 리스트를 재반환해주는 함수
    public static List<Double> myDoubleAfterDivide(List<Double> myDouble, int divideIndex) {
        double divisorDouble1 = myDouble.get(divideIndex); //나눠질 값
        double divisorDouble2 = myDouble.get(divideIndex + 1); //나눌 수

        //나눌 수가 0일 때 예외처리
        if (divisorDouble2 == 0) throw new RuntimeException("0으로 나누어지는 경우가 있어 계산이 불가능합니다.");

        //예외를 통과하면
        myDouble.set(divideIndex, divisorDouble1 / divisorDouble2); //나누기해서 myDouble의 앞쪽 인덱스에 집어넣고,
        myDouble.remove(divideIndex + 1); //뒤의 값은 이미 사용했으므로 지운다

        return myDouble; //수정된 myDouble을 리턴
    }

    //+, -로만 재정립된 arraylist들을 가지고 +, - 처리해주는 함수
    public static double plusAndMinus(List<Double> myDouble, List<String> myCalcs) {
        double sum = myDouble.get(0); //새로 재정립된 arraylist의 첫번째 값 (+, -가 없다면 0번째 값을 리턴하도록)
        if (myCalcs.isEmpty()) return sum;
        int calcsIndex = 0;

        for (int i = 1; i < myDouble.size(); i++) {
            if (myCalcs.get(calcsIndex).equals("+")) {
                sum += myDouble.get(i);
                calcsIndex++;
            } else if (myCalcs.get(calcsIndex).equals("-")) {
                sum -= myDouble.get(i);
                calcsIndex++;
            } else throw new RuntimeException("해석 불가 : 올바른 계산식이 아닙니다.");
        }

        return sum;
    }
}
