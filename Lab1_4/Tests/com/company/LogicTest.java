package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    @Test
    void DefaultStringTest() {

        String string = "ttest teest tesst testt";

        int result = 4;
        int msg = Logic.SearchIdenticalSymbols(string);

        assertEquals(result, msg);
    }

    @Test
    void ZeroSymbolsTest() {

        String string = "test test test test";

        int result = 0;
        int msg = Logic.SearchIdenticalSymbols(string);

        assertEquals(result, msg);
    }

    @Test
    void WhitespaceDoubleSymbolTest() {

        String string = "test  test  test test";

        int result = 0;
        int msg = Logic.SearchIdenticalSymbols(string);

        assertEquals(result, msg);
    }

    @Test
    void EmptyStrTest() {

        String string = "";

        int result = 0;
        int msg = Logic.SearchIdenticalSymbols(string);

        assertEquals(result, msg);
    }
}