package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Собираем данные
        System.out.print("Введите число, соотвествующее единице измерения: ");

        //Создаем экземпляр сканера
        Scanner in = new Scanner(System.in);

        //Получаем значение для множителя
        int multiplicator = in.nextInt();

        //Получаем число
        System.out.print("Введите число: ");
        int num = in.nextInt();

        //Вычисляем результат
        double result = Logic.Compare(num, multiplicator);

        //Выводим результат
        System.out.println("Результат: " + result + " кг");
    }
}
