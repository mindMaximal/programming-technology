package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    @Test
    void Multiplicator1Test() {

        int num = 100;
        int mult = 1;

        double result = 100;
        double test = Logic.Compare(num, mult);

        assertEquals(result, test);
    }

    @Test
    void Multiplicator2Test() {

        int num = 1000000;
        int mult = 2;

        double result = 1;
        double test = Logic.Compare(num, mult);

        assertEquals(result, test);
    }

    @Test
    void Multiplicator3Test() {

        int num = 1000;
        int mult = 3;

        double result = 1;
        double test = Logic.Compare(num, mult);

        assertEquals(result, test);
    }

    @Test
    void Multiplicator4Test() {

        int num = 3;
        int mult = 4;

        double result = 3000;
        double test = Logic.Compare(num, mult);

        assertEquals(result, test);
    }

    @Test
    void Multiplicator5Test() {

        int num = 2;
        int mult = 5;

        double result = 200;
        double test = Logic.Compare(num, mult);

        assertEquals(result, test);
    }


    @Test
    void MultiplicatorNotFormatTest() {

        int num = 200;
        int mult = 0;

        String result = "Неверное значение: 0";

        Throwable exception = assertThrows(
            IllegalStateException.class,
            ()->{
                Logic.Compare(num, mult);
            }
        );
    }
}