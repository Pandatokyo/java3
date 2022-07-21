package java3.lesson1;


import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    // Первое задание
    private static void swapArrayItems(Object[] array, int firstNumPos, int secondNumPos) throws WrongPosForSwapException {
        if (firstNumPos < 0 || firstNumPos > array.length ||
                secondNumPos < 0 || secondNumPos > array.length || firstNumPos == secondNumPos) {
            throw new WrongPosForSwapException("An error occurred. Check item positions.");
        }
        Object temp = array[firstNumPos];
        array[firstNumPos] = array[secondNumPos];
        array[secondNumPos] = temp;
    }
    // Второе задание

    private static <T> ArrayList arrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

}
