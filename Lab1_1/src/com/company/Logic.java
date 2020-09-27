package com.company;

public class Logic {

    public static String Compare(int[] nums) {

        if (nums.length != 3) return "Введен некорректный массив";

        //Создаем необходимые переменные
        int max = nums[0];
        int min = nums[0];
        Integer medium = null;

        //Перебираем массив и ищем максимальное и минимальное значения путем сравнения
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] > max) {
                max = nums[i];
            } else if (nums[i] < min) {
                min = nums[i];
            }

        }

        //Снова перебираем массив, находя число удовлетворяющей условию
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < max && nums[i] > min) medium = nums[i];

        }

        //Возвращаем результат
        return "Max = " + max + "\r\nMedium = " + (medium != null ? medium : "Такого числа нет")  + "\r\nMin = " + min;
    }
}
