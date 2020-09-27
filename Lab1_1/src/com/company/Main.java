package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] nums = new int[3];

        System.out.println("Введите три числа:");
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < nums.length; i++) {
            nums[i] = in.nextInt();
        }

        String result = Logic.Compare(nums);

        System.out.println(result);

    }
}
