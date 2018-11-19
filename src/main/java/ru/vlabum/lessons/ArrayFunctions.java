package ru.vlabum.lessons;

public class ArrayFunctions {

    public static boolean is14(int[] array) {
        if (array.length < 2)
            return false;
        boolean is1 = false, is4 = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) is1 = true;
            else if (array[i] == 4) is4 = true;
            if (is1 && is4)
                return true;
        }
        return false;
    }


    public static int[] getTail4(int[] array) {
        return getTailN(array, 4);
    }

    public static int[] getTailN(int[] array, int num) {
        if (array.length == 0)
            throw new RuntimeException("array is empty");
        int inum = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num)
                inum = i;
        }
        if (inum == -1)
            throw new RuntimeException("No num " + num + " in array.");
        inum++;
        int[] arrayReturn = new int[array.length - inum];
        System.arraycopy(array, inum, arrayReturn, 0, arrayReturn.length);
        return arrayReturn;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
