package me.incheol.chapter10;


import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

//  LongAccumulator로 누적 요소의 최댓값 또는 최솟값을 계산하라.
public class Question9 {

    private static LongAccumulator maxAccumulator = new LongAccumulator(Question9::getMaxValue, 0);
    private static LongAccumulator minAccumulator = new LongAccumulator(Question9::getMinValue, 0);


    public static void main(String[] args) {
        putRandomValues();
        System.out.println(maxAccumulator.get());
        System.out.println(minAccumulator.get());
    }

    //Make method to use Method Reference for LongBinaryOperator FunctionalInterface to get Maximum Value
    private static long getMaxValue(long l, long l1) {
        return l > l1 ? l : l1;

    }

    private static long getMinValue(long l, long l1) {
        return l > l1 ? l1 : l;
    }


    private static void putRandomValues() {
        Random random = new Random();
        long value = 0;
        for (long i = 0; i < 1000; i++) {
            value = random.nextLong();
            maxAccumulator.accumulate(value);
            minAccumulator.accumulate(value);
        }
    }

}
