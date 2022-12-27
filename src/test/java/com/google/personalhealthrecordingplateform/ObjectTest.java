package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/15 9:30
 */
public class ObjectTest {
    @Test
    public void test() {
        Object object = new Object();
        int[] arr = new int[]{2};
        Integer[] arr1 = new Integer[]{4};
        char[] charArr = new char[1];
        System.out.println(arr.getClass().getName());
        System.out.println(arr.getClass().getTypeName());
        System.out.println(arr1.getClass().getName());
        System.out.println(charArr.getClass().getName());


    }
}
