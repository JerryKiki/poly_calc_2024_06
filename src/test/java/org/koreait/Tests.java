package org.koreait;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Tests {
    @Test
    @DisplayName("1 + 1 == 2")
    void t1() {
        assertThat(Calc.run("1 + 1")).isEqualTo(2);
    }

    @Test
    @DisplayName("2 + 1 == 3")
    void t2() {
        assertThat(Calc.run("2 + 1")).isEqualTo(3);
    }

    @Test
    @DisplayName("2 + 2 == 4")
    void t3() {
        assertThat(Calc.run("2 + 2")).isEqualTo(4);
    }

    @Test
    @DisplayName("3 + 2 == 5")
    void t4() {
        assertThat(Calc.run("3 + 2")).isEqualTo(5);
    }

    @Test
    @DisplayName("1000 + 200 == 1200")
    void t5() {
        assertThat(Calc.run("1000 + 200")).isEqualTo(1200);
    }

    @Test
    @DisplayName("50 - 30 == 20")
    void t6() {
        assertThat(Calc.run("50 - 30")).isEqualTo(20);
    }

    @Test
    @DisplayName("3 - 1 == 2")
    void t7() {
        assertThat(Calc.run("3 - 1")).isEqualTo(2);
    }

    @Test
    @DisplayName("10 + 20 + 30 == 60")
    void t8() {
        assertThat(Calc.run("10 + 20 + 30")).isEqualTo(60);
    }

    @Test
    @DisplayName("10 - 20 + 30 == 20")
    void t9() {
        assertThat(Calc.run("10 - 20 + 30")).isEqualTo(20);
    }

    @Test
    @DisplayName("10 - 10 - 10 - 10 + 10 + 10 - 10 == -10")
    void t10() {
        assertThat(Calc.run("10 - 10 - 10 - 10 + 10 + 10 - 10")).isEqualTo(-10);
    }

    @Test
    @DisplayName("100 - 20 + 50 + 100 + 30 - 40 == 220")
    void t11() {
        assertThat(Calc.run("100 - 20 + 50 + 100 + 30 - 40")).isEqualTo(220);
    }

    @Test
    @DisplayName("10 * 10 * 10 == 1000")
    void t12() {
        assertThat(Calc.run("10 * 10 * 10")).isEqualTo(1000);
    }

    @Test
    @DisplayName("10 + 5 * 2 == 20")
    void t13() {
        assertThat(Calc.run("10 + 5 * 2")).isEqualTo(20);
    }

    @Test
    @DisplayName("10 + 5 * 2 * 4 == 50")
    void t14() {
        assertThat(Calc.run("10 + 5 * 2 * 4")).isEqualTo(50);
    }

    @Test
    @DisplayName("10 * 3 + 2 * 4 == 50")
    void t15() {
        assertThat(Calc.run("10 * 3 + 2 * 4")).isEqualTo(38);
    }

    @Test
    @DisplayName("20 + 10 + 5 * 2 == 40")
    void t16() {
        assertThat(Calc.run("20 + 10 + 5 * 2")).isEqualTo(40);
    }

    @Test
    @DisplayName("10 * -10 == -100")
    void t17() {
        assertThat(Calc.run("10 * -10")).isEqualTo(-100);
    }

    @Test
    @DisplayName("10 + -10 == 0")
    void t18() {
        assertThat(Calc.run("10 + -10")).isEqualTo(0);
    }

    @Test
    @DisplayName("10 / 10 == 1")
    void t19() {
        assertThat(Calc.run("10 / 10")).isEqualTo(1);
    }

    @Test
    @DisplayName("10 / 10 * 10 == 10")
    void t20() {
        assertThat(Calc.run("10 / 10 * 10")).isEqualTo(10);
    }

    @Test
    @DisplayName("10 * 20 + 10 + 5 * 2 == 220")
    void t21() {
        Assertions.assertThat(Calc.run("10 * 20 + 10 + 5 * 2")).isEqualTo(220);
    }

    @Test
    @DisplayName("(10 + 20) == 30")
    void t22() {
        Assertions.assertThat(Calc.run("(10 + 20)")).isEqualTo(30);
    }

    @Test
    @DisplayName("(((10 + 20))) == 30")
    void t23() {
        Assertions.assertThat(Calc.run("(((10 + 20)))")).isEqualTo(30);
    }

    @Test
    @DisplayName("((10 + 20)) == 30")
    void t24() {
        Assertions.assertThat(Calc.run("((10 + 20))")).isEqualTo(30);
    }

    @Test
    @DisplayName("(20 + 20) + 20 == 60")
    void t25() {
        Assertions.assertThat(Calc.run("(20 + 20) + 20")).isEqualTo(60);
    }

    @Test
    @DisplayName("20 + (20 + 20) == 60")
    void t26() {
        Assertions.assertThat(Calc.run("20 + (20 + 20)")).isEqualTo(60);
    }

    @Test
    @DisplayName("(20 + (20 + 20)) == 60")
    void t27() {
        Assertions.assertThat(Calc.run("(20 + (20 + 20))")).isEqualTo(60);
    }

    @Test
    @DisplayName("10 * 20 / 10 + 5 * 2 == 30")
    void t28() {
        Assertions.assertThat(Calc.run("10 * 20 / 10 + 5 * 2")).isEqualTo(30);
    }

    @Test
    @DisplayName("10 * 30 / (10 + 5) * 2 == 40")
    void t29() {
        Assertions.assertThat(Calc.run("10 * 30 / (10 + 5) * 2")).isEqualTo(40);
    }

    @Test
    @DisplayName("(10 * 30 / (10 + 5) * 2) == 40")
    void t30() {
        Assertions.assertThat(Calc.run("(10 * 30 / (10 + 5) * 2)")).isEqualTo(40);
    }

    @Test
    @DisplayName("(10 + 20) * 3 == 90")
    void t31() {
        Assertions.assertThat(Calc.run("(10 + 20) * 3")).isEqualTo(90);
    }

    @Test
    @DisplayName("(10 + 5) / (10 + 5) * 2 == 2")
    void t32() {
        Assertions.assertThat(Calc.run("(10 + 5) / (10 + 5) * 2")).isEqualTo(2);
    }

    @Test
    @DisplayName("((10 + 5) / (10 + 5) * 2) == 2")
    void t33() {
        Assertions.assertThat(Calc.run("((10 + 5) / (10 + 5) * 2)")).isEqualTo(2);
    }

    @Test
    @DisplayName("((10 + 5) / (10 + 5) * 2) == 2")
    void t34() {
        Assertions.assertThat(Calc.run("((10 + 5) / (10 + 5) * 2)")).isEqualTo(2);
    }

    @Test
    @DisplayName("(((10 + 10) / 10 + 10 * 2)) == 22")
    void t35() {
        Assertions.assertThat(Calc.run("(((10 + 10) / 10 + 10 * 2))")).isEqualTo(22);
    }

    @Test
    @DisplayName("3 * 10 + (40 + 10) / 10 + (10 + 5) == 50")
    void t36() {
        Assertions.assertThat(Calc.run("3 * 10 + (40 + 10) / 10 + (10 + 5)")).isEqualTo(50);
    }

    @Test
    @DisplayName("((20 + 20)) + 20 == 60")
    void t37() {
        Assertions.assertThat(Calc.run("((20 + 20)) + 20")).isEqualTo(60);
    }

    @Test
    @DisplayName("(10 + 20) * 3 == 90")
    void t38() {
        Assertions.assertThat(Calc.run("(10 + 20) * 3")).isEqualTo(90);
    }

    @Test
    @DisplayName("10 + (10 + 5) == 25")
    void t39() {
        Assertions.assertThat(Calc.run("10 + (10 + 5)")).isEqualTo(25);
    }

    @Test
    @DisplayName("-(10 + 5) == -15")
    void t40() {
        Assertions.assertThat(Calc.run("-(10 + 5)")).isEqualTo(-15);
    }

    @Test
    @DisplayName(" -(10 + 5) == -15")
    void t41() {
        Assertions.assertThat(Calc.run(" -(10 + 5)")).isEqualTo(-15);
    }

    @Test
    @DisplayName("-10 / 2 == -5")
    void t42() {
        Assertions.assertThat(Calc.run("-10 / 2")).isEqualTo(-5);
    }

    @Test
    @DisplayName("5 / 2 == 2.5")
    void t43() {
        Assertions.assertThat(Calc.run("5 / 2")).isEqualTo(2.5);
    }

    @Test
    @DisplayName("10 / 2 == 5")
    void t44() {
        Assertions.assertThat(Calc.run("10 / 2")).isEqualTo(5);
    }

    @Test
    @DisplayName("3 * 1 + (1 - (4 * 1 - (1 - 1))) == 0")
    void t45() {
        Assertions.assertThat(Calc.run("3 * 1 + (1 - (4 * 1 - (1 - 1)))")).isEqualTo(0);
    }

    @Test
    @DisplayName("10 / 2.5 == 4")
    void t46() {
        Assertions.assertThat(Calc.run("10 / 2.5")).isEqualTo(4);
    }

    @Test
    @DisplayName("10 / 0 == 0으로 나누는 에러")
    void t47() {
        assertThatThrownBy(() -> Calc.run("10 / 0"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("0으로 나누어지는 경우가 있어 계산이 불가능합니다.");
    }

    @Test
    @DisplayName("-(8 + 2) * -(7 + 3) + 5 == 105")
    void t48() {
        Assertions.assertThat(Calc.run("-(8 + 2) * -(7 + 3) + 5")).isEqualTo(105);
    }

    @Test
    @DisplayName("10 / (5 - 5) == 0으로 나누는 에러")
    void t49() {
        assertThatThrownBy(() -> Calc.run("10 / (5 - 5)"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("0으로 나누어지는 경우가 있어 계산이 불가능합니다.");
    }

    @Test
    @DisplayName("10 * 10 / (5 - 5) == 0으로 나누는 에러")
    void t50() {
        assertThatThrownBy(() -> Calc.run("10 * 10 / (5 - 5)"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("0으로 나누어지는 경우가 있어 계산이 불가능합니다.");
    }

    @Test
    @DisplayName("5 * (3 + 5) / 2.5 == 16")
    void t51() {
        Assertions.assertThat(Calc.run("5 * (3 + 5) / 2.5")).isEqualTo(16);
    }

    @Test
    @DisplayName("5 * (3 + 6) / 4 == 11.25")
    void t52() {
        Assertions.assertThat(Calc.run("5 * (3 + 6) / 4")).isEqualTo(11.25);
    }

    @Test
    @DisplayName("10 / 3 == 3.333")
    void t53() {
        Assertions.assertThat(Calc.run("10 / 3")).isEqualTo(3.333);
    }

    @Test
    @DisplayName("-(8 + 2) * -(7 + 3) + 5 == 105")
    void t54() {
        Assertions.assertThat(Calc.run("-(8 + 2) * -(7 + 3) + 5")).isEqualTo(105);
    }
}
