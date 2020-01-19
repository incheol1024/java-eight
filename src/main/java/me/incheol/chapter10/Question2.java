package me.incheol.chapter10;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class Question2 {

    public static void main(String[] args) {
        int[] arrayTest1 = createNumberArray(10_000);
        int[] arrayTest2 = createNumberArray(10_000);

        Instant instant = Instant.now();
        useArraySort(arrayTest2);
        Instant end = Instant.now();
        Duration sortTime = Duration.between(instant, end);


        Instant parallelSortStart = Instant.now();
        useParallelSort(arrayTest1);
        Instant parallelSortEnd = Instant.now();
        Duration parallelSortTime = Duration.between(parallelSortStart, parallelSortEnd);


        System.out.println(parallelSortTime.toMillis());
        System.out.println(sortTime.toMillis());
        System.out.println(parallelSortTime.minus(sortTime).isNegative());

    }

    private static int[] createNumberArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int)Math.random() * size;
        }
        return arr;
    }

    private static void useParallelSort(int[] sorting) {
        Arrays.parallelSort(sorting);
    }

    private static void useArraySort(int[] sorting) {
        Arrays.sort(sorting);
    }


}
