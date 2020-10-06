package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    @Test 
    void CompareTest() {

        int[] array = new int[] {300, 5, 1, 3, 7, 123, 74};

        String result = "Два наибольших числа: 123, 300\r\nДва наименьших: 1, 3";
        String msg = Logic.Compare(array);

        assertEquals(result, msg);
    }

    @Test
    void SmallArrayTest() {

        int[] array = new int[] {300, 5, 1};

        String result = "Два наибольших числа: 5, 300\r\nДва наименьших: 1, 5";
        String msg = Logic.Compare(array);

        assertEquals(result, msg);
    }

}