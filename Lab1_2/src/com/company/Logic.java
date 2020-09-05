package com.company;

public class Logic {

    public static double Compare(int num, int multiplicator) {
        //Преобразуем исходное число, для проведения умножения
        double result = (double) num;

        //Исходя из значения мультипликатора выполняем действия
        switch (multiplicator) {
            case 1:
                result *= 1;
                break;
            case 2:
                result *= 0.000001;
                break;
            case 3:
                result *= 0.001;
                break;
            case 4:
                result *= 1000;
                break;
            case 5:
                result *= 100;
                break;
            //В случае неверного значения - выводим ошибку
            default:
                throw new IllegalStateException("Неверное значение: " + multiplicator);
        }

        return result;
    }
}
