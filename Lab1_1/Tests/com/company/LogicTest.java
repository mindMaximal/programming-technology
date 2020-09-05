package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    @Test
    void Multiplicator1Test() {

        int[] nums = new int[] {1, 3, 5};

        String result = Logic.Compare(nums);
        String msg = "Max = 5\r\nMedium = 3\r\nMin = 1";

        assertEquals(msg, result);
    }

    @Test
    void SimilarNumsTest() {

        int[] nums = new int[] {5, 5, 5};

        String result = Logic.Compare(nums);
        String msg = "Max = 5\r\nMedium = Такого числа нет\r\nMin = 5";

        assertEquals(msg, result);
    }

    @Test
    void OneNumTest() {

        int[] nums = new int[] {5};

        String result = Logic.Compare(nums);
        String msg = "Введен некорректный массив";

        assertEquals(msg, result);
    }

    @Test
    void BigArrayTest() {

        int[] nums = new int[] {5, 25, 3, 4};

        String result = Logic.Compare(nums);
        String msg = "Введен некорректный массив";

        assertEquals(msg, result);
    }
}