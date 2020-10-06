package com.company;

public class Main {

    public static void main(String[] args) {
        //Исходный массив
        int[] array = new int[] {300, 5, 1, 3, 7, 123, 74};

        //Расчитываем результат
        String result = Logic.Compare(array);

        //Выводим сообщение
        System.out.println(result);

    }
}
