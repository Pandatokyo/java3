package main.java;

import java.util.Arrays;

public class Task3 {
    public static void main(String[] args) {
        int[] arr = {1,1,4,4,4,4,1,1,4,1,4,4,1};
        int[] arr1 = {4,4,4};
        int[] arr2 = {1, 1, 1};
        int[] arr3 = {};
        System.out.println(checkArray(arr));
        System.out.println(checkArray(arr1));
        System.out.println(checkArray(arr2));
        System.out.println(checkArray(arr3));
    }

    public static boolean checkArray(int[] arr) {
        boolean has1 = false;
        boolean has4 = false;

        for (int i : arr) {
            if (has1 & has4) break;
            if (i == 1) has1 = true;
            if (i == 4) has4 = true;
        }
        return has1 && has4;
    }
}

