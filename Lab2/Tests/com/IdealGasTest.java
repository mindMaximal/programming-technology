package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdealGasTest {

    @Test
    void AddPTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 25;
        double resultV = 0.6;

        double valueP = idealGas.add(new Pressure(5));
        double valueV = idealGas.getVolume();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void reducePTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultV = 15;

        double valueP = idealGas.reduce(new Pressure(5));
        double valueV = idealGas.getVolume();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void AddVTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultV = 15;

        double valueV = idealGas.add(new Volume(5));
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void reduceVTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 15;
        int resultV = 1;

        double valueV = idealGas.reduce(new Volume(3));
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultV, valueV);
    }

    @Test
    void AddTTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 25;
        int resultT = 25;


        double valueT = idealGas.add(new Temperature(5));
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultT, valueT);
    }

    @Test
    void reduceTTest() {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        int resultP = 1;
        int resultT = 1;

        double valueT = idealGas.reduce(new Temperature(5));
        double valueP = idealGas.getPressure();

        assertEquals(resultP, valueP);
        assertEquals(resultT, valueT);
    }

}