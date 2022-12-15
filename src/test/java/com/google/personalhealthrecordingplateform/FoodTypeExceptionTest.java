package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.exception.FoodTypeException;
import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/14 22:08
 */
public class FoodTypeExceptionTest {
    @Test
    public void test() {
        try {
            throw new FoodTypeException("异常异常");
        } catch (FoodTypeException e) {
            System.out.println(e.getMessage());
        }
    }
}
