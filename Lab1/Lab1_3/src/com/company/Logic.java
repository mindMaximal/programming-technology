package com.company;

public class Logic {

    public static String Compare(int[] array) {

        //Присваиваем начальные значения
        int max = array[0];
        int preMax = array[0];
        int min = array[0];
        int nextMin = array[0];

        //Проходим по массиву и ищем максимальное, минимальное значение
        for (int i = 0; i < array.length; i++) {

            if (array[i] > max) {
                max = array[i];
            } else if (array[i] < min) {
                min = array[i];
            }
        }

        //Если превое число оказалось максимальным, то изменим второе по значимости
        if (preMax == max) {
            preMax = array[1];
        }
        //Если превое число оказалось минимуму, то изменим второе по значимости
        if (nextMin == min) {
            nextMin = array[1];
        }

        //Проходим по массиву и ищем второе максимальное и минимальное числа
        for (int i = 0; i < array.length; i++) {

            if (array[i] > preMax && array[i] != max) {
                preMax = array[i];
            } else if (array[i] < nextMin && array[i] != min) {
                nextMin = array[i];
            }

        }

        //Выводим результат
        return "Два наибольших числа: " + preMax + ", " + max + "\r\nДва наименьших: " + min + ", " + nextMin;
    }

}
