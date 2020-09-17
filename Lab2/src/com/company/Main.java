package com.company;

public class Main {

    public static void main(String[] args) {

        IdealGas idealGas = new IdealGas(5, 3, 5);

        System.out.println(idealGas.getInfo());

        idealGas.addP(3);

        System.out.println(idealGas.getInfo());

    }
}
