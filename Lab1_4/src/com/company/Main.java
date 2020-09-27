package com.company;

import static java.lang.Character.isWhitespace;

public class Main {

    public static void main(String[] args) {
        //Вводим исходную строку
        String string = "ttest teest tesst testt";

        //Расчитываем результат
        int result = Logic.SearchIdenticalSymbols(string);

        //Выводим сообщение
        System.out.println("В строке " + result + " одинаковых соседних символов");
    }
}
