package main.java;

import java.util.Arrays;

public class Task2 {

    public static int[] task(int[] arr) {
        int x = 0;
        int[] result = null;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                x = arr[i];
                result = new int[arr.length - i - 1];
                for (int j = i + 1, k = 0; j < arr.length; j++, k++) {
                    if (arr[j] == 4) continue;
                    result[k] = arr[j];
                }
            }
        }
        if (x == 0) {
            {
                throw new RuntimeException();
            }
        }
        return result;
    }
}
