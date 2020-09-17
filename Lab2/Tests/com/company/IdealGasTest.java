package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdealGasTest {

    @Test
    void AddPTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 15;
        int resultV = 1;

        double valueP = idealGas.addP(3);
        double valueV = idealGas.getVolume();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void reducePTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultV = 15;

        double valueP = idealGas.reduceP(5);
        double valueV = idealGas.getVolume();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void AddVTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultV = 15;

        double valueV = idealGas.addV(5);
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void reduceVTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 15;
        int resultV = 1;

        double valueV = idealGas.reduceV(3);
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void AddTTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 25;
        int resultT = 25;


        double valueT = idealGas.addT(5);
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultT, valueT);
    }

    @Test
    void reduceTTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultT = 1;

        double valueT = idealGas.reduceT(5);
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultT, valueT);
    }

}