package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/15 9:30
 */
public class ObjectTest {
    @Test
    public void test() {
        Object obj = "123";
        Object obj1 = 1222.3f;
        String str = (String) obj;

        //Double -> Float报错
        Float fl = (Float) obj1;
        System.out.println();
    }
}
