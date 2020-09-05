package com.company;

import static java.lang.Character.isWhitespace;

public class Logic {

    //Метод подсчета количества одинаковых символов
    public static int SearchIdenticalSymbols (String str) {

        //Создаем счетчик для подсчета
        int j = 0;

        //Проходим по каждому символу строки
        for (int i = 0; i + 1 < str.length(); i++) {
            //Если данный символ, равен следующему и это не пробел
            if (str.charAt(i) == str.charAt(i + 1) && !isWhitespace(str.charAt(i))) j++;
        }

        //Возвращаем результат
        return j;
    }
}
